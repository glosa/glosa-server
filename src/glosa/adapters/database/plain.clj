(ns glosa.adapters.database.plain
  (:require
    [glosa.ports.captcha :as captcha]
    [glosa.models.utils :as utils]
    [cheshire.core :refer [generate-stream parse-stream]]
    [clojure.java.io :as io]
    [clojure.string :as s]))

(def db (atom {}))
(def db-path "db.json")

(defn db-save
  "Save database"
  [update-data]
  (generate-stream update-data (clojure.java.io/writer db-path)))

(defn db-load
  "Load database"
  []
  ;; Generate file if not exist
  (when-not (.exists (io/file db-path)) (clojure.java.io/writer db-path))
  ;; Get JSON
  (reset! db (parse-stream (clojure.java.io/reader db-path) true)))

(db-load)

(defn get-deep
  "Calculate the depth of the commentary. If it is a parent, it will return 0, if it is a sub-comment 1, if it is a sub-comment of a sub-comment 2..."
  [id parents]
  (let [comment (first (filter (fn [comment] (= (:id comment) id)) @db))
        parent-id (:parent comment)
        parents-temp (if (or (empty? parents) (nil? parents)) [] parents)]
    (if (empty? (str parent-id))
      (count parents-temp)
      (recur parent-id (if (empty? (str parent-id)) [] (conj parents parent-id))))))

(defn get-all-threads
  "Get all urls threads"
  []
  (distinct (map (fn [comment] {:thread (:thread comment)}) @db)))

(defn get-threads-search
  "Search threads"
  ([]
   (get-all-threads))
  ([query]
   (filter
     (fn [comment]
       (s/includes?
         (s/upper-case (:thread comment))
         (s/upper-case query)))
     (get-all-threads))))

(defn get-comments
  "Find comments from url. Sort by createdAt and Add deep value"
  [url]
  (map (fn [comment] (assoc comment :deep (get-deep (:id comment) nil))) (sort-by :createdAt (filter #(= (:thread %) url) @db))))

(defn get-new-id
  "Generate a new id by finding out which is the highest id and uploading one"
  []
  (inc (reduce (fn [id item] (if (< id (:id item)) (:id item) id)) 0 @db)))

(defn get-email-parent
  "Get email parent"
  [id]
  (let [comment   (first (filter (fn [my-comment] (= (:id my-comment) id)) @db))
        parent-id (:parent comment)
        parent    (first (filter (fn [my-comment] (= (str parent-id) (str (:id my-comment)))) @db))]
    (when parent (:email parent))))

(defn add-comment
  "Add new comment"
  [parent author email message token thread]
  (let [check (captcha/check-token token thread)]
    (when check
      (let [update-db (conj @db {:id (get-new-id) :parent parent :createdAt (utils/get-unixtime-now) :thread thread :author author :email email :message message})
            new-id    (get-new-id)]
        (reset! db update-db)
        (db-save @db)
        new-id))))

(defn update-comment
  "Update one comment"
  [id parent author email message]
  ;; Remove comment
  (let [is-exist               (not= (count (filter (fn [comment] (= (bigint (:id comment)) (bigint id))) @db)) 0)
        db-with-update-comment (map (fn [comment] (if (= (bigint (:id comment)) (bigint id)) (assoc comment
                                                                                                    :id id
                                                                                                    :parent parent
                                                                                                    :author author
                                                                                                    :email email
                                                                                                    :message message) comment) @db))]
    (when is-exist
      ;; Update database
      (reset! db db-with-update-comment)
      ;; Save data
      (db-save @db))
    is-exist))

(defn delete-comment
  "Delete one comment"
  [id]
  ;; Remove comment
  (let [is-exist      (not= (count (filter (fn [comment] (= (bigint (:id comment)) (bigint id))) @db)) 0)
        db-without-id (filter (fn [comment] (not= (bigint (:id comment)) (bigint id))) @db)]
    (when is-exist 
      ;; Update database
      (reset! db db-without-id)
      ;; Save data
      (db-save @db))
    is-exist))
