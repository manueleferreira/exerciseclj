(ns com.fnexercise.fnexercise
  (:gen-class)
  (:require [clojure.contrib.math :as math]
            [clojure.java.io :as io] ))
  (use '[clojure.string :only (split)])

(definterface INode
  (getCar [])
  (getCdr [])
  (setCar [x])
  (setCdr [x]))

(deftype Node [^:volatile-mutable car 
               ^:volatile-mutable cdr]
  INode
  (getCar [this] car)
  (getCdr [this] cdr)
  (setCar [this x] (set! car x) this)
  (setCdr [this x] (set! cdr x) this))


(def customers [])

(defn find-customer 
  "Find customer by number value."
  [number] 
  (loop [i 0]
    (when (< i (.length customers))
      (let [customer (nth customers i)]
        (cond
          (= (.getCar customer) number) customer
          :else (recur (inc i)))))))

(defn add-new-customer 
  "Add new node to customer list."
  [customer]
  (def customers 
    (conj customers customer)))


(defn add-new-invite
  "Add new invite."
  [x y]
  (if (nil? (find-customer y))
    (let [node-x (find-customer x) 
          node-y (Node. y nil)] 
      (do
        (if (nil? node-x) 
          (add-new-customer (Node. x (list node-y)))
          (.setCdr node-x (conj (.getCdr node-x) node-y))) 
        (add-new-customer node-y)))))

(defn read-invite-file 
  "Read invites by customers from text file."
  [path]
  (with-open [rdr (io/reader path)]
    (doseq [line (line-seq rdr)]
      (let [list (split line #"\s")] 
        (do
          (let [x (nth list 0) y (nth list 1)]
            (add-new-invite x y)))))))

(defn list-ranking 
  "List Ranking values from customer map."
  []
  (do
    (def map-reward {})
    (loop [i 0]
      (when (< i (.length customers))
        (let [customer (nth customers i) 
              points (count-points-ranking customer)]
          (do
            (assoc map-reward customer points)
            (recur (inc i))))))))

(defn count-points-ranking 
  "map customer and reward value."
  {}
  [customer level]
  (let [invites (.getCdr customer)]
    (loop [i 0]
      (when (< i (.length invites))
        (let [invite (nth invites i) 
              chidren-invite (.getCdr invite)]
          (if (no-nil? chidren-invite)
            (+ (math/expt (/ 1 2) level) 
               (count-points-ranking invite (inc level)))))))))




