(ns glosa.ports.database
  (:require
   [glosa.config :refer [config]]))

;; Enable adapter

(when (= (config :database) "plain") (require '[glosa.adapters.database.plain :as adapter]))

;; Functions

(defn get-comments
  "Find comments from url"
  [url]
  (adapter/get-comments url))

(defn get-threads
  "Find threads from name"
  ([]
   (adapter/get-threads))
  ([name]
  (adapter/get-threads name)))

(defn get-email-parent
  "Get email from parent comment"
  [id]
  (adapter/get-email-parent id))

(defn add-comment
  "Add new comment"
  [parent author email message token thread]
  (adapter/add-comment parent author email message token thread))


(defn delete-comment
  "Delete one comment"
  [id]
  (adapter/delete-comment id))
