(ns com.fnexercise.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [compojure.handler :refer [site]]
            [ring.util.response :refer [resource-response response]]
            [ring.middleware.reload :as reload]
            [ring.middleware.json :as middleware]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [com.fnexercise.core :as fncore]
            [datomic.api :as d]))

(defroutes app-routes
  (GET "/" [] "")
  (GET "/ranking" [] 
       (response (fncore/list-ranking)))
  (GET "/ranking/:x{[0-9]+}/:y{[0-9]+}" [x y]
       (response (fncore/add-new-invite x y)))
  (route/resources "/")
  (route/not-found "Page not found"))

(def app
  (-> app-routes
      (middleware/wrap-json-body)
      (middleware/wrap-json-response)
      (wrap-defaults api-defaults)))

(defn -main [& args] 
  (run-server (site app) {:port 3000}))
