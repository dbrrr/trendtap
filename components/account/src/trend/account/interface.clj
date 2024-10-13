(ns trend.account.interface
  (:require
    [trend.database.interface :as database]
    [trend.account.repo :as repo]))

(defn create! [ctx email info]
  (database/execute-one! ctx (repo/create ctx email info)))

(defn by-email-ignoring-tenant! [ctx email]
  (database/execute! ctx (repo/by-email-ignoring-tenant ctx email)))

(defn by-email! [ctx email]
  (assert (:tenant-id ctx) "By email called without proper tenant")
  (database/execute-one! ctx (repo/by-email ctx email)))
