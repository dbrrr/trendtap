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

(defn respond [rendered-html]
  (response/response rendered-html))

(defn render-and-respond [html]
  (-> html
      render
      respond))

(def head
  [:head
   [:meta {:charset "UTF-8"}]
   [:title "MindHive"]
   [:script {:src "/htmx-library"}]
   [:script {:src "/htmx-ws"}]
   [:script {:src "/hyperscript"}]
   [:script {:src "/htmx-sse"}]
   [:script {:src "/tailwind"}]
   [:link {:rel "stylesheet" :href "/common.css"}]])
