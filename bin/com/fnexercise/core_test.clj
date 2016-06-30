(ns com.fnexercise.core-test
  (:require [clojure.test :refer :all]
            [com.fnexercise.core :refer :all]))

(defn setup [])

(defn teardown []
  (reset-customers))

(defn each-fixture [f]
  (setup)
  (f)
  (teardown))

(use-fixtures :each each-fixture)

(defn not-nil [value]
  (not
    (nil? value)))

(deftest find-customer-nil-test
  (is 
    (nil?
      (find-customer-by-value 1))))

(deftest find-customer-test
  (add-new-invite 1 2)
  (is 
    (let [returned (find-customer-by-value 1)]
      (= (.getCar returned) 1)))
  (is 
    (not 
      (let [returned (find-customer-by-value 1)]
        (= (.getCar returned) 2)))))

(deftest has-already-invited-test
  (add-new-invite 1 2)
  (is 
    (empty? (has-already-invited 1)))
  (is 
    (not 
      (empty?
        (has-already-invited 2)))))

(deftest add-new-invite-test
  (do
    (add-new-invite 1 2)
    (is 
      (= (count (list-ranking))
         2))))

(deftest list-ranking-empty-test
  (do
    (is 
      (= (count (list-ranking))
         0))))

(deftest read-invite-file-empty-test
  (do
    (read-invite-file "C:/Users/manue/Source/Repos/fnexercise/doc/input_empty.txt")
    (is
      (= (count (list-ranking))
         0))))

(deftest read-invite-file-test
  (do
    (read-invite-file "C:/Users/manue/Source/Repos/fnexercise/doc/input_test.txt")
    (let [list (list-ranking)]
      (is 
        (= (count list) 7)))))
