(ns trend.transcript.interface
  (:require [clojure.data.json :as json]
            [clojure.java.io :as io]
            [trend.completion.interface :as completion]))

(defn parse-jsonl-file [filename]
  (with-open [reader (io/reader filename)]
    (doall
     (map #(json/read-str % :key-fn keyword)
          (line-seq reader)))))

(defn- extract-woz-formatted-transcript [transcript]
  (let [segments (-> transcript :meeting :transcriptSegments)]
    (apply str (map #(format "%s: %s\n" (:speakerName %) (:text %))
                    segments))))

(defn ingest-file [ctx transcript-format file-path]
  (let [data (parse-jsonl-file file-path)
        formatted-file (extract-woz-formatted-transcript (first data))]
    ;; TODO save file to S3 or some repository, with a unique original index name
    ;; TODO identify duplicate if the same file is uploaded twice
    (completion/submit {} (concat [{:role "system" :content "You will receive a chat transcript. Please tell me what the main challenge in the conversation is."}]
                                  [{:role "user" :content formatted-file}]))
    #_(completion/submit {} (concat [{:role "system" :content "Please summarize what the Project Manager is talking about in this conversation"}]
                                    [{:role "user" :content formatted-file}]))

    ;; Create silo record corresponding to this meeting
    ;; Offer suggestions for the actors against existing actors
    ;; Tie the actors to the silo, with summaries

    ))

(println "---")
(println (ingest-file {} {} "/Users/dave/Documents/trendtap_top/MISeD/woz/woz.jsonl"))
