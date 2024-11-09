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
   [trend.util.interface :as util]))

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

(defn silos-as-graph [req]
  (let [silos (silo/all! (:ctx req))
        actors (actor/by-silos! (:ctx req) silos)
        silos-used-by-actors (set (map :silo-id actors))
        ;; TODO update these to uses classes instead of custom attributes
        silo-nodes (map #(hash-map :data {:id %
                                          :nodeType "silo"
                                          :label "Meeting"}) silos-used-by-actors)
        actor-nodes (map #(hash-map :data {:id (util/id %)
                                           :nodeType "actor"
                                           :label (-> % :details :description)}) actors)
        meeting-edges (map #(hash-map :data {:id (str (rand-int 10000))
                                             :source (util/id %)
                                             :target (:silo-id %)})
                           actors)]
    {:body (json/write-str (vec (concat silo-nodes actor-nodes meeting-edges)))
     :headers {"Content-Type" "application/json"}
     :status 200}))

;; NOTE don't change this name, or link/to will break
(link/routes route-tree
  (ring/router
   [["/" {:get {:handler (fn [req] req)}}]
    ["/app" {:name :link/app
             :get  {:handler (fn [req]
                               (app/load-it req))}}]
    ["/login" {:name :link/login
               :get  {:handler (fn [req]
                                 (login/login-page (link/to :link/login
                                                            :link/signup)))}
               :post {:handler (fn [req]
                                 (println (-> req :parameters :form))
                                 {:status  301
                                  :cookies {:session "Foobar"}
                                  :headers {"Location" "/signup"}})
                      :parameters {:form any?}}}]
    ["/signup" {:name :link/signup
                :get  {:handler (fn [req] (signup/signup-form (link/to :link/signup)))}
                :post {:handler (fn [req]
                                  (signup/new-user req)
                                  {:status 301
                                   :headers {"Location" "/"}})
                       :parameters {:form any?}}}]
    ["/silo/activity/" {:name    :link/silo-activity
                        :handler (fn [req] (silos-as-graph req))}]
    ["/silo/example"
     ["" {:name :link/silo-example
          :get  {:handler (fn [req] (demo/silo (link/to :link/get-actor-row
                                                        :link/silo-example)))}
          :post {:handler    (fn [{{form-params :form} :parameters :as req}]
                            (demo/generate (:ctx req)
                                           (link/to :link/app)
                                           form-params))
                 :parameters {:form any?}}}]

     ["/actor" {:name :link/get-actor-row
                :get  {:handler (fn [req] (demo/add-actor (link/to :link/get-actor-row)))}}]]

    ;; Unnecessary stuff
    ["/htmx-library" {:get {:handler (fn [req] (htmx-library req))}}]
    ["/activity" {:get {:handler (fn [req] (activity req))}}]
    ["/htmx-ws" {:get {:handler (fn [req] (htmx-ws req))}}]
    ["/hyperscript" {:get {:handler (fn [req] (hyperscript req))}}]
    ["/htmx-sse" {:get {:handler (fn [req] (htmx-sse req))}}]
    ["/tailwind" {:get {:handler (fn [req] (tailwind req))}}]]
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
