(ns trend.completion.core
  (:require [wkok.openai-clojure.api :as api]
            [trend.completion.key :as key]
            [clojure.data.json :as json]))

(defn submit [messages]
  (let [response (api/create-chat-completion {:model "gpt-3.5-turbo"
                                              :messages messages}
                                             {:api-key key/chatgpt-key
                                              :organization "org-mkdt4uzmVrlqEYmodw95hPZn"})
        completion-response (get-in response [:choices 0 :message :content])]
    (json/read-str completion-response :key-fn keyword )))
