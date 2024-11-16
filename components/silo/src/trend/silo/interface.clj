(ns trend.silo.interface
  (:require [trend.database.interface :as database]
            [trend.silo.repo :as repo]
            [trend.silo.sample :as sample]
            [trend.util.interface :as util]
            [trend.actor.interface :as actor]
            [medley.core :as medley]))

(defn ->meeting-transcript [transcript-key]
  {:type :meeting-transcript
   :source-material-key transcript-key})

(defn create! [ctx info]
  (database/execute-one! ctx (repo/create ctx info)))

(defn all! [ctx]
  (database/execute! ctx (repo/all ctx)))

(defn by-id! [ctx silo-id]
  (database/execute-one! ctx (repo/by-id ctx silo-id)))

(defn delete! [ctx silo]
  (database/execute-one! ctx (repo/delete ctx silo)))

(defn set-details! [ctx silo details]
  (database/execute-one! ctx (repo/set-details ctx (util/id silo) details)))

(defn sample! [ctx sampler silo]
  (let [sample {:text (sample/-sample sampler silo)}
        sampler-key (sample/-key sampler)]
    (database/execute-one! ctx (repo/record-sample ctx (util/id silo) sampler-key sample))))

(defn samples! [ctx silo]
  (database/execute! ctx (repo/samples-by-silo ctx silo)))

(defn resolve! [ctx silo-id]
  (let [silo (by-id! ctx silo-id)
        actors (some->> silo
                        (actor/by-silo! ctx)
                        (map #(actor/resolve-account! ctx %)))]
    (some-> silo
            (assoc :actors actors))))
