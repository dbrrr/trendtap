(ns trend.account.interface-test
  (:require [clojure.test :as test :refer :all]
            [trend.account.interface :as account]
            [trend.util.test.fixtures :as fixtures]
            [trend.util.interface :as util]
            [trend.util.test.util :as test-util]
            [trend.tenant.interface :as tenant])
  (:import
   (org.postgresql.util PSQLException)))

(use-fixtures :once fixtures/database)

(deftest basic-sanity-tests
  (let [ctx (fixtures/test-ctx)
        account (account/create! ctx "foo@bar.com" {:anything :iwant})
        new-tenant-ctx (test-util/tenant-ctx ctx)
        account2 (account/create! new-tenant-ctx "foo@bar.com" {:anything :iwant})]
    (testing "Basic creation works"
      (is (= (util/id account)
             (util/id (account/by-email! ctx "foo@bar.com"))))
      (is (= (util/id account2)
             (util/id (account/by-email! new-tenant-ctx "foo@bar.com")))))
    (testing "Emails are not unique"
      (is (= (util/id-set [account account2])
             (util/id-set (account/by-email-ignoring-tenant! ctx "foo@bar.com")))))
    (testing "Cascading deletes works"
      (tenant/delete-by-id! ctx (:tenant-id new-tenant-ctx))
      (is (= (util/id-set [account])
             (util/id-set (account/by-email-ignoring-tenant! ctx "foo@bar.com")))))
    (testing "Duplicates cant be created in the same tenant"
      (is (thrown? PSQLException
                   (account/create! ctx "foo@bar.com" {:anything :iwant}))))
    (testing "Accounts can't be created in a non-existent tenant"
      (is (thrown? PSQLException
                   (account/create! new-tenant-ctx "foo@bar.com" {:anything :iwant}))))))
