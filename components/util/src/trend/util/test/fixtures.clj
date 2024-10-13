(ns trend.util.test.fixtures
  (:require [trend.database.interface :as db]
            [trend.util.test.util :as test-util]
            [com.stuartsierra.component :as component]))

(def ^:private current-test-db (atom nil))
(def ^:private current-test-ctx (atom nil))

(defn test-db []
  @current-test-db)

(defn test-ctx []
  @current-test-ctx)

(defn database [f]
  (let [db (db/start)]
    (reset! current-test-db db)
    (reset! current-test-ctx (test-util/tenant-ctx {:db (test-db)}))
    (try
      (f)
      (finally
        (db/truncate-all db)
        (component/stop db)
        (reset! current-test-db nil)
        (reset! current-test-ctx nil)))))
