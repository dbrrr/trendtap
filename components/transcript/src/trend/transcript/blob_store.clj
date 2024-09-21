(ns trend.transcript.blob-store)

(defn save! [transcript]
  (spit (str "./components/transcript/resources/file.txt") transcript))

(defn load! [transcript]
  (slurp (str "./components/transcript/resources/file.txt")))
