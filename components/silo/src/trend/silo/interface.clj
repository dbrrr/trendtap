(ns trend.silo.interface
  (:require [trend.database.interface :as database]
            [trend.silo.repo :as repo]
            [trend.silo.sample :as sample]
            [trend.util.interface :as util]))

(defn ->meeting-transcript [transcript-key]
  {:type :meeting-transcript
   :source-material-key transcript-key})

(defn create! [ctx info]
  (database/execute-one! ctx (repo/create ctx info)))

(defn delete! [ctx silo]
  (database/execute-one! ctx (repo/delete ctx silo)))

(defn sample! [ctx sampler silo]
  (let [sample {:text (sample/-sample sampler silo)}
        sampler-key (sample/-key sampler)]
    (database/execute-one! ctx (repo/record-sample ctx (util/id silo) sampler-key sample))))

(defn samples! [ctx silo]
  (database/execute! ctx (repo/samples-by-silo ctx silo)))
