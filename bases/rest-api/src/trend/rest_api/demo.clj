(ns trend.rest-api.demo
  (:require
   [clojure.data.json :as json]
   [clojure.string :as str]
   [trend.completion.interface :as completion]
   [trend.rest-api.common :as common]
   [trend.silo.interface :as silo]
   [trend.transcript.interface :as transcript]
   [trend.actor.interface :as actor]
   [trend.util.interface :as util]
   [medley.core :as medley]))

(defn add-actor-row [get-actor-row-link]
  [:tr {:id "add-actor"}
   [:td {:class "whitespace-nowrap py-2 pl-4 pr-3 text-sm sm:pl-0"}
    [:div {:class "flex items-center"}
     [:div {:class "relative"}
      [:button {:type "button",
                :hx-get get-actor-row-link
                :hx-target "#add-actor"
                :hx-swap "outerHTML"
                :class "flex block w-full mt-2 rounded-lg border-2 border-dashed border-gray-300 p-2 text-center hover:border-gray-400 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"}
       [:svg {:xmlns "http://www.w3.org/2000/svg", :x "0px", :y "0px", :width "20", :height "20", :viewbox "0 0 16 16"}
        [:circle {:cx "8", :cy "8", :r "8", :fill "#33cc33"}]
        [:polygon {:fill "#fff", :points "12,7 9,7 9,4 7,4 7,7 4,7 4,9 7,9 7,12 9,12 9,9 12,9"}]]
       [:span {:class "mx-2 block text-sm text-gray-900"} "Add coworker"]]]]]
   [:td {:class "whitespace-nowrap px-3 py-2 text-sm text-gray-500"}]])

(defn actor-row [{:keys [id name current-user?]}]
  (let [name-label (if current-user?
                     "You"
                     "Coworker")
        content-label (if current-user?
                        "What did you say?"
                        "What did they say?")
        name-input-id (str "name:" id)
        perspective-input-id (str "perspective:" id)]
    [:tr
     [:td {:class "whitespace-nowrap py-4 pl-4 pr-3 text-sm sm:pl-0"}
      [:div {:class "flex items-center"}
       [:div {:class "relative"}
        [:label {:for "name",
                 :class "absolute -top-2 left-2 inline-block bg-white px-1 text-xs font-medium text-gray-900"} name-label]
        [:input (merge
                 {:type "text",
                  :name name-input-id,
                  :id name-input-id
                  :class "block w-full rounded-md border-0 px-2 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6",
                  :placeholder "Jane Smith"}
                 (when current-user?
                   {:readonly true
                    :value name}))]]]]
     [:td {:class "whitespace-nowrap px-3 py-4 text-sm text-gray-500"}
      [:div {:class "relative"}
       [:label {:for "name", :class "absolute -top-2 left-2 inline-block bg-white px-1 text-xs font-medium text-gray-900"} content-label]
       [:textarea {:rows "2",
                   :name perspective-input-id,
                   :id perspective-input-id,
                   :class "px-2 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"}]]]]))

(defn- silo-description []
  [:div {:class "mt-8"}
   [:h2 {:class "text-l font-bold tracking-tight text-gray-900"} "How would you summarize your last meeting?"]
   [:div {:class "mt-2"}
    [:textarea {:rows "4", :name "description", :id "description", :class "px-2 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"}]]])

(defn add-actor [links]
  (common/respond
   (str
    (common/render (actor-row {}))
    (common/render (add-actor-row (:link/get-actor-row links))))))

(defn silo [links user]
  (common/render-and-respond
   [:html
    common/head
    [:body
     [:form {:action (:link/silo-example links) :method "POST"}
      [:div {:class "mx-auto max-w-2xl mt-20"}
       [:div {:class "divide-y divide-gray-200 overflow-hidden rounded-lg bg-white shadow"}
        [:div {:class "px-4 py-5 sm:p-6"}
         [:div
          [:h2 {:class "text-3xl font-bold tracking-tight text-gray-900"} "Get started with an example"]
          [:p {:class "mt-3 text-md leading-6 text-gray-600"} "We're all about conversational intelligence. Briefly describe your last meeting, and we'll use it as an example."]]

         (silo-description)

         [:div {:class "mt-8 flow-root"}
          [:h2 {:class "text-l font-bold tracking-tight text-gray-900"} "Who participated?"]
          [:div {:class "mt-2 -mx-4 -my-2 overflow-x-auto sm:-mx-6 lg:-mx-8"}
           [:div {:class "inline-block min-w-full py-2 align-middle sm:px-6 lg:px-8"}
            [:table {:class "min-w-full divide-y divide-gray-300"}
             [:tbody {:class "divide-y divide-gray-200 bg-white"}
              (actor-row {:id (random-uuid)
                          :name (str (:first-name (:details user)) " " (:last-name (:details user)))
                          :current-user? true})
              (actor-row {:id (random-uuid)})
              (add-actor-row (:link/get-actor-row links))]]]]]]]

       [:div {:class "px-4 py-4 sm:px-6"}
        [:div {:class "flex justify-end"}
         [:button {:type "submit",
                   :_ "on click set my.innerText to 'Generating...'"
                   :class "rounded-md bg-indigo-600 px-3.5 py-2.5 text-center text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"} "Generate example"]]]]]]]))

(defn- ->participants [participants]
  (vals (reduce-kv (fn [acc element-id element-value]
                     (let [[t k] (str/split (name element-id) #":")]
                       (assoc-in acc [(keyword k) (keyword t)] element-value)))
                   {}
                   participants)))

(def demo-system-message-template "bases/rest-api/resources/rest-api/prompt-templates/demo-system-message.moustache")
(def demo-user-message-template "bases/rest-api/resources/rest-api/prompt-templates/demo-user-message.moustache")

(defn- chat-line [{:keys [participant text]}]
[:li
   [:div {:class "relative pb-8"}
    [:span {:class "absolute left-5 top-5 -ml-px h-full w-0.5 bg-gray-200", :aria-hidden "true"}]
    [:div {:class "relative flex items-start space-x-3"}
     [:div
      [:div {:class "relative px-1"}
       [:div {:class "flex h-8 w-8 items-center justify-center rounded-full bg-gray-100 ring-8 ring-white"}
        [:svg {:class "h-5 w-5 text-gray-500", :viewbox "0 0 20 20", :fill "currentColor", :aria-hidden "true", :data-slot "icon"}
         [:path {:fill-rule "evenodd", :d "M18 10a8 8 0 1 1-16 0 8 8 0 0 1 16 0Zm-5.5-2.5a2.5 2.5 0 1 1-5 0 2.5 2.5 0 0 1 5 0ZM10 12a5.99 5.99 0 0 0-4.793 2.39A6.483 6.483 0 0 0 10 16.5a6.483 6.483 0 0 0 4.793-2.11A5.99 5.99 0 0 0 10 12Z", :clip-rule "evenodd"}]]]]]
     [:div {:class "min-w-0 flex-1"}
      [:div
       [:div {:class "text-sm"}
        [:a {:href "#", :class "font-medium text-gray-900"} participant]]]
      [:div {:class "mt-2 text-sm text-gray-700"}
       [:p text]]]]]])

(defn generate [ctx links form-params]
  (let [participants (->participants (dissoc form-params :description))
        demo (completion/submit
              (concat [{:role "system" :content (completion/render-prompt demo-system-message-template {})}]
                      [{:role "user" :content (completion/render-prompt demo-user-message-template
                                                                        {:description (:description form-params)
                                                                         :participants participants})}]))
        response (json/read-str demo :key-fn keyword)
        {:keys [actors]} (transcript/ingest-demo-format response)
        silo (silo/create! ctx (silo/->meeting-transcript "foobar"))
        actors (map #(actor/create! ctx (util/id silo) {:description %}) actors)
        current-user (medley/find-first #(= (-> % :details :description)
                                            (str (-> ctx :user :details :first-name)
                                                 " "
                                                 (-> ctx :user :details :last-name)))
                                        actors)
        _ (actor/link-to-account ctx (util/id current-user) (util/id (:user ctx)))]
    (common/render-and-respond
     [:html
      common/head
      [:body
       [:div {:class "mx-auto max-w-2xl mt-20"}
        [:div {:class "overflow-hidden rounded-lg bg-white shadow p-10"}
         [:div {:class "px-4 sm:p-6"}
          [:div
          [:h2 {:class "text-3xl font-bold tracking-tight text-gray-900"} "Your short example meeting"]]]
         [:div {:class "flow-root mt-5"}
          [:ul {:role "list", :class "-mb-8"}
           (doall (for [chat (:transcript response)]
                    (chat-line chat)))]]]
        [:div {:class "px-4 py-4 sm:px-6"}
         [:div {:class "flex justify-end"}
          [:form {:action (:link/app links) :method "GET"}
           [:button {:type "submit",
                     :class "rounded-md bg-indigo-600 px-3.5 py-2.5 text-center text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"}
            "Continue"]]]]]]])))
