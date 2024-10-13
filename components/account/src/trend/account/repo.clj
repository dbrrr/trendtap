(ns trend.account.repo
  (:require
    [honey.sql.helpers :as hh]))

(def account-table :account)

(defn create [{:keys [tenant-id] :as _ctx} email details]
  (-> (hh/insert-into account-table)
      (hh/values [{:tenant-id (parse-uuid tenant-id)
                   :email email
                   :details [:lift details]}])
      (hh/returning [:*])))

(defn by-email [{:keys [tenant-id] :as _ctx} email]
  (-> (hh/select :*)
      (hh/from account-table)
      (hh/where [:and
                 [:= :email email]
                 [:= :tenant-id (parse-uuid tenant-id)]])))

(defn by-email-ignoring-tenant [_ctx email]
  (-> (hh/select :*)
      (hh/from account-table)
      (hh/where [:= :email email])))
