(ns trend.transcript.interface
  (:require [clojure.data.json :as json]
            [clojure.java.io :as io]
            [trend.completion.interface :as completion]
            [trend.actor.interface :as actor]
            [trend.silo.interface :as silo]
            [trend.util.interface :as util]))

(defn parse-jsonl-file [filename]
  (with-open [reader (io/reader filename)]
    (doall
     (map #(json/read-str % :key-fn keyword)
          (line-seq reader)))))

(defn- extract-woz-formatted-transcript [transcript]
  (let [segments (-> transcript :meeting :transcriptSegments)
        actors (set (map :speakerName segments))]
    {:actors actors
     :transcript (apply str (map #(format "%s: %s\n" (:speakerName %) (:text %))
                                 segments))}))

(defn- summary! [transcript]
  (completion/submit {} (concat [{:role "system" :content "You will receive a chat transcript. Please summarize the main topic of discussion."}]
                                [{:role "user" :content transcript}])))

(defn ingest-file [ctx transcript-format file-path]
  (let [data (parse-jsonl-file file-path)
        {:keys [actors transcript]} (extract-woz-formatted-transcript (first data))]
    (assert (> (count actors) 0) "Transcript must have actors. Don't yet support anything else")
    ;; TODO save file to S3 or some repository, with a unique original index name
    ;; TODO identify duplicate if the same file is uploaded twice
    (let [silo (silo/create! ctx (silo/->meeting-transcript "foobar"))]
      (doseq [actor actors]
        (actor/create! ctx (util/id silo) {:description actor}))
      silo)))
