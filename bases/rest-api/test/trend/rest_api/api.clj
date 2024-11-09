(ns trend.rest-api.api
  (:require  [clojure.test :refer :all]
             [trend.rest-api.core :as core]
             [reitit.core :as r]
             [hickory.core :as h]
             [hickory.select :as hs]
             [ring.mock.request :as mr]
             [clojure.string :as str]))

(defn- get-login-page []
  (-> (mr/request :get (:path (r/match-by-name core/route-tree :link/login)))
      (core/handler)
      :body))

(defn- extract-login-form [login-page]
  (->> (h/parse login-page)
       (h/as-hickory)
       (hs/select (hs/tag :form))
       first))

(defn login [user-email]
  ;; TODO this won't work like this longterm
  (let [login-form (-> (get-login-page)
                       (extract-login-form))
        form-inputs (hs/select (hs/tag :input) login-form)
        form-action (-> login-form :attrs :action)
        form-method (-> login-form :attrs :method)
        email-input (first form-inputs)
        form-params {(-> email-input :attrs :name) user-email}]
    (assert (= 1 (count form-inputs)))
    (-> (mr/request (keyword (str/lower-case form-method)) form-action)
        (mr/body form-params)
        (core/handler))))

(defn secured-handler [user-email]
  (let [session-cookie (:session (:cookies (login user-email)))]
    (fn [req]
      (-> req
          (assoc :cookies {:session session-cookie})
          (core/handler)))))

(defn homepage []
  (mr/request :get (:path (r/match-by-name core/route-tree :link/home))))
