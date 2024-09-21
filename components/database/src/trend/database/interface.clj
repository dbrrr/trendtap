(ns trend.database.interface
  (:require [trend.database.core :as core]))

(defn execute! [ctx query]
  (core/execute! (:db ctx) query))

(defn execute-one! [ctx query]
  (core/execute-one! (:db ctx) query))

(defn start []
  (core/start))

(defn truncate-all [database]
  (core/truncate-all database))
