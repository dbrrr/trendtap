(ns trend.completion.interface
  (:require [trend.completion.core :as core]))

(defn submit [messages]
  (core/submit messages))
