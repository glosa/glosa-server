(ns glosa.core
  (:require
   [glosa.config :refer [config]]
   [ring.middleware.params :refer [wrap-params]]
   [ring.middleware.defaults :refer [site-defaults wrap-defaults]]
   [ring.middleware.reload :refer [wrap-reload]]
   [ring.middleware.session :refer [wrap-session]]
   [glosa.urls :refer [all-routes]]
   [ring.adapter.jetty :refer [run-jetty]]) (:gen-class))

(def wrapped-handler
  ;; Handler middlewares
  (-> all-routes
      (wrap-defaults (assoc-in site-defaults [:security :anti-forgery] false))
      wrap-params
      wrap-session
      (#(if (config :debug) (wrap-reload %)))))

(defn -main [& args]
  ;; Main
  ;; Welcome
  (prn (str "Open " (config :domain) ":" (config :port)))
  ;; Run web server
  (run-jetty wrapped-handler {:port (config :port)}))
