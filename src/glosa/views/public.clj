;;;; Views public web
(ns glosa.views.public
  (:require
   [tadam.templates :refer [render-JSON]]
   [cheshire.core :refer [generate-stream]]
   [clojure.java.io :as io]
   ))

(def db {})
(def db-path "db.json")

(defn db-save
  "Save database"
  [update-data]
  (generate-stream update-data (clojure.java.io/writer (.getFile (io/resource db-path)))))

(defn get-comments
  ;; View JSON
  [req]
  (render-JSON req {:url (-> req :params :url)}))

(defn status-404
  ;; View page 404
  [req]
  (render-JSON req {:status 404}))
