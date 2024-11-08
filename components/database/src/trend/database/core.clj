(ns trend.database.core
  (:require
   [clojure.string :as str]
   [hikari-cp.core :as cp]
   [honey.sql :as sql]
   [com.stuartsierra.component :as component]
   [next.jdbc :as jdbc]
   [medley.core :as medley]
   [ragtime.core :as ragtime]
   [ragtime.next-jdbc :as ragtime-jdbc]
   [jsonista.core :as json]
   [next.jdbc.prepare :as prepare]
   [next.jdbc.result-set :as rs])
  (:import [org.postgresql.util PGobject]
           [java.sql PreparedStatement]))

(def mapper (json/object-mapper {:decode-key-fn keyword}))
(def ->json json/write-value-as-string)
(def <-json #(json/read-value % mapper))

(set! *warn-on-reflection* true)

(defn ->pgobject
  "Transforms Clojure data to a PGobject that contains the data as
  JSON. PGObject type defaults to `jsonb` but can be changed via
  metadata key `:pgtype`"
  [x]
  (let [pgtype (or (:pgtype (meta x)) "jsonb")]
    (doto (PGobject.)
      (.setType pgtype)
      (.setValue (->json x)))))

(defn <-pgobject
  "Transform PGobject containing `json` or `jsonb` value to Clojure
  data."
  [^org.postgresql.util.PGobject v]
  (let [type  (.getType v)
        value (.getValue v)]
    (if (#{"jsonb" "json"} type)
      (when value
        (with-meta (<-json value) {:pgtype type}))
      value)))

(extend-protocol prepare/SettableParameter
  clojure.lang.IPersistentMap
  (set-parameter [m ^PreparedStatement s i]
    (.setObject s i (->pgobject m)))

  clojure.lang.IPersistentVector
  (set-parameter [v ^PreparedStatement s i]
    (.setObject s i (->pgobject v))))

;; if a row contains a PGobject then we'll convert them to Clojure data
;; while reading (if column is either "json" or "jsonb" type):
(extend-protocol rs/ReadableColumn
  org.postgresql.util.PGobject
  (read-column-by-label [^org.postgresql.util.PGobject v _]
    (<-pgobject v))
  (read-column-by-index [^org.postgresql.util.PGobject v _2 _3]
    (<-pgobject v)))

(defn- db-info-from-url [db-url]
  (let [db-uri              (java.net.URI. db-url)
        [username password] (str/split (or (.getUserInfo db-uri) ":") #":")]
    {:username      username
     :password      password
     :port-number   (.getPort db-uri)
     :database-name (str/replace-first (.getPath db-uri) "/" "")
     :server-name   (.getHost db-uri)}))

(defn datasource-options [url]
  (merge (db-info-from-url url)
         {:auto-commit        true
          :read-only          false
          :adapter            "postgresql"
          :connection-timeout 30000
          :validation-timeout 5000
          :idle-timeout       600000
          :max-lifetime       1800000
          :minimum-idle       10
          :maximum-pool-size  20
          :pool-name          "db-pool"
          :register-mbeans    false}))

(defn- migrate! [datasource]
  (with-open [connection (jdbc/get-connection datasource)]
    (let [migrations (ragtime-jdbc/load-directory "components/database/resources/database/migrations")]
      (ragtime/migrate-all (ragtime-jdbc/sql-database connection {:migrations-table "ragtime_migrations"})
                           (ragtime/into-index migrations)
                           migrations))))

(defn- rollback! [datasource n]
  (with-open [connection (jdbc/get-connection datasource)]
    (let [migrations (ragtime-jdbc/load-directory "components/database/resources/database/migrations")]
      (ragtime/rollback-last (ragtime-jdbc/sql-database connection {:migrations-table "ragtime_migrations"})
                             (ragtime/into-index migrations)
                             n))))

(defn truncate-all [database]
  (rollback! (:datasource database) 1000))

#_(truncate-all @trend.rest-api.system/system)

(defn execute! [database query]
  (with-open [connection (jdbc/get-connection (:datasource database))]
    (->> (jdbc/execute! connection
                        (sql/format query)
                        jdbc/unqualified-snake-kebab-opts)
         (map (fn [r]
                (-> r
                    (dissoc :tenant-id)
                    (medley/update-existing :id str)))))))

(defn execute-one! [database query]
  (with-open [connection (jdbc/get-connection (:datasource database))]
    (-> (jdbc/execute-one! connection
                           (sql/format query)
                           jdbc/unqualified-snake-kebab-opts)
        (dissoc :tenant-id)
        (medley/update-existing :id str))))

(defrecord Database [db-url datasource num-migrations]
  component/Lifecycle
  (start [this]
    (if (:datasource this)
      this
      (let [config (datasource-options db-url)
            datasource (cp/make-datasource config)]
        (migrate! datasource)
        (assoc this :datasource datasource))))
  (stop [this]
    (cp/close-datasource (:datasource this))
    (assoc this :datasource nil))

  ;; allow the Database component to be "called" with no arguments
  ;; to produce the underlying datasource object
  clojure.lang.IFn
  (invoke [_] datasource))

(defn start []
  (component/start
   (map->Database {:db-url "postgresql://postgres:psql@localhost:9999/trendtap"})))
