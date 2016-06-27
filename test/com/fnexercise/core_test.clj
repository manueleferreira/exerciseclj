(ns com.fnexercise.core-test
  (:require [clojure.test :refer :all]
            [com.fnexercise.core :refer :all]))

(defn setup []
  (reset-customers))

(defn teardown [])

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
      (with-redefs [customers []]
         (find-customer 1)))))

(deftest find-customer-test
  (is 
    (not-nil 
      (do
        (add-new-invite 1 2)
        (find-customer 1)))))

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
  (let [list []]
    (do
      (read-invite-file "C:/Users/manue/Source/Repos/fnexercise/doc/input_empty.txt")
      (is
        (= (count (list-ranking))
           0)))))

(defn select-values [map ks]
  (reduce #(conj %1 (map %2)) [] ks))

(deftest read-invite-file-test
  (do
    (read-invite-file "C:/Users/manue/Source/Repos/fnexercise/doc/input_test.txt")
    (let [list (list-ranking)]
      (is 
        (= (count list) 6)))))
