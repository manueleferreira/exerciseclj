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

(deftest add-new-customer-test
  (let [returned (add-new-customer (instance-node 1 false nil))]
    (is
      (not
        (= (.getCar returned) nil)))
    (is
      (= (.getCar returned) 1))
    (is
      (= (.getInvited returned) false))
    (is
      (nil?
        (.getCdr returned)))))
  
(deftest exist-and-not-invited-someone-test
  (let [returned (exist-and-not-invited-someone 1)]
    (is
      (not
        (nil? returned)))
    (is
      (= (.getCar returned) 1))
    (is
      (not 
        (nil?
          (exist-and-not-invited-someone 1))))))

(deftest exist-and-not-invited-someone-check-test
  (let
    [node-y (instance-node 2 false nil)
     node-x (instance-node 1 false (list node-y))]
    (do
      (add-new-customer node-y)
      (add-new-customer node-x)))
  (let [returned (exist-and-not-invited-someone 2)]
    (is
      (nil? returned))))

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
