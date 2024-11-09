(ns trend.rest-api.core-test
  (:require [clojure.test :as test :refer :all]
            [trend.util.test.fixtures :as fixtures]
            [trend.account.interface :as account]
            [trend.rest-api.api :as api]
            [trend.util.interface :as util]))

(use-fixtures :once fixtures/database)

(deftest basic-sanity-tests
  (let [ctx (fixtures/test-ctx)
        account (account/create! ctx "foo@bar.com" {})
        t1-id (:tenant-id ctx)]
    (testing "Basic membership invariants hold"
      (api/login

       ))))
