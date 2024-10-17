(ns trend.rest-api.common
  (:require
   [rum.core :as rum]
   [ring.util.response :as response]))

(rum/defc rum-wrap [html]
  html)

(defn render [html]
  (-> html
      rum-wrap
      (rum/render-html)))

(defn render-and-respond [html]
  (-> html
      render
      (response/response)))

(def head
  [:head
   [:meta {:charset "UTF-8"}]
   [:title "MindHive"]
   [:script {:src "/htmx-library"}]
   [:script {:src "/htmx-ws"}]
   [:script {:src "/htmx-sse"}]
   [:script {:src "/tailwind"}]])
