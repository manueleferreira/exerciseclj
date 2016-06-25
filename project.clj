(defproject com.fnexercise "0.1.0-SNAPSHOT"
  :description "Reward system"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/math.numeric-tower "0.0.4"]
                 [compojure "1.4.0"]
                 [ring/ring-defaults "0.1.5"]
                 [c3p0/c3p0 "0.9.1.2"]
                 [org.clojure/java.jdbc "0.2.3"]
                 [com.h2database/h2 "1.3.168"]
                 [cheshire "4.0.3"]]
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler com.fnexercise.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]]}})
