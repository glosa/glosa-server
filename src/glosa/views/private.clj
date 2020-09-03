;;;; Views public web
(ns glosa.views.private
  (:require
   [glosa.config :refer [config]]
   [clojure.string :as s]
   [tadam.templates :refer [render-JSON]]
   [tadam.responses :refer [response]]
   [tadam.utils :refer [get-JSON]]
   [glosa.ports.database :as database]
   [glosa.ports.notify :as notify]
   [glosa.ports.captcha :as captcha]))

(defn check-bearer-token
  [req]
  (let [authorization-raw    (or (:Authorization (get-JSON req)) "")
        authorization-bearer (s/trim (s/replace authorization-raw #"^Bearer " ""))
        admin-token          (:admin-token config)]
    (= authorization-bearer admin-token)))

(defn response-401
  [req]
  (response (assoc req :WWW-Authenticate "Basic realm=\"Wrong administrator token\"") "" 401 ""))

(defn get-threads
  "Search threads"
  [req]
  (if (check-bearer-token req) (render-JSON req (database/get-threads (or (:search (get-JSON req)) ""))) (response-401 req)))
