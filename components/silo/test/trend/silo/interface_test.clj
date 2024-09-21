(ns trend.silo.interface-test
  (:require
   [clojure.test :as test :refer :all]
   [trend.silo.interface :as silo]
   [trend.util.test.fixtures :as fixtures]
   [trend.util.interface :as util]
   [trend.actor.interface :as actor]))

(use-fixtures :once fixtures/database)

(deftest basic-sanity-tests
  (let [ctx (fixtures/test-ctx)
        silo (silo/create! ctx {:transcript-id "foo"})
        actor (actor/create! ctx (util/id silo) {:info "bar"})]

    (testing "Cascading delete works"
      (is (actor/by-id! ctx (util/id actor)))
      (silo/delete! ctx silo)
      (is (not (actor/by-id! ctx (util/id actor)))))))
