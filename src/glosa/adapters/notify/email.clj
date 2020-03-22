(ns glosa.adapters.notify.email
  (:require
   [glosa.config :refer [config]]
   [postal.core :refer [send-message]]))

(defn send
  "Send email"
  [message]
  (send-message {:from    (config :from)
                 :to      [(config :to)]
                 :subject (config :subject)
                 :body    message}))
