(ns glosa.models.utils-test
  (:require [clojure.test :refer :all]
            [glosa.models.utils :as utils]))

(deftest get-unixtime-now-test
  (testing "Unixtime is more 1.500.000"
    (is (> (utils/get-unixtime-now) 1500000))))
