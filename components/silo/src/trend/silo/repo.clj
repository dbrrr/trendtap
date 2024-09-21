(ns trend.silo.repo
  (:require
    [honey.sql.helpers :as hh]
    [trend.util.interface :as util]))

(def silo-table :silo)

(defn create [{:keys [tenant-id] :as _ctx} details]
  (-> (hh/insert-into silo-table)
      (hh/values [{:tenant-id (parse-uuid tenant-id)
                   :details [:lift details]}])
      (hh/returning [:*])))

(defn delete [{:keys [tenant-id] :as _ctx} silo]
  (-> (hh/delete-from silo-table)
      (hh/where [:and
                 [:= :id (parse-uuid (util/id silo))]
                 [:= :tenant-id (parse-uuid tenant-id)]])
      (hh/returning [:*])))
