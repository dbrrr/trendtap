(ns trend.database.interface
  (:require [trend.database.core :as core]))

(defn execute! [ctx query]
  (core/execute! (:db ctx) query))

(defn execute-one! [ctx query]
  (core/execute-one! (:db ctx) query))

(defn create []
  (core/create))

(defn truncate-all [database]
  (core/truncate-all database))
