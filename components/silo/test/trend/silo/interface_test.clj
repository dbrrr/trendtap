(ns trend.silo.interface-test
  (:require
   [clojure.test :as test :refer :all]
   [trend.silo.interface :as silo]
   [trend.util.test.fixtures :as fixtures]))

(use-fixtures :once fixtures/database)

(deftest basic-sanity-tests
  (let [ctx (fixtures/test-ctx)]
    (is (= 1 (silo/create! ctx {:transcript-id "foo"})))))
