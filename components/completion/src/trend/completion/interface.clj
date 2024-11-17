(ns trend.completion.interface
  (:require [trend.completion.core :as core]
            [pogonos.core :as pg]
            [clojure.data.json :as json]))

(defn submit
  ([messages] (submit messages {}))
  ([messages {:keys [json] :as options}]
   (cond-> (core/submit messages)
     json (json/read-str :key-fn keyword))))

(defn render-prompt [template-filepath template-args]
  (pg/render-file template-filepath template-args))
