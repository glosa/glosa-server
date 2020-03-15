(ns glosa.ports.captcha
  (:require
   [glosa.adapters.captcha.time :as adapter]))

(defn get-token
  "Generates a token to validate"
  [url]
  (adapter/get-token url))

(defn check-token
  "Check token is valid"
  [token url]
  (adapter/check-token token url))
