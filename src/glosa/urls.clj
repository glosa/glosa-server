(ns glosa.urls
  (:require
   [compojure.core :refer [defroutes context GET POST DELETE]]
   [compojure.route :as route]
   [glosa.views.public :as view-public]
   [glosa.views.private :as view-private]))

(def api-prefix "/api/v1")

(defroutes public
  ;; Urls public pages
  (context api-prefix []
           (GET "/comments/" [] view-public/get-comments)
           (POST "/comments/" [] view-public/add-comment)
           (GET "/captcha/" [] view-public/get-captcha)
           (GET "/ping/" [] view-public/pong)))

(defroutes private
  ;; Urls public pages
  (context api-prefix []
           (GET "/threads/" [] view-private/get-threads)
           (DELETE "/comments/:id" [id] view-private/delete-comment)))

(defroutes resources-routes
  ;; Resources (statics)
  (route/resources "/")
  (route/not-found view-public/status-404))

(def all-routes
  ;; Wrap routers. "resources-routes" should always be the last.
  (compojure.core/routes public private resources-routes))
