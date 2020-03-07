(ns glosa.ports.database
  (:require
   [glosa.adapters.database.plain :as adapter]))

(defn get-comments
  "Find comments from url"
  [url]
  (adapter/get-comments url))

(defn add-comment
  "Add new comment"
  [url parent author message]
  (adapter/add-comment url parent author message))
