(ns trend.silo.interface-test
  (:require
   [clojure.test :as test :refer :all]
   [trend.silo.interface :as silo]
   [trend.util.test.fixtures :as fixtures]
   [trend.util.interface :as util]
   [trend.actor.interface :as actor]
   [trend.account.interface :as account]))

(use-fixtures :once fixtures/database)

(deftest basic-sanity-tests
  (let [ctx (fixtures/test-ctx)
        silo (silo/create! ctx {:transcript-id "foo"})
        account (account/create! ctx "foo@bar.com" {})
        actor (actor/create! ctx (util/id silo) {:info "bar"})]

    (testing "Cascading delete works"
      (is (actor/by-id! ctx (util/id actor)))
      (silo/delete! ctx silo)
      (is (not (actor/by-id! ctx (util/id actor)))))

    (testing "Resolution works"
      (let [resolved-silo (silo/resolve! ctx (util/id silo))]
        (is (= nil resolved-silo)))
      (let [silo (silo/create! ctx {:transcript-id "foo"})
            resolved-silo (silo/resolve! ctx (util/id silo))]
        (is (= '() (:actors resolved-silo)))
        (let [actor (actor/create! ctx (util/id silo) {:info "bar"})
              resolved-silo (silo/resolve! ctx (util/id silo))]
          (is (= 1 (count (:actors resolved-silo))))
          (is (not (contains? (first (:actors resolved-silo)) :account)))
          (let [_ (actor/link-to-account ctx (util/id actor) (util/id account))
                resolved-silo (silo/resolve! ctx (util/id silo))]
            (is (= 1 (count (:actors resolved-silo))))
            (is (:email (:account (first (:actors resolved-silo)))))))))))
