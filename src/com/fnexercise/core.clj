(ns com.fnexercise.core
  (:require [clojure.java.io :as io]
            [clojure.math.numeric-tower :as math]
            [clojure.string :as str]))

; Basic structure
(definterface INode
  (getCar [])
  (getCdr [])
  (getInvited [])
  (setCar [x])
  (setCdr [x])
  (setInvited [x]))

(deftype Node [^:volatile-mutable car
               ^:volatile-mutable invited
               ^:volatile-mutable cdr]
  INode
  (getCar [this] car)
  (getCdr [this] cdr)
  (getInvited [this] invited)
  (setCar [this x] (set! car x) this)
  (setCdr [this x] (set! cdr x) this)
  (setInvited [this x] (set! invited x) this))

(def customers [])

(defn reset-customers []
  (def customers []))

(defn find-customer-by-value 
  "Find customer by number value."
  [number] 
  (first
    (for [customer customers
       :let [value (.getCar customer)]
       :when (= value number)]
    customer)))

(defn has-already-invited
  [y]
  (for [customer customers
        :when (not 
                (empty? 
                  (remove #(not= (.getCar %) y) (.getCdr customer))))] customer))

(defn add-new-customer 
  "Add new node to customer list."
  [customer]
  (def customers 
      (conj customers customer))
  customer)

(defn exist-and-not-invited-someone
  [y]
  (let [node-y (find-customer-by-value y)]
    (if (nil? node-y)
      (add-new-customer (Node. y false nil))
      (if (empty? (has-already-invited y))
        node-y
        nil))))

(defn add-new-invite
  "Add new invite."
  [x y]
  (let [node-x (find-customer-by-value x) 
        node-y (exist-and-not-invited-someone y)
        list-y (if (not (nil? node-y)) 
                 (list node-y))] 
    (if (nil? node-x) 
        (add-new-customer (Node. x true list-y)) 
        (do 
          (if (not (nil? node-y)) 
            (.setCdr node-x (conj (.getCdr node-x) node-y))) 
          (.setInvited node-x true))))) 

(defn read-invite-file 
  "Read invites by customers from text file."
  [path]
  (with-open [rdr (io/reader path)]
    (doseq [line (line-seq rdr)]
      (let [list (str/split line #"\s") 
            x (nth list 0) 
            y (nth list 1)]
            (add-new-invite x y)))))

(defn count-points-ranking 
  "map customer and reward value."
  [customer level]
  (let [invites (.getCdr customer)]
    (loop [sum 0 i 0]
      (if (= i (count invites))
        sum
        (recur 
          (+ sum 
             (let [invite (nth invites i)
                   chidren-invite (.getCdr invite)]
                  (if (.getInvited invite)
                    (+ (math/expt 0.5 level)
                       (count-points-ranking invite (inc level)))
                    0))) 
          (inc i))))))

(defn list-ranking 
  "List Ranking values from customer map."
  []
  (for [x customers]
    {(.getCar x) (count-points-ranking x 0)}))
