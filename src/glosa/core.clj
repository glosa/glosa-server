(ns glosa.core
  (:require
   [environ.core :refer [env]]
   [ring.middleware.params :refer [wrap-params]]
   [ring.middleware.defaults :refer [site-defaults wrap-defaults]]
   [ring.middleware.reload :refer [wrap-reload]]
   [ring.middleware.session :refer [wrap-session]]
   [glosa.urls :refer [all-routes]]
   [ring.adapter.jetty :refer [run-jetty]]) (:gen-class))

(def wrapped-handler
  ;; Handler middlewares
  (-> all-routes
      (wrap-defaults (assoc site-defaults [:security :anti-forgery] true))
      wrap-params
      wrap-session
      (#(if (env :debug) (wrap-reload %)))))

(defn -main [& args]
  ;; Main
  ;; Welcome
  (prn (str "Open http://localhost:" (env :port)))
  ;; Run web server
  (run-jetty wrapped-handler {:port (Integer/parseInt (env :port))}))
