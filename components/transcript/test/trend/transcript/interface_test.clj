(ns trend.transcript.interface-test
  (:require [clojure.test :as test :refer :all]
            [trend.transcript.interface :as transcript]))

(deftest dummy-test
  (is (= 1 1)))


(def demo-transcript
  {:transcript
   [{:participant "David Russell",
     :text
     "Hey Bob, thanks for meeting with me today to discuss Trump vs Kamala."}
    {:participant "Bob Johnson",
     :text
     "Of course, David. I'm interested in hearing your perspective on Trump."}
    {:participant "David Russell",
     :text
     "Well, I've always been pro Trump. I think he's done a lot for our country."}
    {:participant "Bob Johnson",
     :text
     "I understand that perspective, but I personally support Kamala. I believe she represents a more inclusive vision for America."}
    {:participant "David Russell",
     :text
     "I can see where you're coming from, but I have concerns about Kamala's policies."}
    {:participant "Bob Johnson",
     :text "What specific policies are you referring to, David?"}
    {:participant "David Russell",
     :text
     "Well, for one, I disagree with her stance on healthcare. I think Trump had a better approach."}
    {:participant "Bob Johnson",
     :text
     "That's a valid point. But I believe healthcare should be a right for all citizens, which is why I support Kamala's plan."}
    {:participant "David Russell",
     :text
     "I respect your opinion, Bob. It's important to have these discussions to understand different perspectives."}]})

(deftest demo-transcript-parsing-actors-works
  (is (= #{"Bob Johnson" "David Russell"}
         (:actors (transcript/ingest-demo-format demo-transcript)))))
