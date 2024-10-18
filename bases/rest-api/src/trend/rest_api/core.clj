(ns trend.rest-api.core
  (:require
   [trend.rest-api.middleware :as middleware]
   [reitit.coercion.malli :as malli-coercion]
   [reitit.ring :as ring]
   [trend.rest-api.signup :as signup]
   [trend.rest-api.login :as login]
   [trend.rest-api.link :as link]))

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

(defn tailwind [_req]
  {:status 200
   :headers {"Content-type" "text/css"}
   :body (slurp "bases/rest-api/resources/rest-api/tailwind.js")})

;; NOTE don't change this name, or link/to will break
(link/routes route-tree
  (ring/router
   [["/" {:get {:handler (fn [req] req)}}]
    ["/login" {:name :link/login
               :get {:handler (fn [req]
                                (login/login-page (link/to :link/login
                                                           :link/signup)))}}]
    ["/signup" {:name :link/signup
                :get {:handler (fn [req] (signup/signup-form))}
                :post {:handler (fn [req] (signup/new-user req))
                       :parameters {:form any?}}}]
    ;; Unnecessary stuff
    ["/htmx-library" {:get {:handler (fn [req] (htmx-library req))}}]
    ["/htmx-ws" {:get {:handler (fn [req] (htmx-ws req))}}]
    ["/htmx-sse" {:get {:handler (fn [req] (htmx-sse req))}}]
    ["/tailwind" {:get {:handler (fn [req] (tailwind req))}}]]
   {:data {:coercion malli-coercion/coercion
           :middleware middleware/stack}}))

(defn default-404 [req]
  {:status 404
   :body "fuckoff!"})

(def handler
  (ring/ring-handler
   route-tree
   default-404
   []))
