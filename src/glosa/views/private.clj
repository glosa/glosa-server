;;;; Views privates
(ns glosa.views.private
  (:require
   [glosa.models.authorization :refer [check-bearer-token]]
   [tadam.templates :refer [render-JSON]]
   [tadam.responses :refer [response]]
   [tadam.utils :refer [get-JSON]]
   [glosa.ports.database :as database]))

(defn response-401
  [req]
  (response (assoc req :WWW-Authenticate "Basic realm=\"Wrong token\"") "" 401 ""))

(defn get-latest-comments
  "All latest comments"
  [req]
  (if (check-bearer-token req)
    (render-JSON req (database/get-latest-comments))
    (response-401 req)))

(defn get-search-threads
  "Search threads"
  [req]
  (if (check-bearer-token req)
    (render-JSON req (database/get-threads-search (or (-> req :params :query) "")))
    (response-401 req)))

(defn update-comment
"Update one comment"
[req]
(let [data    (get-JSON req)
      id      (:id data)
      author  (:author data)
      email   (:email data)
      message (:message data)]
  (if (check-bearer-token req)
    (render-JSON req {:updated (database/update-comment id author email message) :id (bigint id)})
    (response-401 req))))

(defn delete-comment
"Delete one comment"
[req]
(let [id (:id (get-JSON req))]
  (if (check-bearer-token req)
    (render-JSON req {:deleted (database/delete-comment id) :id (bigint id)})
    (response-401 req))))

