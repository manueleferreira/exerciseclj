(ns com.fnexercise.fnexercise)
  (use 'clojure.java.io)
  (use '[clojure.string :only (split)])

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

(defn find-customer [param] 
  (loop [i 0]
    (when (< i (.length customers))
      (let [node (nth customers i)]
        (cond
          (= (.getCar node) param) node
          :else (recur (inc i)))))))

(defn add-customer-node [node]
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

(defn read-invite-file [path]
  (with-open [rdr (reader path)]
    (doseq [line (line-seq rdr)]
      (let [list (split line #"\s")] 
        (do
          (let [x (nth list 0) y (nth list 1)]
            (add-new-invite x y)))))))


