(ns trend.tenant.repo
  (:require
    [honey.sql.helpers :as hh]))

(def tenant-table :tenant)

(defn create [_ctx name]
  (-> (hh/insert-into tenant-table)
      (hh/values [{:name name}])
      (hh/returning [:*])))

(defn delete-by-id [_ctx id]
  (-> (hh/delete-from tenant-table)
      (hh/where [:= :id (parse-uuid id)])))

(defn find-first []
  (-> (hh/select :*)
      (hh/from tenant-table)))

(defn by-id [tenant-id]
  (-> (hh/select :*)
      (hh/from tenant-table)
      (hh/where [:= :id (parse-uuid tenant-id)])))
