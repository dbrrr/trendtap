(ns trend.tenant.core
  (:require [trend.tenant.repo :as repo]
            [trend.database.interface :as db]))

(defn create! [ctx name]
  (db/execute-one! ctx (repo/create ctx name)))

(defn find-first! [ctx]
  (db/execute-one! ctx (repo/find-first)))
