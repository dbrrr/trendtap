(ns trend.util.interface)

(defn id [entity]
  (some-> (:id entity)
          str))

(defn id-set [entities]
  (set (map id entities)))
