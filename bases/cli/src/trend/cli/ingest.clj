(ns trend.cli.ingest
  (:require
    [trend.transcript.interface :as transcript]
    [trend.silo.interface :as silo]
    [trend.actor.interface :as actor]
    [trend.util.interface :as util]
    [trend.silo.sample :as sample]))

(defn ingest [ctx filepath]
  (let [{:keys [actors]} (transcript/ingest-file ctx :foo filepath)
        silo (silo/create! ctx (silo/->meeting-transcript "foobar"))]
    (assert (> (count actors) 0) "Transcript must have actors. Don't yet support anything else")
    (doseq [actor actors]
      (actor/create! ctx (util/id silo) {:description actor}))
    (silo/sample! ctx sample/summarizer silo)

    (let [actors (actor/by-silo! ctx silo)
          silo-sample (first (silo/samples! ctx silo))]
      (println "Silo created:")
      (doseq [actor actors]
        (println "  Actor: " (-> actor :details :description)))
      (println "  Summary:" (-> silo-sample :details :text)))))
