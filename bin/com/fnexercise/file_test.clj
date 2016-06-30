(ns com.fnexercise.file_test
  (:require [clojure.test :refer :all]
            [com.fnexercise.core :refer :all]
            [com.fnexercise.file :refer :all]))
;utilities methods
(defn setup [])

(defn teardown []
  (reset-customers))

(defn each-fixture [f]
  (setup)
  (f)
  (teardown))

(use-fixtures :each each-fixture)

(deftest read-invite-file-empty-test
  (read-invite-file "C:/Users/manue/Source/Repos/fnexercise/doc/input_empty.txt")
    (is
      (= (count (list-ranking)) 0)))

(deftest read-invite-file-test
  (read-invite-file "C:/Users/manue/Source/Repos/fnexercise/doc/input_test.txt")
    (let [list (list-ranking)]
      (is 
        (= (count list) 7))))


