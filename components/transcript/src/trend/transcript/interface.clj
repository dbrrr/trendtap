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

(defn ingest-file [ctx transcript-format file-path]
  (let [data (parse-jsonl-file file-path)
        formatted-file (extract-woz-formatted-transcript (first data))]
    ;; TODO save file to S3 or some repository, with a unique original index name
    ;; TODO identify duplicate if the same file is uploaded twice

    (let [actors (json/read-str (completion/submit {} (concat [{:role "system" :content "You will receive a chat transcript. Please tell me who the main people in this conversation are. Return them in JSON-format like {people: ['Bob', 'Alice']}. Do not wrap this in back-ticks."}]
                                                              [{:role "user" :content formatted-file}]))
                                :key-fn keyword)
          silo (silo/create! ctx {:transcript-id "bar"})]
      (doseq [actor (:people actors)]
        (actor/create! ctx (:id silo) {:description actor})))))
