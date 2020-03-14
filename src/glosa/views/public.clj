;;;; Views public web
(ns glosa.views.public
  (:require
   [tadam.templates :refer [render-JSON]]
   [cheshire.core :refer [parse-string]]
   [glosa.config :refer [config]]
   [glosa.ports.database :as database]
   [glosa.ports.captcha :as captcha]))

(defn is-valid-domain
  "Check if the request comes from a valid domain"
  [req]
  (= (get-in req [:headers "host"]) (str (config :domain) ":" (config :port))))

(defn get-comments
  "All comments from url"
  [req]
  (render-JSON req (if (is-valid-domain req) (database/get-comments (-> req :params :url)) {:status 401})))

(defn add-comment
  "Add new comment"
  [req]
  ;; Get params
  ;; Check token
  ;; Remove token
  ;; Save comment
  ;; Return
  (def temp ((parse-string (slurp (-> req :body)) true) :token))
  (if (captcha/check-token temp)
    (let []
      (render-JSON req {:status 200})
      )
    (render-JSON req {:status 401})
    )
  )

(defn get-captcha
  "Get token captcha"
  [req]
  (render-JSON req (if (is-valid-domain req) (assoc {} :token (captcha/get-token)) {:status 401})))


(defn status-404
  "Page 404"
  [req]
  (render-JSON req {:status 404}))
