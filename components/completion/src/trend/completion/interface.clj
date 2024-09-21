(ns trend.completion.interface
  (:require [trend.completion.core :as core]))

(defn submit [ctx messages]
  (core/submit ctx messages))
