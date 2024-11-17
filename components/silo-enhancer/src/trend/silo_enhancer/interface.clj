(ns trend.silo-enhancer.interface
  (:require [trend.transcript.interface :as transcript]
            [trend.completion.interface :as completion]
            [trend.rest-api.system :as system]
            [trend.silo.interface :as silo]))

(def summary-system-message-template "components/silo-enhancer/resources/silo-enhancer/prompt-templates/summary-system-message.moustache")
(def summary-user-message-template "components/silo-enhancer/resources/silo-enhancer/prompt-templates/summary-user-message.moustache")

(defn summarize! [ctx silo]
  (let [transcript (transcript/from-silo silo)
        response (completion/submit
                  (concat [{:role "system" :content (completion/render-prompt summary-system-message-template {})}]
                          [{:role "user" :content (completion/render-prompt summary-user-message-template {:transcript transcript})}]))]

    (silo/set-details! ctx silo (assoc (:details silo) :summary response))))

(def title-system-message-template "components/silo-enhancer/resources/silo-enhancer/prompt-templates/title-system-message.moustache")
(def title-user-message-template "components/silo-enhancer/resources/silo-enhancer/prompt-templates/title-user-message.moustache")

(defn generate-title! [ctx silo]
  (let [transcript (transcript/from-silo silo)
        ;; TODO this won't always be JSON, we need to handle that
        response (completion/submit
                  (concat [{:role "system" :content (completion/render-prompt title-system-message-template {})}]
                          [{:role "user" :content (completion/render-prompt title-user-message-template {:transcript transcript})}]))]
    (silo/set-details! ctx silo (assoc (:details silo) :title response))))
