(ns glosa.ports.notify
  (:require
   [glosa.adapters.notify.email :as adapter]))

(defn send
  "Send message"
  [message]
  (adapter/send message))
