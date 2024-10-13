(ns trend.util.test.util
  (:require [trend.tenant.interface :as tenant]))

(defn tenant-ctx
  "Creates a tenant and returns a context, for use in testing"
  [ctx]
  (let [t (tenant/create! ctx "test-tenant")]
    (assoc ctx :tenant-id (:id t))))
