(ns trend.database.utils
  (:require [jsonista.core :as json]
            [next.jdbc.prepare :as prepare]
            [next.jdbc.result-set :as rs])
  (:import [org.postgresql.util PGobject]
           [java.sql PreparedStatement]))
