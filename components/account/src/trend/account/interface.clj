(ns trend.account.interface
  (:require
   [trend.database.interface :as database]
   [trend.account.repo :as repo]
   [trend.util.interface :as util]))

(defn create! [ctx email info]
  (database/execute-one! ctx (repo/create email info)))

(defn add-to-tenant! [ctx account tenant]
  (database/execute-one! ctx (repo/add-to-tenant (util/id account) (util/id tenant))))

(defn remove-from-tenant! [ctx account tenant]
  (database/execute-one! ctx (repo/remove-from-tenant (util/id account) (util/id tenant))))

(defn tenant-ids! [ctx account]
  (map :tid (database/execute! ctx (repo/tenant-ids-by-account-id (util/id account)))))

(defn by-email! [ctx email]
  (database/execute-one! ctx (repo/by-email email)))
