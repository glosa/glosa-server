(ns glosa.urls
  (:require
   [compojure.core :refer [defroutes GET]]
   [compojure.route :as route]
   [glosa.views.public :as view-public]))

(def api-prefix "/api/v1/")

(defroutes public
  ;; Urls public pages
  (GET (str api-prefix "comments/") [] view-public/get-comments)
  (GET (str api-prefix "captcha/") [] view-public/get-captcha))

(defroutes resources-routes
  ;; Resources (statics)
  (route/resources "/")
  (route/not-found view-public/status-404))

(def all-routes
  ;; Wrap routers. "resources-routes" should always be the last.
  (compojure.core/routes public resources-routes))
