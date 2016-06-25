(ns com.fnexercise.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defn return-fn []
  {1 "Bob"})

(defroutes app-routes
  (GET "/ranking" [] (return-fn))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
