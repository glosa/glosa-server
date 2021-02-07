(ns glosa.models.authorization
  (:require
   [glosa.config :refer [config]]
   [clojure.string :as s]
   [tadam.utils :refer [get-header]]))

(defn check-bearer-token
  [req]
  (let [authorization-raw    (or (get-header req "authorization") "")
        authorization-bearer (s/trim (s/replace authorization-raw #"^Bearer " ""))
        token                (:token config)]
    (= authorization-bearer token)))
