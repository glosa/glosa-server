(ns glosa.urls
  (:require
   [compojure.core :refer [defroutes context GET POST PUT DELETE]]
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
           (POST "/token/check/" [] view-public/check-token)
           (GET "/ping/" [] view-public/pong)))

(defroutes private
  ;; Urls public pages
  (context api-prefix []
           (POST "/threads/search/:query" [] view-private/get-search-threads)
           (PUT "/comments/" [] view-private/update-comment)
           (DELETE "/comments/" [] view-private/delete-comment)))

(defroutes resources-routes
  ;; Resources (statics)
  (route/resources "/")
  (route/not-found view-public/status-404))

(def all-routes
  ;; Wrap routers. "resources-routes" should always be the last.
  (compojure.core/routes public private resources-routes))
