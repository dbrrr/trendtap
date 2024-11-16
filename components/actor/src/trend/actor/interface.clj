(ns trend.actor.interface
  (:require [trend.database.interface :as database]
            [trend.actor.repo :as repo]
            [trend.util.interface :as util]
            [trend.account.interface :as account]))

(defn create! [ctx silo-id info]
  (database/execute-one! ctx (repo/create ctx silo-id info)))

(defn link-to-account [ctx actor-id account-id]
  (database/execute-one! ctx (repo/link-to-account ctx actor-id account-id)))

(defn by-id! [ctx actor-id]
  (database/execute-one! ctx (repo/by-id ctx actor-id)))

(defn by-silos! [ctx silos]
  (database/execute! ctx (repo/by-silo-ids ctx (map util/id silos))))

(defn by-silo! [ctx silo]
  (database/execute! ctx (repo/by-silo-ids ctx [(util/id silo)])))

(defn resolve-account! [ctx {:keys [account-id] :as actor}]
  (let [related-account (some->> account-id
                                 (account/by-id! ctx))]
    (cond-> actor
      related-account (assoc :account related-account))))
