(ns trend.rest-api.core
  (:require
   [trend.rest-api.middleware :as middleware]
   [rum.core :as rum]
   [reitit.coercion.malli :as malli-coercion]
   [ring.util.response :as response]
   [reitit.ring :as ring]))

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

(defn app-load [_req]
  (render-and-respond
   [:html
    [:head
     [:meta {:charset "UTF-8"}]
     [:title "MindHive"]
     [:script {:src "/htmx-library"}]
     [:script {:src "/htmx-ws"}]
     [:script {:src "/htmx-sse"}]
     [:script {:src "/tailwind"}]]
    [:body
[:div {:class "relative isolate bg-white"}
 [:div {:class "mx-auto grid max-w-7xl grid-cols-1 lg:grid-cols-2"}
  [:div {:class "relative px-6 pb-20 pt-24 sm:pt-32 lg:static lg:px-8 lg:py-48"}
   [:div {:class "mx-auto max-w-xl lg:mx-0 lg:max-w-lg"}
    [:div {:class "absolute inset-y-0 left-0 -z-10 w-full overflow-hidden bg-gray-100 ring-1 ring-gray-900/10 lg:w-1/2"}
     [:svg {:class "absolute inset-0 h-full w-full stroke-gray-200
            [mask-image:radial-gradient(100%_100%_at_top_right,white,transparent)]", :aria-hidden "true"}
      [:defs
       [:pattern {:id "83fd4e5a-9d52-42fc-97b6-718e5d7ee527", :width "200", :height "200", :x "100%", :y "-1", :patternunits "userSpaceOnUse"}
        [:path {:d "M130 200V.5M.5 .5H200", :fill "none"}]]]
      [:rect {:width "100%", :height "100%", :stroke-width "0", :fill "white"}]
      [:svg {:x "100%", :y "-1", :class "overflow-visible fill-gray-50"}
       [:path {:d "M-470.5 0h201v201h-201Z", :stroke-width "0"}]]
      [:rect {:width "100%", :height "100%", :stroke-width "0", :fill "url(#83fd4e5a-9d52-42fc-97b6-718e5d7ee527)"}]]]
    [:h2 {:class "text-3xl font-bold tracking-tight text-gray-900"} "Signup"]
    [:p {:class "mt-6 text-lg leading-8 text-gray-600"} "Less noise. Fewer meetings. More context."]
    ]]
  [:form {:action "#", :method "POST", :class "px-6 pb-24 pt-20 sm:pb-32 lg:px-8 lg:py-48"}
   [:div {:class "mx-auto max-w-xl lg:mr-0 lg:max-w-lg"}
    [:div {:class "grid grid-cols-1 gap-x-8 gap-y-6 sm:grid-cols-2"}
     [:div
      [:label {:for "first-name", :class "block text-sm font-semibold leading-6 text-gray-900"} "First name"]
      [:div {:class "mt-2.5"}
       [:input {:type "text", :name "first-name", :id "first-name", :autocomplete "given-name", :class "block w-full rounded-md border-0 px-3.5 py-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"}]]]
     [:div
      [:label {:for "last-name", :class "block text-sm font-semibold leading-6 text-gray-900"} "Last name"]
      [:div {:class "mt-2.5"}
       [:input {:type "text", :name "last-name", :id "last-name", :autocomplete "family-name", :class "block w-full rounded-md border-0 px-3.5 py-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"}]]]
     [:div {:class "sm:col-span-2"}
      [:label {:for "email", :class "block text-sm font-semibold leading-6 text-gray-900"} "Email"]
      [:div {:class "mt-2.5"}
       [:input {:type "email", :name "email", :id "email", :autocomplete "email", :class "block w-full rounded-md border-0 px-3.5 py-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"}]]]
     [:div {:class "sm:col-span-2"}
      [:label {:for "phone-number", :class "block text-sm font-semibold leading-6 text-gray-900"} "Phone number"]
      [:div {:class "mt-2.5"}
       [:input {:type "tel", :name "phone-number", :id "phone-number", :autocomplete "tel", :class "block w-full rounded-md border-0 px-3.5 py-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"}]]]
     [:div {:class "sm:col-span-2"}
      [:label {:for "message", :class "block text-sm font-semibold leading-6 text-gray-900"} "Message"]
      [:div {:class "mt-2.5"}
       [:textarea {:name "message", :id "message", :rows "4", :class "block w-full rounded-md border-0 px-3.5 py-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"}]]]]
    [:div {:class "mt-8 flex justify-end"}
     [:button {:type "submit", :class "rounded-md bg-indigo-600 px-3.5 py-2.5 text-center text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"} "Send message"]]]]]]
     ]]))

(app-load {})

(def route-tree
  (ring/router
   [["/" {:get {:handler (fn [req] (app-load req))}}]

    ;; Unnecessary stuff
    ["/htmx-library" {:get {:handler (fn [req] (htmx-library req))}}]
    ["/htmx-ws" {:get {:handler (fn [req] (htmx-ws req))}}]
    ["/htmx-sse" {:get {:handler (fn [req] (htmx-sse req))}}]
    ["/tailwind" {:get {:handler (fn [req] (tailwind req))}}]


    ]
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
