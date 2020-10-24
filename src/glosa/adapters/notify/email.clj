(ns glosa.adapters.notify.email
  (:require
   [glosa.config :refer [config]]
   [glosa.ports.database :refer [get-email-parent]]
   [postal.core :refer [send-message]]
   [selmer.parser :as s]
   [clojure.java.io :as io]))

(def template-html-path "template-email.html")

(defn template-email-load
  "Load template email"
  []
  (cond (not (.exists (io/file template-html-path)))
        (with-open [w (clojure.java.io/writer  template-html-path)]
          (.write w (slurp (io/resource template-html-path))))))

(template-email-load)

(defn send-notify
  "Send email"
  [id author message thread]
  (.start (Thread. (fn []
                     ;; Send email Admin
                     (doseq [to [(config :admin) (get-email-parent id)]] (if-not (nil? to) (send-message {:host (config :smtp-host)
                                                                                                          :user (config :smtp-user)
                                                                                                          :pass (config :smtp-password)
                                                                                                          :port (config :smtp-port)
                                                                                                          :tls  (config :smtp-tls)}
                                                                                                         {:from    (config :from)
                                                                                                          :to      [to]
                                                                                                          :subject (config :subject)
                                                                                                          :body    [{:type    "text/html; charset=\"UTF-8\""
                                                                                                                     :content (s/render (slurp template-html-path) {:author  author
                                                                                                                                                                    :message message
                                                                                                                                                                    :thread  thread})}]}) nil))))))
