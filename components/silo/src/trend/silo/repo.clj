(ns trend.silo.repo
  (:require
    [honey.sql.helpers :as hh]
    [trend.util.interface :as util]))

(def silo-table :silo)
(def silo-sample-table :silo-sample)

(defn create [{:keys [tenant-id] :as _ctx} details]
  (-> (hh/insert-into silo-table)
      (hh/values [{:tenant-id (parse-uuid tenant-id)
                   :details [:lift details]}])
      (hh/returning [:*])))

(defn all [{:keys [tenant-id] :as _ctx}]
  (-> (hh/select :*)
      (hh/from silo-table)
      (hh/where [:= :tenant-id (parse-uuid tenant-id)])))

(defn delete [{:keys [tenant-id] :as _ctx} silo]
  (-> (hh/delete-from silo-table)
      (hh/where [:and
                 [:= :id (parse-uuid (util/id silo))]
                 [:= :tenant-id (parse-uuid tenant-id)]])
      (hh/returning [:*])))

(defn record-sample [{:keys [tenant-id] :as _ctx} silo-id sampler-key sample]
  (-> (hh/insert-into silo-sample-table)
      (hh/values [{:tenant-id (parse-uuid tenant-id)
                   :silo-id (parse-uuid silo-id)
                   :sampler-key sampler-key
                   :details [:lift sample]}])
      (hh/returning [:*])))

(defn samples-by-silo [{:keys [tenant-id] :as _ctx} silo]
  (-> (hh/select :*)
      (hh/from silo-sample-table)
      (hh/where [:and
                 [:= :tenant-id (parse-uuid tenant-id)]
                 [:= :silo-id (parse-uuid (util/id silo))]])))
