(ns trend.tenant.interface
  (:require [trend.tenant.core :as core]))

(defn create! [ctx name]
  (core/create! ctx name))

(defn delete-by-id! [ctx id]
  (core/delete-by-id! ctx id))

(defn by-id! [ctx id]
  (core/by-id! ctx id))

;; NOTE temp thing just to get tenant selection working
(defn find-first! [ctx]
  (core/find-first! ctx))
