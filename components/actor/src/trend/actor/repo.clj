(ns trend.actor.repo
  (:require
   [honey.sql.helpers :as hh]))

(def actor-table :actor)

(defn link-to-account [{:keys [tenant-id] :as _ctx} actor-id account-id]
  (-> (hh/update actor-table)
      (hh/set {:account-id (parse-uuid account-id)})
      (hh/where [:and
                 [:= :id (parse-uuid actor-id)]
                 [:= :tenant-id (parse-uuid tenant-id)]])))

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

(defn by-silo-id [{:keys [tenant-id] :as _ctx} silo-id]
  (-> (hh/select :*)
      (hh/from actor-table)
      (hh/where [:and
                 [:= :silo-id (parse-uuid silo-id)]
                 [:= :tenant-id (parse-uuid tenant-id)]])))
