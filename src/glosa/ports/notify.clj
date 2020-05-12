(ns glosa.ports.notify
  (:require
   [glosa.config :refer [config]]))

;; Enable adapter

(if (= (config :notify) "email") (require '[glosa.adapters.notify.email :as adapter]))

;; Functions

(defn send
  "Send message"
  [id author message thread]
  (if (not-empty (config :notify)) (adapter/send id author message thread) nil))
