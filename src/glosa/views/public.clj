;;;; Views public web
(ns glosa.views.public
  (:require
   [tadam.templates :refer [render-JSON]]
   [tadam.utils :refer [get-JSON]]
   [glosa.ports.database :as database]
   [glosa.ports.notify :as notify]
   [glosa.ports.captcha :as captcha]
   [clojure.string :as s]))

(defn get-comments
  "All comments from url"
  [req]
  (render-JSON req (database/get-comments (-> req :params :url))))

(defn add-comment
  "Add new comment"
  [req]
  (let [my-json    (get-JSON req)
        id-comment (database/add-comment (:parent my-json) (:author my-json) (:email my-json) (:message my-json) (:token my-json) (:thread my-json))
        added?     (not (nil? id-comment))]
    ;; Notify
    (when added? (notify/send-notify id-comment (:author my-json) (:message my-json) (:thread my-json)))
    ;; Response
    (render-JSON req {
                      :added added?
                      } 200)))

(defn get-captcha
  "Get token captcha"
  [req]
  (let [url (-> req :params :url)]
    (render-JSON req (if (s/blank? url) {:error "Need URL"} (assoc {} :token (captcha/get-token url))))))

(defn pong
"Gives a simple message to record his abilities to play ping pong while he is alive"
[req]
(render-JSON req {:ping "pong"}))

(defn status-404
"Page 404"
[req]
(render-JSON req {:status "Not found"} 404))
