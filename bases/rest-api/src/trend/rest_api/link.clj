(ns trend.rest-api.link
  (:require
   [reitit.core :as r]
   [clojure.walk :refer [postwalk]]))

(defmacro routes [name & body]
  (let [rewrite (fn [expr]
                  (if (and (seq? expr)
                           (= (first expr) 'link/to))
                    ;; Insert `name` as the first argument to `link/to`
                    `(link/to ~name ~@(rest expr))
                    expr))]
    `(def ~name
       ~@(postwalk rewrite body))))

(defn to [route-tree & args]
  (reduce (fn [links link-name]
            (assoc links
                   link-name
                   (:path (r/match-by-name route-tree link-name))))
          {}
          args))
