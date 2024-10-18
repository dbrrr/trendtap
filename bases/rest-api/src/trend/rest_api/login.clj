(ns trend.rest-api.login
  (:require
   [trend.rest-api.common :as common]))

(defn login-page [links]
  (common/render-and-respond
   [:html
    common/head
    [:body
     [:div {:class "flex min-h-full items-center justify-center px-4 py-12 sm:px-6 lg:px-8"}
      [:div {:class "w-full max-w-sm space-y-10"}
       [:div
        [:img {:class "mx-auto h-10 w-auto", :src "https://tailwindui.com/plus/img/logos/mark.svg?color=indigo&shade=600", :alt "Your Company"}]
        [:h2 {:class "mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900"} "Sign in to your account"]]
       [:form {:class "space-y-6", :action (:link/login links), :method "POST"}
        [:div {:class "relative -space-y-px rounded-md shadow-sm"}
         [:div {:class "pointer-events-none absolute inset-0 z-10 rounded-md ring-1 ring-inset ring-gray-300"}]
         [:div
          [:label {:for "email-address", :class "sr-only"} "Email address"]
          [:input {:id "email-address", :name "email", :type "email", :autocomplete "email", :required true, :class "relative block w-full rounded-t-md border-0 py-1.5 px-2.5 text-gray-900 ring-1 ring-inset ring-gray-100 placeholder:text-gray-400 focus:z-10 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6", :placeholder "Email address"}]]]
        [:div
         [:button {:type "submit", :class "flex w-full justify-center rounded-md bg-indigo-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"}
          "Sign in"]]]
       [:p {:class "text-center text-sm leading-6 text-gray-500"} "Not a member?"
        [:a {:href (:link/signup links), :class "pl-2 font-semibold text-indigo-600 hover:text-indigo-500"} "Signup"]]]]]]))
