(ns trend.actor.interface
  (:require [trend.database.interface :as database]
            [trend.actor.repo :as repo]
            [trend.util.interface :as util]))

(defn create! [ctx silo-id info]
  (database/execute-one! ctx (repo/create ctx silo-id info)))

(defn by-id! [ctx actor-id]
  (database/execute-one! ctx (repo/by-id ctx actor-id)))

(defn by-silo! [ctx silo]
  (database/execute! ctx (repo/by-silo-id ctx (util/id silo))))
