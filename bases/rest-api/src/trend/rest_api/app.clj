(ns trend.rest-api.app
  (:require
   [trend.rest-api.common :as common]
   [trend.silo.interface :as silo]
   [trend.actor.interface :as actor]
   [medley.core :as medley]
   [trend.util.interface :as util]))

(def silo-insight-panel "silo-detail-floating-window")

(defn- insight-panel-actor [silo-id actor active?]
  (let [active-classes "bg-gray-100"]
    [:li {:hx-trigger "mouseover"
          :hx-target (format "#%s" "silo-detail-floating-window-actor-details")
          :hx-swap "outerHTML"
          :hx-get (format "/silo/%s/insights/actor/%s" silo-id (util/id actor))
          :_ "on mouseenter remove .bg-gray-100 from .panel-actor then add .bg-gray-100 to me"
          :class (cond-> "panel-actor group flex cursor-default select-none items-center rounded-md p-2"
                   active? (str " " active-classes)), :id "option-1", :role "option", :tabindex "-1"}
     [:span {:class "inline-block h-6 w-6 overflow-hidden rounded-full bg-gray-100"}
      [:svg {:class "h-full w-full text-gray-300", :fill "currentColor", :viewbox "0 0 24 24"}
       [:path {:d "M24 20.993V24H0v-2.996A14.977 14.977 0 0112.004 15c4.904 0 9.26 2.354 11.996 5.993zM16.002 8.999a4 4 0 11-8 0 4 4 0 018 0z"}]]]
     [:span {:class "ml-3 flex-auto truncate"} (-> actor :details :description)]
     [:svg {:class (cond-> "ml-3 size-2 flex-none text-gray-400"
                     true (str " hidden")), :viewbox "0 0 20 20", :fill "currentColor", :aria-hidden "true", :data-slot "icon"}
      [:path {:fill-rule "evenodd", :d "M8.22 5.22a.75.75 0 0 1 1.06 0l4.25 4.25a.75.75 0 0 1 0 1.06l-4.25 4.25a.75.75 0 0 1-1.06-1.06L11.94 10 8.22 6.28a.75.75 0 0 1 0-1.06Z", :clip-rule "evenodd"}]]]))

(defn insight-panel-actor-section [actor]
  [:div {:id "silo-detail-floating-window-actor-details"
         :class "hidden h-96 w-1/2 flex-none flex-col divide-y divide-gray-100 overflow-y-auto sm:flex"}
   [:div {:class "flex-none p-6 text-center"}
    [:span {:class "inline-block h-14 w-14 overflow-hidden rounded-full bg-gray-100"}
     [:svg {:class "h-full w-full text-gray-300", :fill "currentColor", :viewbox "0 0 24 24"}
      [:path {:d "M24 20.993V24H0v-2.996A14.977 14.977 0 0112.004 15c4.904 0 9.26 2.354 11.996 5.993zM16.002 8.999a4 4 0 11-8 0 4 4 0 018 0z"}]]]
    [:h2 {:class "mt-3 font-semibold text-gray-900"} (-> actor :details :description)]
    [:p {:class "text-sm/6 text-gray-500"} "Director, Product Development"]]
   [:div {:class "flex flex-auto flex-col justify-between p-6"}
    [:button {:type "button",
              :class "mt-6 w-full rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
              :hx-get "/new-content"
              :hx-swap "innerHTML transition:true"
              :hx-target "#swap-me"}
     "Earn Points"]]])

(defn silo-insights-panel [ctx silo-id]
  (let [silo (silo/by-id! ctx silo-id)
        actors (actor/by-silo! ctx silo)]
    [:div {:class "z-10 mx-auto max-w-3xl transform overflow-hidden rounded-xl opacity-90 bg-white shadow-2xl ring-1 ring-black ring-opacity-5 transition-all"}
     [:div {:class "relative p-5 flex"}
      [:h1 {:class "text-xl font-semibold leading-6 text-gray-900"} "Meeting Title"]
      [:div {:class "ml-auto"}
       [:div {:class "-mx-1.5 -my-1.5"}
        [:button {:type "button",
                  :id "silo-detail-floating-window-close"
                  :class "inline-flex rounded-md bg-gray-50 p-1.5 text-gray-500 hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-gray-600 focus:ring-offset-2 focus:ring-offset-gray-50"
                  :_ (format "on click add .hidden to #%s then call window.clearCytoscapeFocus() end" silo-insight-panel)}
         [:span {:class "sr-only"} "Dismiss"]
         [:svg {:class "h-5 w-5", :viewbox "0 0 20 20", :fill "currentColor", :aria-hidden "true", :data-slot "icon"}
          [:path {:d "M6.28 5.22a.75.75 0 0 0-1.06 1.06L8.94 10l-3.72 3.72a.75.75 0 1 0 1.06 1.06L10 11.06l3.72 3.72a.75.75 0 1 0 1.06-1.06L11.06 10l3.72-3.72a.75.75 0 0 0-1.06-1.06L10 8.94 6.28 5.22Z"}]]]]]]
     [:div {:class "slide-it divide-y divide-gray-100" :id "swap-me"}
      [:div {:class "px-6 py-14 text-center text-sm sm:px-14"}
       [:svg {:class "mx-auto h-6 w-6 text-gray-400", :fill "none", :viewbox "0 0 24 24", :stroke-width "1.5", :stroke "currentColor", :aria-hidden "true", :data-slot "icon"}
        [:path {:stroke-linecap "round", :stroke-linejoin "round", :d "M15 19.128a9.38 9.38 0 0 0 2.625.372 9.337 9.337 0 0 0 4.121-.952 4.125 4.125 0 0 0-7.533-2.493M15 19.128v-.003c0-1.113-.285-2.16-.786-3.07M15 19.128v.106A12.318 12.318 0 0 1 8.624 21c-2.331 0-4.512-.645-6.374-1.766l-.001-.109a6.375 6.375 0 0 1 11.964-3.07M12 6.375a3.375 3.375 0 1 1-6.75 0 3.375 3.375 0 0 1 6.75 0Zm8.25 2.25a2.625 2.625 0 1 1-5.25 0 2.625 2.625 0 0 1 5.25 0Z"}]]
       [:p {:class "mt-4 font-semibold text-gray-900"} "No people found"]
       [:p {:class "mt-2 text-gray-500"} "We couldn’t find anything with that term. Please try again."]]
      [:div {:id "silo-insights-actor-panel"
             :class "flex transform-gpu divide-x divide-gray-100"}
       [:div {:class "max-h-96 min-w-0 flex-auto scroll-py-4 overflow-y-auto px-6 py-4 sm:h-96"}
        [:h2 {:class "mb-4 mt-2 text-xs font-semibold text-gray-500"} "Participants"]
        [:ul {:class "-mx-2 text-sm text-gray-700", :id "options", :role "listbox"}
         (insight-panel-actor silo-id (first actors) :active)
         (for [actor (rest actors)]
           (insight-panel-actor silo-id actor false))]]
       (insight-panel-actor-section (first actors))]]]))

(defn moar [req]
  (common/render-and-respond
   [:div {:class "px-6 py-14 text-center text-sm sm:px-14"}
    [:svg {:class "mx-auto h-6 w-6 text-gray-400", :fill "none", :viewbox "0 0 24 24", :stroke-width "1.5", :stroke "currentColor", :aria-hidden "true", :data-slot "icon"}
     [:path {:stroke-linecap "round", :stroke-linejoin "round", :d "M15 19.128a9.38 9.38 0 0 0 2.625.372 9.337 9.337 0 0 0 4.121-.952 4.125 4.125 0 0 0-7.533-2.493M15 19.128v-.003c0-1.113-.285-2.16-.786-3.07M15 19.128v.106A12.318 12.318 0 0 1 8.624 21c-2.331 0-4.512-.645-6.374-1.766l-.001-.109a6.375 6.375 0 0 1 11.964-3.07M12 6.375a3.375 3.375 0 1 1-6.75 0 3.375 3.375 0 0 1 6.75 0Zm8.25 2.25a2.625 2.625 0 1 1-5.25 0 2.625 2.625 0 0 1 5.25 0Z"}]]
    [:p {:class "mt-4 font-semibold text-gray-900"} "No people found"]
    [:p {:class "mt-2 text-gray-500"} "We couldn’t find anything with that term. Please try again."]]))

(defn- main-ticker-silo-item [silo silo-id->actor]
  (let [silo-id (util/id silo)
        silo-actors (get silo-id->actor (util/id silo))]
    [:li {:class "flex items-center justify-between gap-x-6 py-5 siloItem"
          :id (util/id silo)
          :style {"z-index" 200}
          :hx-target (format "#%s" silo-insight-panel)
          :hx-get (format "/silo/%s/insights" silo-id)}
     [:div {:class "min-w-0"}
      [:div {:class "flex items-start gap-x-3"}
       [:p {:class "text-sm/6 font-semibold text-gray-900"} "New benefits plan"]
       [:p {:class "mt-0.5 whitespace-nowrap rounded-md bg-gray-50 px-1.5 py-0.5 text-xs font-medium text-gray-600 ring-1 ring-inset ring-gray-500/10"} "In progress"]]
      [:div {:class "mt-1 flex items-center gap-x-2 text-xs/5 text-gray-500"}
       [:p {:class "whitespace-nowrap"}
        [:time {:datetime "2023-05-05T00:00Z"} "May 5, 2023"]]
       [:svg {:viewbox "0 0 2 2", :class "h-0.5 w-0.5 fill-current"}
        [:circle {:cx "1", :cy "1", :r "1"}]]
       [:p {:class "truncate"} (format "%s participants" (count silo-actors))]]]
     [:div {:class "flex flex-none items-center gap-x-4"}
      [:a {:href "#", :class "hidden rounded-md bg-white px-2.5 py-1.5 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50 sm:block"} "View project"
       [:span {:class "sr-only"} ", New benefits plan"]]]]))

(defn load-it [{:keys [ctx] :as req}]
  (let [silos (silo/all! ctx)
        silo-id->actor (some->> (seq silos)
                                (actor/by-silos! ctx)
                                (group-by (comp str :silo-id)))]
    (common/render-and-respond
     [:html
      common/head
      [:script {:type "module" :src "/activity"}]
      [:body
       [:div {:class "min-h-full"}
        [:nav {:class "bg-gray-800 z-20"}
         [:div {:class "mx-auto max-w-7xl px-4 sm:px-6 lg:px-8"}
          [:div {:class "flex h-16 items-center justify-between"}
           [:div {:class "flex items-center"}
            [:div {:class "flex-shrink-0"}
             [:img {:class "h-8 w-8", :src "https://tailwindui.com/plus/img/logos/mark.svg?color=indigo&shade=500", :alt "Your Company"}]]
            [:div {:class "hidden md:block"}
             [:div {:class "ml-10 flex items-baseline space-x-4"}
              [:a {:href "#", :class "rounded-md bg-gray-900 px-3 py-2 text-sm font-medium text-white", :aria-current "page"} "Community"]
              [:a {:href "#", :class "rounded-md px-3 py-2 text-sm font-medium text-gray-300 hover:bg-gray-700 hover:text-white"} "Insights"]
              [:a {:href "#", :class "rounded-md px-3 py-2 text-sm font-medium text-gray-300 hover:bg-gray-700 hover:text-white"} "Alerts"]]]]
           [:div {:class "hidden md:block"}
            [:div {:class "ml-4 flex items-center md:ml-6"}
             [:button {:type "button", :class "relative rounded-full bg-gray-800 p-1 text-gray-400 hover:text-white focus:outline-none focus:ring-2 focus:ring-white focus:ring-offset-2 focus:ring-offset-gray-800"}
              [:span {:class "absolute -inset-1.5"}]
              [:span {:class "sr-only"} "View notifications"]
              [:svg {:class "h-6 w-6", :fill "none", :viewbox "0 0 24 24", :stroke-width "1.5", :stroke "currentColor", :aria-hidden "true", :data-slot "icon"}
               [:path {:stroke-linecap "round", :stroke-linejoin "round", :d "M14.857 17.082a23.848 23.848 0 0 0 5.454-1.31A8.967 8.967 0 0 1 18 9.75V9A6 6 0 0 0 6 9v.75a8.967 8.967 0 0 1-2.312 6.022c1.733.64 3.56 1.085 5.455 1.31m5.714 0a24.255 24.255 0 0 1-5.714 0m5.714 0a3 3 0 1 1-5.714 0"}]]]
             [:div {:class "relative ml-3"}
              [:div
               [:button {:type "button", :class "relative flex max-w-xs items-center rounded-full bg-gray-800 text-sm text-white focus:outline-none focus:ring-2 focus:ring-white focus:ring-offset-2 focus:ring-offset-gray-800", :id "user-menu-button", :aria-expanded "false", :aria-haspopup "true"}
                [:span {:class "absolute -inset-1.5"}]
                [:span {:class "sr-only"} "Open user menu"]
                [:img {:class "h-8 w-8 rounded-full", :src "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80", :alt ""}]]]]]]
           [:div {:class "-mr-2 flex md:hidden"}
            [:button {:type "button", :class "relative inline-flex items-center justify-center rounded-md bg-gray-800 p-2 text-gray-400 hover:bg-gray-700 hover:text-white focus:outline-none focus:ring-2 focus:ring-white focus:ring-offset-2 focus:ring-offset-gray-800", :aria-controls "mobile-menu", :aria-expanded "false"}
             [:span {:class "absolute -inset-0.5"}]
             [:span {:class "sr-only"} "Open main menu"]
             [:svg {:class "block h-6 w-6", :fill "none", :viewbox "0 0 24 24", :stroke-width "1.5", :stroke "currentColor", :aria-hidden "true", :data-slot "icon"}
              [:path {:stroke-linecap "round", :stroke-linejoin "round", :d "M3.75 6.75h16.5M3.75 12h16.5m-16.5 5.25h16.5"}]]
             [:svg {:class "hidden h-6 w-6", :fill "none", :viewbox "0 0 24 24", :stroke-width "1.5", :stroke "currentColor", :aria-hidden "true", :data-slot "icon"}
              [:path {:stroke-linecap "round", :stroke-linejoin "round", :d "M6 18 18 6M6 6l12 12"}]]]]]]
         [:div {:class "md:hidden", :id "mobile-menu"}
          [:div {:class "space-y-1 px-2 pb-3 pt-2 sm:px-3"}
           [:a {:href "#", :class "block rounded-md bg-gray-900 px-3 py-2 text-base font-medium text-white", :aria-current "page"} "Community"]
           [:a {:href "#", :class "block rounded-md px-3 py-2 text-base font-medium text-gray-300 hover:bg-gray-700 hover:text-white"} "Insights"]]
          [:div {:class "border-t border-gray-700 pb-3 pt-4"}
           [:div {:class "flex items-center px-5"}
            [:div {:class "flex-shrink-0"}
             [:img {:class "h-10 w-10 rounded-full", :src "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80", :alt ""}]]
            [:div {:class "ml-3"}
             [:div {:class "text-base font-medium text-white"} "Tom Cook"]
             [:div {:class "text-sm font-medium text-gray-400"} "tom@example.com"]]
            [:button {:type "button", :class "relative ml-auto flex-shrink-0 rounded-full bg-gray-800 p-1 text-gray-400 hover:text-white focus:outline-none focus:ring-2 focus:ring-white focus:ring-offset-2 focus:ring-offset-gray-800"}
             [:span {:class "absolute -inset-1.5"}]
             [:span {:class "sr-only"} "View notifications"]
             [:svg {:class "h-6 w-6", :fill "none", :viewbox "0 0 24 24", :stroke-width "1.5", :stroke "currentColor", :aria-hidden "true", :data-slot "icon"}
              [:path {:stroke-linecap "round", :stroke-linejoin "round", :d "M14.857 17.082a23.848 23.848 0 0 0 5.454-1.31A8.967 8.967 0 0 1 18 9.75V9A6 6 0 0 0 6 9v.75a8.967 8.967 0 0 1-2.312 6.022c1.733.64 3.56 1.085 5.455 1.31m5.714 0a24.255 24.255 0 0 1-5.714 0m5.714 0a3 3 0 1 1-5.714 0"}]]]]]]]
        [:header {:class "bg-white shadow-sm z-20"}
         [:div {:class "mx-auto max-w-7xl px-4 py-4 sm:px-6 lg:px-8"}
          [:h1 {:class "text-lg font-semibold leading-6 text-gray-900"} "Community"]]]
        [:main
         [:div {:class "mx-auto"}
          [:div {:class "mx-auto grid max-w-2xl grid-cols-1 grid-rows-1 items-start gap-x-8 gap-y-8 lg:mx-0 lg:max-w-none lg:grid-cols-2"}
           [:div {:id silo-insight-panel
                  :class "z-10 hidden"
                  :style {"position" "absolute"}}
            (silo-insights-panel ctx (util/id (first silos)))]

           [:div {:id "activity-container"
                  :style {"width" "100%"
                          "height" "100%"
                          "position" "absolute"
                          "z-index" 0
                          "display" "block"}}]
           [:canvas {:id "graphCanvas"}]
           [:div {:class "p-5 bg-opacity-80 shadow-sm ring-1 ring-gray-900/5 lg:col-start-3 lg:row-end-1 overflow-y-scroll" :style {"z-index" 100 "height" "800px"}}
            [:ul {:role "list",
                  :class "divide-y divide-gray-100"}
             (doall
              (for [silo (reverse (sort-by :created silos))]
                (main-ticker-silo-item silo silo-id->actor)))]]]]

         #_[:div {:class "mx-auto max-w-7xl px-4 py-6 sm:px-6 lg:px-8"}]]]]])))
