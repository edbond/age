(defproject age "1.0.0-SNAPSHOT"
  :description "FIXME: write"
  :dependencies [[org.clojure/clojure "1.2.0-RC2"]
                 [org.clojure/clojure-contrib "1.2.0-RC2"]
                 [compojure "0.4.1"]
                 [enlive "1.0.0-SNAPSHOT"]
                 [ring/ring-core "0.2.5"]
                 [ring/ring-devel "0.2.5"]
                 [ring/ring-jetty-adapter "0.2.5"]
                 [inflections "0.4"]
                 [joda-time "1.6"]]
  :dev-dependencies [[swank-clojure "1.3.0-SNAPSHOT"]
                     [lein-run "1.0.0-SNAPSHOT"]]
  :jvm-opts ["-server" "-d64"])
