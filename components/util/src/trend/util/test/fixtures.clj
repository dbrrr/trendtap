(ns trend.util.test.fixtures
  (:require [trend.database.interface :as db]
            [trend.tenant.interface :as tenant]
            [com.stuartsierra.component :as component]))

(def ^:private current-test-db (atom nil))
(def ^:private current-test-ctx (atom nil))

(defn test-db []
  @current-test-db)

(defn test-ctx []
  @current-test-ctx)

(defn- tenant-ctx
  "Creates a tenant and returns a context, for use in testing"
  []
  (let [ctx {:db (test-db)}
        t (tenant/create! ctx "test-tenant")]
    (assoc ctx :tenant-id (:id t))))

(defn database [f]
  (let [db (db/start)]
    (reset! current-test-db db)
    (reset! current-test-ctx (tenant-ctx))
    (try
      (f)
      (finally
        (db/truncate-all db)
        (component/stop db)
        (reset! current-test-db nil)
        (reset! current-test-ctx nil)))))
