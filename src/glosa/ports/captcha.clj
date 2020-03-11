(ns glosa.ports.captcha
  (:require
   [glosa.adapters.captcha.time :as adapter]))

(defn generate-token
  "Generates a token to validate"
  []
  (adapter/generate-token))

(defn check-token
  "Check token is valid"
  [token]
  (adapter/check-token token))
