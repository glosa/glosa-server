;;;; Views public web
(ns glosa.views.public
  (:require
   [tadam.templates :refer [render-JSON]]
   [clj-yaml.core :as yaml]
   [glosa.ports.database :as database]))

(def config (yaml/parse-string (slurp "config.yaml")))

(defn is-valid-domain
  "Check if the request comes from a valid domain"
  [req]
  (= (get-in req [:headers "host"]) (config :domain)))

(defn get-comments
  "All comments from url"
  [req]
  (render-JSON req (if (is-valid-domain req)(database/get-comments (-> req :params :url)) {})))

(defn status-404
  "Page 404"
  [req]
  (render-JSON req {:status 404}))
