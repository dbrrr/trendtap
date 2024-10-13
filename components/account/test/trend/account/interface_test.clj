(ns trend.account.interface-test
  (:require [clojure.test :as test :refer :all]
            [trend.account.interface :as account]
            [trend.tenant.interface :as tenant]
            [trend.util.interface :as util]
            [trend.util.test.fixtures :as fixtures])
  (:import
   (org.postgresql.util PSQLException)))

(use-fixtures :once fixtures/database)

(deftest basic-sanity-tests
  (let [ctx (fixtures/test-ctx)
        account (account/create! ctx "foo@bar.com" {})
        t1-id (:tenant-id ctx)
        t1 (tenant/by-id! ctx t1-id)
        t2 (tenant/create! ctx "Test tenant 2")
        t2-id (util/id t2)]
    (testing "Basic creation works"
      (is (= (util/id account)
             (util/id (account/by-email! ctx "foo@bar.com")))))
    (testing "Emails must be unique"
      (is (thrown? PSQLException
                   (account/create! ctx "foo@bar.com" {}))))
    (testing "Basic membership invariants hold"
      (account/add-to-tenant! ctx account t1)
      (is (= (set (map (comp parse-uuid util/id) [t1]))
             (set (account/tenant-ids! ctx account))))
      (account/add-to-tenant! ctx account t2)
      (is (= (set (map (comp parse-uuid util/id) [t1 t2]))
             (set (account/tenant-ids! ctx account))))
      (account/remove-from-tenant! ctx account t1)
      (is (= (set (map (comp parse-uuid util/id) [t2]))
             (set (account/tenant-ids! ctx account))))
      (tenant/delete-by-id! ctx t2-id)
      (is (= (set (map (comp parse-uuid util/id) []))
             (set (account/tenant-ids! ctx account)))))
    (testing "Duplicates cant be created in the same tenant"
      (account/add-to-tenant! ctx account t1)
      (is (thrown? PSQLException
                   (account/add-to-tenant! ctx account t1))))))
