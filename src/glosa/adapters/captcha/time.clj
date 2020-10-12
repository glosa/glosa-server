(ns glosa.adapters.captcha.time
  (:require
   [glosa.config :refer [config]]
   [glosa.models.utils :as utils]
   [clojure.string :as string]
   [cheshire.core :refer [generate-stream parse-stream]]
   [clojure.java.io :as io]))

;; Variables

(def db-path "captcha.json")

(defn db-load
  "Load database"
  []
  ;; Generate file if not exist
  (when-not (.exists (io/file db-path)) (clojure.java.io/writer db-path))
  ;; Get JSON
  (parse-stream (clojure.java.io/reader db-path) true))

(def db (atom (db-load)))
(def token-len 20)
(def min-time-seconds (if (config :debug) 0 10))

;; Functions

(defn rand-str
  "Generate random string"
  [len]
  (string/join (repeatedly len #(char (+ (rand 26) 65)))))

(defn db-save
  "Save database"
  [update-data]
  ;; Multi threading
  (generate-stream update-data (clojure.java.io/writer db-path)))

(defn clear-tokens-old
  "Clean tokens with more than one day"
  []
  (let [now          (utils/get-unixtime-now)
        nowMinus24h  (- now 86400)
        clear-tokens (filter (fn [item] (> (item :createdAt) nowMinus24h)) @db)]
    (reset! db clear-tokens)
    (db-save @db)
    clear-tokens))

(defn add-token-db
  "Add token to database"
  [token url]
  (let [now           (utils/get-unixtime-now)
        clear-tokens  (clear-tokens-old) ;; Clean tokens with more than one day
        update-tokens (conj clear-tokens {:token token :createdAt now :url url})] ;; Add new token
    (reset! db update-tokens)
    (db-save @db)))

(defn get-token
  "Generates a token to validate"
  [url]
  (let [new-token (rand-str token-len)] ;; New token
    (add-token-db new-token url) ;; Save in database
    new-token ;; Return token
    ))

(defn check-token
  "Check token is valid. Token exist and createdAt minor min-time-seconds"
  [token url]
  (let [now   (utils/get-unixtime-now)
        valid (some (fn [item] (and (= (item :token) token) (< (+ (item :createdAt now) min-time-seconds) now) (= (item :url) url))) @db)]
    (when valid (reset! db (filter (fn [item] (not= (:token item) token)) @db))) ;; Remove token
    (clear-tokens-old)
    (not (nil? valid))))
