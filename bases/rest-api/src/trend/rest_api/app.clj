(ns trend.rest-api.app
  (:require
   [trend.rest-api.common :as common]))

(defn load-it [x]
  (common/render-and-respond
   [:html
    common/head
    [:script {:type "module" :src "/activity"}]
    [:body
     [:div {:class "min-h-full"}
      [:nav {:class "bg-gray-800"}
       [:div {:class "mx-auto max-w-7xl px-4 sm:px-6 lg:px-8"}
        [:div {:class "flex h-16 items-center justify-between"}
         [:div {:class "flex items-center"}
          [:div {:class "flex-shrink-0"}
           [:img {:class "h-8 w-8", :src "https://tailwindui.com/plus/img/logos/mark.svg?color=indigo&shade=500", :alt "Your Company"}]]
          [:div {:class "hidden md:block"}
           [:div {:class "ml-10 flex items-baseline space-x-4"}
            [:a {:href "#", :class "rounded-md bg-gray-900 px-3 py-2 text-sm font-medium text-white", :aria-current "page"} "Activity"]
            [:a {:href "#", :class "rounded-md px-3 py-2 text-sm font-medium text-gray-300 hover:bg-gray-700 hover:text-white"} "Insights"]]]]
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
              [:img {:class "h-8 w-8 rounded-full", :src "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80", :alt ""}]]]
            [:div {:class "absolute right-0 z-10 mt-2 w-48 origin-top-right rounded-md bg-white py-1 shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none", :role "menu", :aria-orientation "vertical", :aria-labelledby "user-menu-button", :tabindex "-1"}
             [:a {:href "#", :class "block px-4 py-2 text-sm text-gray-700", :role "menuitem", :tabindex "-1", :id "user-menu-item-1"} "Settings"]]]]]
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
         [:a {:href "#", :class "block rounded-md bg-gray-900 px-3 py-2 text-base font-medium text-white", :aria-current "page"} "Activity"]
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
            [:path {:stroke-linecap "round", :stroke-linejoin "round", :d "M14.857 17.082a23.848 23.848 0 0 0 5.454-1.31A8.967 8.967 0 0 1 18 9.75V9A6 6 0 0 0 6 9v.75a8.967 8.967 0 0 1-2.312 6.022c1.733.64 3.56 1.085 5.455 1.31m5.714 0a24.255 24.255 0 0 1-5.714 0m5.714 0a3 3 0 1 1-5.714 0"}]]]]
         [:div {:class "mt-3 space-y-1 px-2"}
          [:a {:href "#", :class "block rounded-md px-3 py-2 text-base font-medium text-gray-400 hover:bg-gray-700 hover:text-white"} "Your Profile"]
          [:a {:href "#", :class "block rounded-md px-3 py-2 text-base font-medium text-gray-400 hover:bg-gray-700 hover:text-white"} "Settings"]
          [:a {:href "#", :class "block rounded-md px-3 py-2 text-base font-medium text-gray-400 hover:bg-gray-700 hover:text-white"} "Sign out"]]]]]
      [:header {:class "bg-white shadow-sm"}
       [:div {:class "mx-auto max-w-7xl px-4 py-4 sm:px-6 lg:px-8"}
        [:h1 {:class "text-lg font-semibold leading-6 text-gray-900"} "Activity"]]]
      [:main
       [:div {:class "mx-auto max-w-7xl px-4 py-16 sm:px-6 lg:px-8"}
        [:div {:class "mx-auto grid max-w-2xl grid-cols-1 grid-rows-1 items-start gap-x-8 gap-y-8 lg:mx-0 lg:max-w-none lg:grid-cols-2"}
         [:div {:id "activity-container"
                :style {"width" "100%"
                        "position" "absolute"
                        "z-index" 0
                        "height" "500px"
                        "display" "block"}}]

         [:div {:class "lg:col-start-3 lg:row-end-1" :style {"z-index" 100}}
          [:ul {:role "list", :class "grid grid-cols-1 gap-x-6 gap-y-8 lg:grid-cols-1 xl:gap-x-8 w-96"}
           [:li {:class "overflow-hidden rounded-xl border border-gray-200"}
            [:div {:class "flex items-center gap-x-4 border-b border-gray-900/5 bg-gray-50 p-6"}
             [:img {:src "https://tailwindui.com/plus/img/logos/48x48/tuple.svg", :alt "Tuple", :class "h-12 w-12 flex-none rounded-lg bg-white object-cover ring-1 ring-gray-900/10"}]
             [:div {:class "text-sm font-medium leading-6 text-gray-900"} "Tuple"]]
            [:dl {:class "-my-3 divide-y divide-gray-100 px-6 py-4 text-sm leading-6"}
             [:div {:class "flex justify-between gap-x-4 py-3"}
              [:dt {:class "text-gray-500"} "Last invoice"]
              [:dd {:class "text-gray-700"}
               [:time {:datetime "2022-12-13"} "December 13, 2022"]]]
             [:div {:class "flex justify-between gap-x-4 py-3"}
              [:dt {:class "text-gray-500"} "Amount"]
              [:dd {:class "flex items-start gap-x-2"}
               [:div {:class "font-medium text-gray-900"} "$2,000.00"]
               [:div {:class "rounded-md bg-red-50 px-2 py-1 text-xs font-medium text-red-700 ring-1 ring-inset ring-red-600/10"} "Overdue"]]]]]
           [:li {:class "overflow-hidden rounded-xl border border-gray-200"}
            [:div {:class "flex items-center gap-x-4 border-b border-gray-900/5 bg-gray-50 p-6"}
             [:img {:src "https://tailwindui.com/plus/img/logos/48x48/savvycal.svg", :alt "SavvyCal", :class "h-12 w-12 flex-none rounded-lg bg-white object-cover ring-1 ring-gray-900/10"}]
             [:div {:class "text-sm font-medium leading-6 text-gray-900"} "SavvyCal"]]
            [:dl {:class "-my-3 divide-y divide-gray-100 px-6 py-4 text-sm leading-6"}
             [:div {:class "flex justify-between gap-x-4 py-3"}
              [:dt {:class "text-gray-500"} "Last invoice"]
              [:dd {:class "text-gray-700"}
               [:time {:datetime "2023-01-22"} "January 22, 2023"]]]
             [:div {:class "flex justify-between gap-x-4 py-3"}
              [:dt {:class "text-gray-500"} "Amount"]
              [:dd {:class "flex items-start gap-x-2"}
               [:div {:class "font-medium text-gray-900"} "$14,000.00"]
               [:div {:class "rounded-md bg-green-50 px-2 py-1 text-xs font-medium text-green-700 ring-1 ring-inset ring-green-600/20"} "Paid"]]]]]
           [:li {:class "overflow-hidden rounded-xl border border-gray-200"}
            [:div {:class "flex items-center gap-x-4 border-b border-gray-900/5 bg-gray-50 p-6"}
             [:img {:src "https://tailwindui.com/plus/img/logos/48x48/reform.svg", :alt "Reform", :class "h-12 w-12 flex-none rounded-lg bg-white object-cover ring-1 ring-gray-900/10"}]
             [:div {:class "text-sm font-medium leading-6 text-gray-900"} "Reform"]]
            [:dl {:class "-my-3 divide-y divide-gray-100 px-6 py-4 text-sm leading-6"}
             [:div {:class "flex justify-between gap-x-4 py-3"}
              [:dt {:class "text-gray-500"} "Last invoice"]
              [:dd {:class "text-gray-700"}
               [:time {:datetime "2023-01-23"} "January 23, 2023"]]]
             [:div {:class "flex justify-between gap-x-4 py-3"}
              [:dt {:class "text-gray-500"} "Amount"]
              [:dd {:class "flex items-start gap-x-2"}
               [:div {:class "font-medium text-gray-900"} "$7,600.00"]
               [:div {:class "rounded-md bg-green-50 px-2 py-1 text-xs font-medium text-green-700 ring-1 ring-inset ring-green-600/20"} "Paid"]]]]]]]]]

       #_[:div {:class "mx-auto max-w-7xl px-4 py-6 sm:px-6 lg:px-8"}]]]]]))
