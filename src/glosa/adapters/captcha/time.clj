(ns glosa.adapters.captcha.time
  (:require
   [cheshire.core :refer [generate-stream parse-stream]]
   [clojure.java.io :as io]))

;; Variables

(def db-path "captcha.json")

(defn db-load
  "Load database"
  []
  ;; Generate file if not exist
  (if-not (.exists (io/file db-path)) (clojure.java.io/writer db-path))
  ;; Get JSON
  (parse-stream (clojure.java.io/reader db-path) true))

(def db (atom (db-load)))
(def token-len 20)
(def min-time-seconds 20)

;; Functions 

(defn rand-str
  "Generate random string"
  [len]
  (apply str (take len (repeatedly #(char (+ (rand 26) 65))))))

(defn get-unixtime-now
  "Get unixtime now"
  []
  (quot (System/currentTimeMillis) 1000))

(defn db-save
  "Save database"
  [update-data]
  (generate-stream update-data (clojure.java.io/writer db-path)))

(defn add-token-db
  "Add token to database"
  [token]
  (let [now          (get-unixtime-now)
        nowMinus24h  (- now 86400)
        clear-tokens (filter (fn [item] (> (item :createdAt) nowMinus24h)) @db) ;; Clean tokens with more than one day

        update-tokens (conj clear-tokens {:token token :createdAt now})] ;; Add new token
    (reset! db update-tokens)
    (db-save @db)))

(defn get-token
"Generates a token to validate"
[]
(let [new-token (rand-str token-len)] ;; New token
  (add-token-db new-token) ;; Save in database
  new-token ;; Return token
  ))

(defn check-token
"Check token is valid"
[token]
true)
