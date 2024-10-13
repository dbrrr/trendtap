(ns trend.util.interface)

(defn id [entity]
  (:id entity))

(defn id-set [entities]
  (set (map id entities)))
