(ns trend.rest-api.middleware
  (:require
   [camel-snake-kebab.core :as csk]
   [muuntaja.core :as muun]
   [muuntaja.format.form :as muun-form]
   [reitit.ring.coercion :as rrc]
   [reitit.ring.middleware.parameters :as parameters]
   [trend.rest-api.system :as system]
   [ring.middleware.cookies :as cook]
   [trend.account.interface :as account]))

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

(defn wrap-auth [handler]
  (fn [req]
    (if (:user (:ctx req))
      (handler req)
      {:status 301
       :headers {"Location" "/login"}})))

(defn wrap-session [handler]
  (fn [req]
    (let [session-cookie (get-in req [:cookies "session" :value])
          account (account/by-email! (:ctx req) session-cookie)
          new-ctx (if account
                    (assoc (:ctx req) :user account)
                    (:ctx req))]
      (handler (assoc req :ctx new-ctx)))))

(defn wrap-system [handler]
  (fn [req]
    (handler (assoc req :ctx @system/system))))

(def stack [parameters/parameters-middleware
            rrc/coerce-request-middleware
            wrap-system
            cook/wrap-cookies
            wrap-session
            ])
