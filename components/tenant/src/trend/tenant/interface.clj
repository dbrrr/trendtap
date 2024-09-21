(ns trend.tenant.interface
  (:require [trend.tenant.core :as core]))

(defn create! [ctx name]
  (core/create! ctx name))

;; NOTE temp thing just to get tenant selection working
(defn find-first! [ctx]
  (core/find-first! ctx))
