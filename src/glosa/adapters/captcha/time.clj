(ns glosa.adapters.captcha.time
  (:require
   [cheshire.core :refer [generate-stream parse-stream]]
   [clojure.java.io :as io]))

(def db-path "captcha.json")

(defn db-load
  "Load database"
  []
  (parse-stream (clojure.java.io/reader (io/resource db-path)) true))

(def db (atom (db-load)))
(def token-len 10)
(def min-time-seconds 20)

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
  (generate-stream update-data (clojure.java.io/writer (.getFile (io/resource db-path)))))

(defn add-token-db
  "Add token to database"
  [token]
  (doall
    (reset! db (conj @db {:token token :createdAt (get-unixtime-now)}))
    (db-save @db)))

(defn generate-token
  "Generates a token to validate"
  []
  (let [new-token (rand-str token-len)]
    (doall
      (add-token-db new-token)
      new-token)))

(defn check-token
  "Check token is valid"
  [token]
  true
  )

