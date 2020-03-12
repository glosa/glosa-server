;;;; Views public web
(ns glosa.views.public
  (:require
   [tadam.templates :refer [render-JSON]]
   [glosa.config :refer [config]]
   [glosa.ports.database :as database]
   [glosa.ports.captcha :as captcha]))

(defn is-valid-domain
  "Check if the request comes from a valid domain"
  [req]
  (= (get-in req [:headers "host"]) (str (config :domain) ":" (config :port))))

(defn get-comments
  "All comments from url"
  [req]
  (render-JSON req (if (is-valid-domain req) (database/get-comments (-> req :params :url)) {})))

(defn get-captcha
  "Get token captcha"
  [req]
  (render-JSON req (if (is-valid-domain req) (assoc {} :token (captcha/get-token)) {})))

(defn status-404
  "Page 404"
  [req]
  (render-JSON req {:status 404}))
