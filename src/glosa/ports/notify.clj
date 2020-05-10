(ns glosa.ports.notify
  (:require
   [glosa.config :refer [config]]))

(if (= (config :notify) "email") (require '[glosa.adapters.notify.email :as adapter]))

(defn send
  "Send message"
  [author message thread]
  (if (not-empty (config :notify)) (adapter/send author message thread)))
