(ns trend.completion.core
  (:require [wkok.openai-clojure.api :as api]
            [trend.completion.key :as key]))

(defn submit [messages]
  (let [response (api/create-chat-completion {:model "gpt-3.5-turbo"
                                              :messages messages}
                                             {:api-key key/chatgpt-key
                                              :organization "org-mkdt4uzmVrlqEYmodw95hPZn"})]
    (get-in response [:choices 0 :message :content])))
