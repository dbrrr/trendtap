(ns trend.completion.interface
  (:require [trend.completion.core :as core]
            [pogonos.core :as pg]))

(defn submit [messages]
  (core/submit messages))

(defn render-prompt [template-filepath template-args]
  (pg/render-file template-filepath template-args))
