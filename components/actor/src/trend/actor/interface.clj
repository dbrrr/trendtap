(ns trend.actor.interface
  (:require [trend.database.interface :as database]
            [trend.actor.repo :as repo]))

(defn create! [ctx silo-id info]
  (database/execute-one! ctx (repo/create ctx silo-id info)))
