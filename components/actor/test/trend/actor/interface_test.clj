(ns trend.actor.interface-test
  (:require [clojure.test :as test :refer :all]
            [trend.actor.interface :as actor]
            [trend.silo.interface :as silo]
            [trend.util.test.fixtures :as fixtures]
            [trend.util.interface :as util]
            [trend.account.interface :as account]))

(use-fixtures :once fixtures/database)

(deftest basic-sanity-tests
  (let [ctx (fixtures/test-ctx)
        silo (silo/create! ctx {:transcript-id "foo"})
        account (account/create! ctx "foo@bar.com" {})
        actor (actor/create! ctx (util/id silo) {:info "bar"})]

    (is (actor/link-to-account ctx (util/id actor) (util/id account)))))
