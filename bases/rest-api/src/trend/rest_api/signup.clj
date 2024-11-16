(ns trend.rest-api.signup
  (:require
   [trend.rest-api.common :as common]
   [trend.account.interface :as account]))

(defn new-user [{{{:keys [first-name last-name email]} :form} :parameters
                 ctx :ctx}]
  (let [account (account/create! ctx email {:first-name first-name
                                            :last-name last-name
                                            :profile-url "https://images.unsplash.com/photo-1519244703995-f4e0f30006d5?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80"})]
    account
    #_(common/render-and-respond
     [:html
      common/head
      [:body
       [:form {:action "/signup" :method "POST"}
        [:div {:class "mx-auto max-w-2xl mt-20"}
         [:div {:class "divide-y divide-gray-200 overflow-hidden rounded-lg bg-white shadow p-5"}
          "Success! Please check your email"]]]]])))

(defn signup-form [links]
  (common/render-and-respond
   [:html
    common/head
    [:body
     [:form {:action (:link/signup links) :method "POST"}
      [:div {:class "mx-auto max-w-2xl mt-20"}
       [:div {:class "divide-y divide-gray-200 overflow-hidden rounded-lg bg-white shadow"}
        [:div {:class "px-4 py-5 sm:p-6"}
         [:div {:class "pb-12"}
          [:h2 {:class "text-3xl font-bold tracking-tight text-gray-900"} "Signup"]
          [:div {:class "mt-10 grid grid-cols-1 gap-x-6 gap-y-8 sm:grid-cols-6"}
           [:div {:class "sm:col-span-3"}
            [:label {:for "first-name", :class "block text-sm font-medium leading-6 text-gray-900"} "First name"]
            [:div {:class "mt-2"}
             [:input {:type "text", :name "first-name", :id "first-name", :autocomplete "given-name", :class "block w-full rounded-md border-0 px-2.5 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"}]]]
           [:div {:class "sm:col-span-3"}
            [:label {:for "last-name", :class "block text-sm font-medium leading-6 text-gray-900"} "Last name"]
            [:div {:class "mt-2"}
             [:input {:type "text", :name "last-name", :id "last-name", :autocomplete "family-name", :class "block w-full rounded-md border-0 px-2.5 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"}]]]
           [:div {:class "sm:col-span-4"}
            [:label {:for "email", :class "block text-sm font-medium leading-6 text-gray-900"} "Email address"]
            [:div {:class "mt-2"}
             [:input {:id "email", :name "email", :type "email", :autocomplete "email", :class "block w-full rounded-md border-0 px-2.5 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"}]]]
           [:div {:class "sm:col-span-3"}
            [:label {:for "country", :class "block text-sm font-medium leading-6 text-gray-900"} "Role"]
            [:div {:class "mt-2"}
             [:select {:id "country", :name "country", :autocomplete "country-name", :class "block w-full rounded-md border-0 px-2.5 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:max-w-xs sm:text-sm sm:leading-6"}
              [:option "Product"]
              [:option "Engineering"]
              [:option "Sales"]]]]
           [:div {:class "sm:col-span-3"}
            [:label {:for "country", :class "block text-sm font-medium leading-6 text-gray-900"} "Organization Size"]
            [:div {:class "mt-2"}
             [:select {:id "country", :name "country", :autocomplete "country-name", :class "block w-full rounded-md border-0 px-2.5 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:max-w-xs sm:text-sm sm:leading-6"}
              [:option "1-10"]
              [:option "10-100"]
              [:option "100-200"]
              [:option "200-500"]
              [:option "500-1000"]
              [:option "1000-10000"]
              [:option "10000+"]]]]]]]
        [:div {:class "px-4 py-4 sm:px-6"}
         [:div {:class "flex justify-end"}
          [:button {:type "submit", :class "rounded-md bg-indigo-600 px-3.5 py-2.5 text-center text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"} "Signup"]]]]]]]]))
