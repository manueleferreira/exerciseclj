(ns com.fnexercise.structure)

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