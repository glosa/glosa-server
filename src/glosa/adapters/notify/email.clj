(ns glosa.adapters.notify.email
  (:require
   [glosa.config :refer [config]]
   [postal.core :refer [send-message]]))

(defn send
  "Send email"
  [message]
  (.start (Thread. (fn [] (send-message {:host (config :smtp-host)
                                         :user (config :smtp-user)
                                         :pass (config :smtp-password)
                                         :port (config :smtp-port)
                                         :tls  (config :smtp-tls)}
                                        {:from    (config :from)
                                         :to      [(config :to)]
                                         :subject (config :subject)
                                         :body    message})))))
