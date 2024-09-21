(ns trend.silo.repo
  (:require
    [honey.sql.helpers :as hh]))

(def silo-table :silo)

(defn create [{:keys [tenant-id] :as _ctx} details]
  (-> (hh/insert-into silo-table)
      (hh/values [{:tenant-id (parse-uuid tenant-id)
                   :details [:lift details]}])
      (hh/returning [:*])))
