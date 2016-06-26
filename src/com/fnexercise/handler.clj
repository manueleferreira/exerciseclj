(ns com.fnexercise.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [compojure.handler :refer [site]]
            [ring.util.response :refer [resource-response response]]
            [ring.middleware.reload :as reload]
            [ring.middleware.json :as middleware]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [com.fnexercise.core :as fncore]
            [org.httpkit.server :refer [run-server]]))

(defroutes app-routes
  (GET "/" [] 
       {:status 200 :body "Hello World"})
  (GET "/ranking" [] 
       {:status 200 :body "ranking"})
  (route/resources "/")
  (route/not-found "Page not found"))
;  (GET "/ranking" [] (response (fncore/list-ranking)))
;  (GET "/ranking/:x/:y" [x y] (response (fncore/add-new-invite x y)))

(def app
  (-> app-routes
      (middleware/wrap-json-body)
      (middleware/wrap-json-response)
      (wrap-defaults api-defaults)))

(defn -main [& args] 
  (run-server (site app-routes) {:port 3000}))
