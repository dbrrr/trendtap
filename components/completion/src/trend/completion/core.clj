(ns trend.completion.core
  (:require [wkok.openai-clojure.api :as api]
            [trend.completion.key :as key]))

(def default-feedback-prompt
  [{:role "system" :content "You are a helpful assistant, focused on gathering product feedback."}])

(defn submit [ctx messages]
  (let [response (api/create-chat-completion {:model "gpt-3.5-turbo"
                                              :messages messages}
                                             {:api-key key/chatgpt-key
                                              :organization "org-mkdt4uzmVrlqEYmodw95hPZn"})
        completion-response (get-in response [:choices 0 :message :content])]
      completion-response))
