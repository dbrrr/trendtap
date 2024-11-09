(ns trend.rest-api.core-test
  (:require [clojure.test :as test :refer :all]
            [trend.util.test.fixtures :as fixtures]
            [trend.account.interface :as account]
            [trend.rest-api.api :as api]
            [trend.util.interface :as util]
            [trend.rest-api.core :as core]))

(use-fixtures :once fixtures/database)

(deftest basic-login-tests
  (let [ctx (fixtures/test-ctx)
        user-email "foo@bar.com"
        account (account/create! ctx user-email {})
        t1-id (:tenant-id ctx)]
    (testing "Conditionally handles existence"
      (let [resp (api/login "not@an-email.com")]
        (is (= 200 (:status resp)))))
    (testing "Cookies are gotten back"
      (let [resp (api/login (:email account))]
        (is (= 301 (:status resp)))))
    (let [secured-handler (api/secured-handler (:email account))
          {:keys [headers] :as _resp} (-> (api/homepage)
                                         secured-handler)]
      (is (= "/app" (get headers "Location"))))
    (let [{:keys [headers] :as _resp} (-> (api/homepage)
                                          core/handler)]
      (is (= "/login" (get headers "Location"))))))
