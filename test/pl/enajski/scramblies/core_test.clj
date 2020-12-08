(ns pl.enajski.scramblies.core-test
  (:require [pl.enajski.scramblies.core :as scramblies]
            [clojure.test :refer [deftest are]]))

(deftest simple-examples
  (are [example ref] (= ref (scramblies/scramble (:str1 example) (:str2 example)))
    {:str1 "rekqodlw" :str2 "world"} true
    {:str1 "cedewaraaossoqqyt" :str2 "codewars"} true
    {:str1 "katas" :str2 "steak"} false))

