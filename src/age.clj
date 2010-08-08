(ns age
  (:use compojure.core
        ring.adapter.jetty)
  (:use ring.middleware.reload)
  (:use ring.middleware.stacktrace)
  (:use inflections)
  (:require [net.cgrand.enlive-html :as enlive])
  (:require [compojure.route :as route])
  (:import [org.joda.time Period DateTime]
           [org.joda.time.format DateTimeFormat]))

(enlive/deftemplate index "index.html"
  [context]
  [:input.datetime] (enlive/set-attr :value (context :dob))
  [:div#age-result] (enlive/content (context :age))
  [:#flash] (if-not (context :error) nil identity)
  [:#errors] (enlive/content (context :error)))

(def datetime-format
     (DateTimeFormat/forPattern "yyyy-MM-dd"))

(defn pl
  [n string]
  (str n (if (= n 1)
           string
           (pluralize string))))

(defn calc-age
  [dob]
  (let [born (. datetime-format parseDateTime dob)
        today (DateTime.)
        period (Period. born today)]
    (apply str (interpose ", "
                          [(pl (. period getYears) " year")
                           (pl (. period getMonths) " month")
                           (pl (. period getDays) " day")]))))

(defroutes age-routes
  (GET "/" [] (index {}))
  (GET "/age" [dob]
       (try
         (index {:dob dob :age (calc-age dob)})
         (catch IllegalArgumentException e
           (index {:dob dob :error (. e getMessage)}))))
  (route/files "/" {:root "src/public"})
  (route/not-found "Page not found"))

(def app
     (-> #'age-routes
         (wrap-stacktrace)
;         (wrap-reload '(age))
         ))

;(future (run-jetty (var app) {:port 8080}))
(run-jetty app {:port 8080})
