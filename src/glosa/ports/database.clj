(ns glosa.ports.database
  (:require
   [glosa.adapters.database.plain :as adapter]))

(defn get-comments
  "Find comments from url"
  [url]
  (adapter/get-comments url))

(defn add-comment
  "Add new comment"
  [parent author message token thread]
  (adapter/add-comment parent author message token thread))
