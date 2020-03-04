;;;; Views public web
(ns glosa.views.public
  (:require
   [tadam.templates :refer [render-JSON]]
   ))

(defn get-comments
  ;; View JSON
  [req]
  (render-JSON req {:url (-> req :params :url)}))

(defn status-404
;; View page 404
[req]
(render-JSON req {:status 404}))
