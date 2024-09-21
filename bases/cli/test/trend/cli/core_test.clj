(ns trend.cli.core-test
  (:require [clojure.test :as test :refer :all]
            [trend.util.test.fixtures :as fixtures]
            [trend.cli.ingest :as ingest]
            [trend.completion.interface :as completion]))

(use-fixtures :once fixtures/database)

(deftest basic-sanity-tests
  (let [ctx (fixtures/test-ctx)]
    (with-redefs [completion/submit (fn [_] "Here's your content, bro")]

      (is (= 1 (ingest/ingest ctx "bases/cli/resources/cli/test_woz.jsonl"))))))
