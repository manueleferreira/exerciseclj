(ns com.fnexercise.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [com.fnexercise.handler :refer :all]))

(deftest test-app
  (testing "main route"
    (let [response (app (mock/request :get "/"))]
      (is (= (:status response) 200))
      (is (= (:body response) ""))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404))))
  
  (testing "ranking route"
    (let [response (app (mock/request :get "/ranking"))]
      (is (= (:status response) 200))))
  
  (testing "ranking with params route"
    (let [response (app (mock/request :get "/ranking/1/2"))]
      (is (= (:status response) 200))
      (is (nil? 
            (:body response)))))
  
  (testing "ranking with one param route"
    (let [response (app (mock/request :get "/ranking/1"))]
      (is (= (:status response) 404))))
  
  (testing "ranking with wrong params route"
    (let [response (app (mock/request :get "/ranking/sss/2"))]
      (is (= (:status response) 404)))))
  
  