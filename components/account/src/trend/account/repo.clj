(ns trend.account.repo
  (:require
    [honey.sql.helpers :as hh]))

(def account-table :account)
(def tenant-account-table :tenant_account_mapping)

(defn create [email details]
  (-> (hh/insert-into account-table)
      (hh/values [{:email email
                   :details [:lift details]}])
      (hh/returning [:*])))

(defn add-to-tenant [account-id tenant-id]
  (-> (hh/insert-into tenant-account-table)
      (hh/values [{:tid (parse-uuid tenant-id)
                   :account-id (parse-uuid account-id)}])
      (hh/returning [:*])))

(defn tenant-ids-by-account-id [account-id]
  (-> (hh/select :*)
      (hh/from tenant-account-table)
      (hh/where [:= :account-id (parse-uuid account-id)])))

(defn remove-from-tenant [account-id tenant-id]
  (-> (hh/delete-from tenant-account-table)
      (hh/where [:and
                 [:= :tid (parse-uuid tenant-id)]
                 [:= :account-id (parse-uuid account-id)]])
      (hh/returning [:*])))

(defn by-email [email]
  (-> (hh/select :*)
      (hh/from account-table)
      (hh/where [:= :email email])))
