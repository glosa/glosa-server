;;;; Views public web
(ns glosa.views.private
  (:require
   [glosa.config :refer [config]]
   [clojure.string :as s]
   [tadam.templates :refer [render-JSON]]
   [tadam.responses :refer [response]]
   [tadam.utils :refer [get-JSON get-header]]
   [glosa.ports.database :as database]))

(defn check-bearer-token
  [req]
  (let [authorization-raw    (or (get-header req "authorization") "")
        authorization-bearer (s/trim (s/replace authorization-raw #"^Bearer " ""))
        token                (:token config)]
    (= authorization-bearer token)))

(defn response-401
  [req]
  (response (assoc req :WWW-Authenticate "Basic realm=\"Wrong token\"") "" 401 ""))

(defn get-search-threads
  "Search threads"
  [req]
  (if (check-bearer-token req) (render-JSON req (database/get-threads-search (or (-> req :params :query) ""))) (response-401 req)))

(defn delete-comment
  "Delete one comment"
  [req]
  (let [id (:id (get-JSON req))]
    (if (check-bearer-token req)
      (render-JSON req {:deleted (database/delete-comment id) :id (bigint id)})
      (response-401 req))))

