(ns trend.rest-api.demo
  (:require [trend.rest-api.common :as common]))

(defn silo []
  (common/render-and-respond
   [:html
    common/head
    [:body
     [:form {:action "#" :method "POST"}
      [:div {:class "mx-auto max-w-2xl mt-20"}
       [:div {:class "divide-y divide-gray-200 overflow-hidden rounded-lg bg-white shadow"}
        [:div {:class "px-4 py-5 sm:p-6"}
         [:div
          [:h2 {:class "text-3xl font-bold tracking-tight text-gray-900"} "Get started with an example"]
          [:p {:class "mt-3 text-md leading-6 text-gray-600"} "We're all about conversational intelligence. Briefly describe your last meeting, and we'll use it as an example."]]

         [:div {:class "mt-8"}
          [:h2 {:class "text-l font-bold tracking-tight text-gray-900"} "How would you summarize your last meeting?"]
          [:div {:class "mt-2"}
           [:textarea {:rows "4", :name "comment", :id "comment", :class "px-2 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"}]]]

         [:div {:class "mt-8 flow-root"}
          [:h2 {:class "text-l font-bold tracking-tight text-gray-900"} "Who participated?"]
          [:div {:class "mt-2 -mx-4 -my-2 overflow-x-auto sm:-mx-6 lg:-mx-8"}
           [:div {:class "inline-block min-w-full py-2 align-middle sm:px-6 lg:px-8"}
            [:table {:class "min-w-full divide-y divide-gray-300"}
             [:tbody {:class "divide-y divide-gray-200 bg-white"}
              [:tr
               [:td {:class "whitespace-nowrap pt-2 pb-3 pl-4 pr-3 text-sm sm:pl-0"}
                [:div {:class "flex items-center"}
                 [:div {:class "relative"}
                  [:label {:for "name", :class "absolute -top-2 left-2 inline-block bg-white px-1 text-xs font-medium text-gray-900"} "You"]
                  [:input {:type "text",
                           :name "name",
                           :value "David Russell",
                           :disabled true
                           :id "name",
                           :class "block w-full rounded-md border-0 px-2 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"}]]]]
               [:td {:class "whitespace-nowrap px-3 pb-3 pt-2 text-sm text-gray-500"}
                [:div {:class "relative"}
                 [:label {:for "name", :class "absolute -top-2 left-2 inline-block bg-white px-1 text-xs font-medium text-gray-900"} "What did you say?"]
                 [:textarea {:rows "2", :name "comment", :id "comment", :class "px-2 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"}]
                 ]]]

              [:tr
               [:td {:class "whitespace-nowrap py-4 pl-4 pr-3 text-sm sm:pl-0"}
                [:div {:class "flex items-center"}
                 [:div {:class "relative"}
                  [:label {:for "name", :class "absolute -top-2 left-2 inline-block bg-white px-1 text-xs font-medium text-gray-900"} "Coworker"]
                  [:input {:type "text", :name "name", :id "name", :class "block w-full rounded-md border-0 px-2 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6", :placeholder "Jane Smith"}]]
                 ]]
               [:td {:class "whitespace-nowrap px-3 py-4 text-sm text-gray-500"}
                [:div {:class "relative"}
                 [:label {:for "name", :class "absolute -top-2 left-2 inline-block bg-white px-1 text-xs font-medium text-gray-900"} "What did they say?"]
                 [:textarea {:rows "2", :name "comment", :id "comment", :class "px-2 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"}]
                 ]]]

              [:tr
                 [:td {:class "whitespace-nowrap py-2 pl-4 pr-3 text-sm sm:pl-0"}
                  [:div {:class "flex items-center"}
                   [:div {:class "relative"}
                    [:button {:type "button", :class "flex block w-full mt-2 rounded-lg border-2 border-dashed border-gray-300 p-2 text-center hover:border-gray-400 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"}
                     [:svg {:xmlns "http://www.w3.org/2000/svg", :x "0px", :y "0px", :width "20", :height "20", :viewbox "0 0 16 16"}
                      [:circle {:cx "8", :cy "8", :r "8", :fill "#33cc33"}]
                      [:polygon {:fill "#fff", :points "12,7 9,7 9,4 7,4 7,7 4,7 4,9 7,9 7,12 9,12 9,9 12,9"}]]
                     [:span {:class "mx-2 block text-sm text-gray-900"} "Add coworker"]]
                   ]]
                  ]
               [:td {:class "whitespace-nowrap px-3 py-2 text-sm text-gray-500"}
                ]

               ]

              ]]



            ]]]]

        [:div {:class "px-4 py-4 sm:px-6"}
         [:div {:class "flex justify-end"}
          [:button {:type "submit", :class "rounded-md bg-indigo-600 px-3.5 py-2.5 text-center text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"} "Generate example"]]]]]]]]))
