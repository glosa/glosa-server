(ns glosa.adapters.database.plain
  (:require
   [glosa.ports.captcha :as captcha]
   [glosa.models.utils :as utils]
   [cheshire.core :refer [generate-stream parse-stream]]
   [clojure.java.io :as io]))

(def db (atom {}))
(def db-path "db.json")

(defn db-save
  "Save database"
  [update-data]
  (generate-stream update-data (clojure.java.io/writer (.getFile db-path))))

(defn db-load
  "Load database"
  []
  (reset! db (parse-stream (clojure.java.io/reader db-path) true)))

(db-load)

(defn get-comments
  "Find comments from url"
  [url]
  (sort-by :createdAt (filter #(= (:thread %) url) @db)))

(defn get-new-id
  "Generate a new id by finding out which is the highest id and uploading one"
  []
  (+ 1 (reduce (fn [id item]
                 (if (< id (item :id))
                   (item :id)
                   id
                   ))
               @db)))

(defn add-comment
  "Add new comment"
  [parent author message token thread]
  (let [id  4
        now utils/get-unixtime-now]
    (captcha/check-token token thread)
    )
  )

