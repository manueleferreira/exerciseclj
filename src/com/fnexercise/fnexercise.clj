(ns com.fnexercise.fnexercise)
  (use 'clojure.java.io)
  (use '[clojure.string :only (split)])
  (use 'clojure.contrib.math)

(definterface INode
  (getCar [])
  (getCdr [])
  (setCar [x])
  (setCdr [x]))

(deftype Node [^:volatile-mutable car ^:volatile-mutable cdr]
  INode
  (getCar [this] car)
  (getCdr [this] cdr)
  (setCar [this x] (set! car x) this)
  (setCdr [this x] (set! cdr x) this))


(def customers [])

(defn find-customer 
  "Find customer by number value."
  {:added "1.0"
   :static true}
  [param] 
  (loop [i 0]
    (when (< i (.length customers))
      (let [node (nth customers i)]
        (cond
          (= (.getCar node) param) node
          :else (recur (inc i)))))))

(defn add-customer-node 
  "Add new node to customer list."
  {:added "1.0"
   :static true}
  [node]
  (def customers 
    (conj customers node)))


(defn add-new-invite
  "Add new invite."
  {:added "1.0"
   :static true}
  [x y]
  (if (nil? (find-customer y))
    (let [node-x (find-customer x) node-y (Node. y nil)] 
      (do
        (if (nil? node-x) 
          (add-customer-node (Node. x (list node-y)))
          (.setCdr node-x (conj (.getCdr node-x) node-y))) 
        (add-customer-node node-y)))))

(defn read-invite-file 
  "Read invites by customers from text file."
  {:added "1.0"
   :static true}
  [path]
  (with-open [rdr (reader path)]
    (doseq [line (line-seq rdr)]
      (let [list (split line #"\s")] 
        (do
          (let [x (nth list 0) y (nth list 1)]
            (add-new-invite x y)))))))

(defn list-ranking 
  "List Ranking values from customer map."
  {:added "1.0"
   :static true}
  []
  (do
    (def map-reward {})
    (loop [i 0]
      (when (< i (.length customers))
        (let [customer (nth customers i) points (count-points-ranking customer)]
          (do
            (assoc map-reward customer points)
            (recur (inc i))))))))

(defn count-points-ranking 
  "map customer and reward value."
  {:added "1.0"
   :static true}
  [customer level]
  (let [invites (.getCdr customer)]
    (loop [i 0]
      (when (< i (.length invites))
        (let [invite (nth invites i) chidren-invite (.getCdr invite)]
          (if (no-nil? chidren-invite)
            (+ (expt (/ 1 2) level) 
               (count-points-ranking invite (inc level)))))))))




