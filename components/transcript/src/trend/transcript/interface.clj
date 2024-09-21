(ns trend.transcript.interface
  (:require [clojure.data.json :as json]
            [clojure.java.io :as io]
            [trend.transcript.blob-store :as blob-store]))

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

(defn from-silo [silo]
  (blob-store/load! {}))

(defn ingest-file [ctx transcript-format file-path]
  ;; TODO identify duplicate if the same file is uploaded twice
  (let [data (parse-jsonl-file file-path)
        {:keys [transcript] :as result} (extract-woz-formatted-transcript (first data))]
    (blob-store/save! transcript)
    result))
