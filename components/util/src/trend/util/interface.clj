(ns trend.util.interface)

(defn id [entity]
  (str (:id entity)))

(defn id-set [entities]
  (set (map id entities)))
