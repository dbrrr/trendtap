(ns trend.rest-api.core
  (:require
   [trend.rest-api.middleware :as middleware]
   [reitit.coercion.malli :as malli-coercion]
   [reitit.ring :as ring]
   [trend.rest-api.signup :as signup]
   [trend.rest-api.login :as login]
   [trend.rest-api.link :as link]
   [trend.rest-api.app :as app]
   [trend.rest-api.demo :as demo]
   [trend.silo.interface :as silo]
   [trend.actor.interface :as actor]
   [clojure.data.json :as json]
   [trend.util.interface :as util]
   [trend.account.interface :as account]
   [trend.rest-api.common :as common]))

(defn css [_req]
  {:status 200
   :headers {"Content-type" "text/css"}
   :body (slurp "bases/rest-api/resources/rest-api/common.css")})

(defn hyperscript [_req]
  {:status 200
   :headers {"Content-type" "application/javascript"}
   :body (slurp "bases/rest-api/resources/rest-api/hyperscript.js")})

(defn htmx-library [_req]
  {:status 200
   :headers {"Content-type" "application/javascript"}
   :body (slurp "bases/rest-api/resources/rest-api/htmx.min.js")})

(defn htmx-ws [_req]
  {:status 200
   :headers {"Content-type" "application/javascript"}
   :body (slurp "bases/rest-api/resources/rest-api/ws.js")})

(defn htmx-sse [_req]
  {:status 200
   :headers {"Content-type" "application/javascript"}
   :body (slurp "bases/rest-api/resources/rest-api/sse.js")})

(defn activity [_req]
  {:status 200
   :headers {"Content-type" "application/javascript"}
   :body (slurp "bases/rest-api/resources/rest-api/activity.js")})

(defn tailwind [_req]
  {:status 200
   :headers {"Content-type" "text/css"}
   :body (slurp "bases/rest-api/resources/rest-api/tailwind.js")})

(defn- actor->graph-element [actor]
  (println (clojure.pprint/pprint actor))
  {:data {:id (util/id actor)
          :nodeType "actor"
          :backgroundImage (-> actor :account :details :profile-url)
          :label (-> actor :details :description)}})

(defn- silo->graph-elements [silo]
  ;; TODO update these to uses classes instead of custom attributes
  (concat
   [{:data {:id (util/id silo)
            :label ""
            :nodeType "silo"}}]
   (map actor->graph-element (:actors silo))
   (for [actor (:actors silo)]
     {:data {:id (str (rand-int 10000))
             :source (util/id actor)
             :target (util/id silo)}})))

(defn silos-as-graph [{:keys [ctx] :as req}]
  (let [silos (silo/all! ctx)
        resolved-silos (map #(silo/resolve! ctx (util/id %)) silos)]
    {:body (json/write-str (vec (mapcat silo->graph-elements resolved-silos)))
     :headers {"Content-Type" "application/json"}
     :status 200}))

;; NOTE don't change this name, or link/to will break
(link/routes route-tree
             (ring/router
              [;; Unsecured routes
               [["/login" {:name :link/login
                           :get  {:handler (fn [req]
                                             (login/login-page (link/to :link/login
                                                                        :link/signup)))}
                           :post {:handler (fn [{:keys [ctx parameters] :as req}]
                                             (let [user-email (-> parameters :form :email)]
                                               (if-let [account (account/by-email! ctx user-email)]
                                                 {:status  302
                                                  :cookies {:session (:email account)}
                                                  :headers {"Location" "/app"}}
                                                 {:status  200
                                                  :body "Fixme"})))
                                  :parameters {:form any?}}}]
     ;; Unnecessary stuff
                ["/htmx-library" {:get {:handler (fn [req] (htmx-library req))}}]
                ["/activity" {:get {:handler (fn [req] (activity req))}}]
                ["/htmx-ws" {:get {:handler (fn [req] (htmx-ws req))}}]
                ["/common.css" {:get {:handler (fn [req] (css req))}}]
                ["/hyperscript" {:get {:handler (fn [req] (hyperscript req))}}]
                ["/htmx-sse" {:get {:handler (fn [req] (htmx-sse req))}}]
                ["/tailwind" {:get {:handler (fn [req] (tailwind req))}}]
                ["/" {:name :link/home
                      :get {:handler (fn [{:keys [ctx] :as req}]
                                       (if (:user ctx)
                                         {:status 302
                                          :headers {"Location" "/app"}}
                                         {:status 302
                                          :headers {"Location" "/login"}}))}}]
                ["/signup" {:name :link/signup
                            :get  {:handler (fn [req] (signup/signup-form (link/to :link/signup)))}
                            :post {:handler (fn [req]
                                              (signup/new-user req)
                                              {:status 302
                                               :headers {"Location" "/"}})
                                   :parameters {:form any?}}}]]

    ;; Secured routes
               ["" {:middleware [middleware/wrap-auth]}
                ["/app" {:name :link/app
                         :get  {:handler (fn [req]
                                           (app/load-it req))}}]
                ["/new-content" {:get {:handler (fn [req]
                                                  (app/moar req))}}]

                ["/silo/activity/" {:name    :link/silo-activity
                                    :handler (fn [req] (silos-as-graph req))}]
                ["/silo/:silo-id"
                 ["/insights"
                  ["" {:name :link/silo-insight
                       :parameters {:path {:silo-id any?}}
                       :get {:handler (fn [{:keys [ctx] :as req}]
                                        (common/render-and-respond
                                         (app/silo-insights-panel ctx (-> req :parameters :path :silo-id))))}}]
                  ["/actor/:actor-id" {:name :link/silo-actor-insight
                                       :parameters {:path {:silo-id any? :actor-id any?}}
                                       :get {:handler (fn [{:keys [ctx] :as req}]
                                                        (let [actor (actor/by-id! ctx (-> req :parameters :path :actor-id))]
                                                          (common/render-and-respond
                                                           (app/insight-panel-actor-section actor))))}}]]]
                ["/silo/example"
                 ["" {:name :link/silo-example
                      :get  {:handler (fn [req] (demo/silo (link/to :link/get-actor-row
                                                                    :link/silo-example)
                                                           (-> req :ctx :user)))}
                      :post {:handler    (fn [{{form-params :form} :parameters :as req}]
                                           (demo/generate (:ctx req)
                                                          (link/to :link/app)
                                                          form-params))
                             :parameters {:form any?}}}]

                 ["/actor" {:name :link/get-actor-row
                            :get  {:handler (fn [req] (demo/add-actor (link/to :link/get-actor-row)))}}]]]]
              {:data {:coercion   malli-coercion/coercion
                      :middleware middleware/stack}}))

(defn default-404 [req]
  {:status 404
   :body "fuckoff!"})

(def handler
  (ring/ring-handler
   route-tree
   default-404
   []))
