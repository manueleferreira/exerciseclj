(ns com.fnexercise.file
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [com.fnexercise.core :refer :all]))

(defn read-invite-file 
  "Read invites by customers from text file."
  [path]
  (with-open [rdr (io/reader path)]
    (doseq [line (line-seq rdr)]
      (let [list (str/split line #"\s") 
            x (nth list 0) 
            y (nth list 1)]
            (add-new-invite x y)))))