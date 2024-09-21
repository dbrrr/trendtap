(ns trend.transcript.interface
  (:require [clojure.data.json :as json]
            [clojure.java.io :as io]
            [trend.completion.interface :as completion]
            [trend.actor.interface :as actor]
            [trend.silo.interface :as silo]))

(defn parse-jsonl-file [filename]
  (with-open [reader (io/reader filename)]
    (doall
     (map #(json/read-str % :key-fn keyword)
          (line-seq reader)))))

(defn- extract-woz-formatted-transcript [transcript]
  (let [segments (-> transcript :meeting :transcriptSegments)]
    (apply str (map #(format "%s: %s\n" (:speakerName %) (:text %))
                    segments))))

(defn- summary! [transcript]
  (completion/submit {} (concat [{:role "system" :content "You will receive a chat transcript. Please summarize the main topic of discussion."}]
                                [{:role "user" :content transcript}])))

(defn ingest-file [ctx transcript-format file-path]
  (let [data (parse-jsonl-file file-path)
        formatted-file (extract-woz-formatted-transcript (first data))]
    ;; TODO save file to S3 or some repository, with a unique original index name
    ;; TODO identify duplicate if the same file is uploaded twice
    (let [summarize (summary! formatted-file)
          silo (silo/create! ctx {:transcript-id "bar"})]


      )))
