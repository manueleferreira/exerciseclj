(ns com.fnexercise.core-test
  (:require [clojure.test :refer :all]
            [com.fnexercise.core :refer :all]))

;utilities methods
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

; unit test

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
     node-x (instance-node 1 true (list node-y))]
    (do
      (add-new-customer node-y)
      (add-new-customer node-x)))
  (let [returned (exist-and-not-invited-someone 2)]
    (is
      (nil? returned))))

(deftest add-new-invite-test
  (is 
    (= (.getCar (add-new-invite 1 2)) 1)))

(deftest list-ranking-empty-test
  (is 
      (= (count (list-ranking)) 0)))

(deftest count-points-ranking-init-test
  (let
    [node-y (instance-node 2 false nil)
     node-x (instance-node 1 true (list node-y))]
    (do
      (add-new-customer node-y)
      (add-new-customer node-x)))
    (let 
      [list (list-ranking)]
      (is
        (= (count list)) 2)
      (is
        (= (first list) {2 0}))))

(deftest count-points-ranking-basic-test
  (let
    [node-x (instance-node 3 false nil)
     node-y (instance-node 2 true (list node-x))
     node-z (instance-node 1 true (list node-y))]
    (do
      (add-new-customer node-x)
      (add-new-customer node-y)
      (add-new-customer node-z)))
  (let
    [list (list-ranking)]
    (is
      (= (count list)) 2)
    (is
      (= (last (last list)) [1 1.0]))))

(deftest count-points-ranking-children-test
  (let
    [node-x (instance-node 5 false nil)
     node-y (instance-node 4 true nil)
     node-z (instance-node 3 true (list node-y node-x))
     node-w (instance-node 2 true (list node-z))
     node-h (instance-node 1 true (list node-w))]
    (do
      (add-new-customer node-x)
      (add-new-customer node-y)
      (add-new-customer node-z)
      (add-new-customer node-w)
      (add-new-customer node-h)))
  (let
    [list (list-ranking)]
    (is
      (= (count list)) 2)
    (is
      (= (last (last list)) [1 1.75]))))
