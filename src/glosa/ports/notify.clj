(ns glosa.ports.notify
  (:require
   [glosa.config :refer [config]]))

;; Enable adapter

(when (= (config :notify) "email") (require '[glosa.adapters.notify.email :as adapter]))

;; Functions

(defn send-notify
  "Send message"
  [id author message thread]
  (when (not-empty (config :notify)) (adapter/send-notify id author message thread)))
