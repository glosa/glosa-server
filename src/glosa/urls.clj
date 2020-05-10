(ns glosa.urls
  (:require
   [compojure.core :refer [defroutes context GET POST]]
   [compojure.route :as route]
   [glosa.views.public :as view-public]))

(def api-prefix "/api/v1")

(defroutes public
  ;; Urls public pages
  (context api-prefix []
           (GET "/comments/" [] view-public/get-comments)
           (POST "/comments/" [] view-public/add-comment)
           (GET "/captcha/" [] view-public/get-captcha)
           (GET "/ping/" [] view-public/pong)))

(defroutes resources-routes
  ;; Resources (statics)
  (route/resources "/")
  (route/not-found view-public/status-404))

(def all-routes
  ;; Wrap routers. "resources-routes" should always be the last.
  (compojure.core/routes public resources-routes))
