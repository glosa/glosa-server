(ns glosa.adapters.notify.email
  (:require
   [glosa.config :refer [config]]
   [postal.core :refer [send-message]]
   [cheshire.core :refer [parse-stream]]
   [clojure.java.io :as io]))

(def template-html-path "template-email.html")

(defn template-email-load
  "Load template email"
  []
  (cond (not (.exists (io/file template-html-path)))
        (with-open [w (clojure.java.io/writer  template-html-path)]
          (.write w (slurp (io/resource template-html-path))))))

(template-email-load)

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
