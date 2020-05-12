(ns glosa.ports.captcha
  (:require
   [glosa.config :refer [config]]))

;; Enable adapter

(if (= (config :captcha) "time") (require '[glosa.adapters.captcha.time :as adapter]))

;; Functions

(defn get-token
  "Generates a token to validate"
  [url]
  (adapter/get-token url))

(defn check-token
  "Check token is valid"
  [token url]
  (adapter/check-token token url))
