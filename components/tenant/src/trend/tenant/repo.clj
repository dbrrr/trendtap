(ns trend.tenant.repo
  (:require
    [honey.sql.helpers :as hh]))

(def tenant-table :tenant)

(defn create [_ctx name]
  (-> (hh/insert-into tenant-table)
      (hh/values [{:name name}])
      (hh/returning [:*])))

(defn find-first []
  (-> (hh/select :*)
      (hh/from tenant-table)))
