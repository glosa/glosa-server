;;;; Views public web
(ns glosa.views.private
  (:require
   [glosa.config :refer [config]]
   [clojure.string :as s]
   [tadam.templates :refer [render-JSON]]
   [tadam.responses :refer [response]]
   [tadam.utils :refer [get-JSON]]
   [glosa.ports.database :as database]))

(defn check-bearer-token
  [req]
  (let [authorization-raw    (or (-> req :headers (get "authorization")) "")
        authorization-bearer (s/trim (s/replace authorization-raw #"^Bearer " ""))
        token                (:token config)]
    (= authorization-bearer token)))

(defn response-401
  [req]
  (response (assoc req :WWW-Authenticate "Basic realm=\"Wrong token\"") "" 401 ""))

(defn get-threads
  "Search threads"
  [req]
  (prn (check-bearer-token req))
  (if (check-bearer-token req) (render-JSON req (database/get-threads (or (:search (get-JSON req)) ""))) (response-401 req)))
