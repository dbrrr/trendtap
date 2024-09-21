(ns trend.silo.interface
  (:require [trend.database.interface :as database]
            [trend.silo.repo :as repo]))

(defn create! [ctx info]
  (database/execute-one! ctx (repo/create ctx info)))
