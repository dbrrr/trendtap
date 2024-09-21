(ns trend.actor.repo
  (:require
    [honey.sql.helpers :as hh]))

(def actor-table :actor)

(defn create [{:keys [tenant-id] :as _ctx} silo-id details]
  (-> (hh/insert-into actor-table)
      (hh/values [{:tenant-id (parse-uuid tenant-id)
                   :silo-id (parse-uuid silo-id)
                   :details [:lift details]}])
      (hh/returning [:*])))

(defn by-id [{:keys [tenant-id] :as _ctx} actor-id]
  (-> (hh/select :*)
      (hh/from actor-table)
      (hh/where [:and
                 [:= :id (parse-uuid actor-id)]
                 [:= :tenant-id (parse-uuid tenant-id)]])))
