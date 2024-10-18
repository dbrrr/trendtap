(ns trend.rest-api.middleware
  (:require
   [camel-snake-kebab.core :as csk]
   [muuntaja.core :as muun]
   [muuntaja.format.form :as muun-form]
   [reitit.ring.coercion :as rrc]
   [reitit.ring.middleware.parameters :as parameters]
   [trend.rest-api.system :as system]))

(def m
  (muun/create
   (-> muun/default-options
       (assoc-in
        [:formats "application/json" :decoder-opts]
        {:decode-key-fn csk/->kebab-case-keyword})
       (assoc-in [:formats "application/x-www-form-urlencoded"] muun-form/format)
       (assoc-in
        [:formats "application/json" :encoder-opts]
        {:encode-key-fn csk/->snake_case}))))

(defn wrap-system [handler]
  (fn [req]
    (handler (assoc req :ctx @system/system))))

(def stack [parameters/parameters-middleware
            rrc/coerce-request-middleware
            wrap-system])
