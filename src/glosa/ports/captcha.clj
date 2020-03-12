(ns glosa.ports.captcha
  (:require
   [glosa.adapters.captcha.time :as adapter]))

(defn get-token
  "Generates a token to validate"
  []
  (adapter/get-token))

(defn check-token
  "Check token is valid"
  [token]
  (adapter/check-token token))
