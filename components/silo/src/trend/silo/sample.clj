(ns trend.silo.sample
  (:require [trend.completion.interface :as completion]
            [trend.transcript.interface :as transcript]))

(defprotocol Sampler
  (-sample [sampler silo])
  (-key [sampler]))

(defrecord Summarizer []
    Sampler
    (-sample [_ silo]
      (let [transcript (transcript/from-silo silo)]
        (completion/submit
         (concat [{:role "system" :content "You will receive a chat transcript. Please summarize the most important topics of the discussion."}]
                 [{:role "user" :content transcript}]))))
    (-key [_] "summarizer-sampler"))

(def summarizer (Summarizer.))
