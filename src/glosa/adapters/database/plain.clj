(ns glosa.adapters.database.plain
  (:require
   [cheshire.core :refer [generate-stream parse-stream]]
   [clojure.java.io :as io]))

(def db (atom {}))
(def db-path "db.json")

(defn db-save
  "Save database"
  [update-data]
  (generate-stream update-data (clojure.java.io/writer (.getFile (io/resource db-path)))))

(defn db-load
  "Load database"
  []
  (reset! db (parse-stream (clojure.java.io/reader (io/resource db-path)) true)))

(db-load)

(defn get-comments
  "Find comments from url"
  [url]
  (sort-by :createdAt (filter #(= (:thread %) url) @db)))

(defn add-comment
  "Add new comment"
  [url parent author message])

