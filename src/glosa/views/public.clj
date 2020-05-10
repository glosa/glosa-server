;;;; Views public web
(ns glosa.views.public
  (:require
   [tadam.templates :refer [render-JSON]]
   [tadam.utils :refer [get-JSON]]
   [glosa.ports.database :as database]
   [glosa.ports.notify :as notify]
   [glosa.ports.captcha :as captcha]))

(defn get-comments
  "All comments from url"
  [req]
  (render-JSON req (database/get-comments (-> req :params :url))))

(defn add-comment
  "Add new comment"
  [req]
  (let [my-json (get-JSON req)]
    (if (database/add-comment (:parent my-json) (:author my-json) (:message my-json) (:token my-json) (:thread my-json))
      (do
        (notify/send (format "Author: %s\nMessage: %s\nThread: %s" (:author my-json) (:message my-json) (:thread my-json)))
        (render-JSON req {} 200))
      (render-JSON req {} 401))))

(defn get-captcha
  "Get token captcha"
  [req]
  (render-JSON req (assoc {} :token (captcha/get-token (-> req :params :url)))))

(defn pong
  "Gives a simple message to record his abilities to play ping pong while he is alive"
  [req]
  (render-JSON req {:ping "pong"}))

(defn status-404
  "Page 404"
  [req]
  (render-JSON req {} 404))
