(ns trend.silo.interface
  (:require [trend.database.interface :as database]
            [trend.silo.repo :as repo]))

(defn ->meeting-transcript [transcript-key]
  {:type :meeting-transcript
   :source-material-key transcript-key})

(defn create! [ctx info]
  (database/execute-one! ctx (repo/create ctx info)))

(defn delete! [ctx silo]
  (database/execute-one! ctx (repo/delete ctx silo)))
