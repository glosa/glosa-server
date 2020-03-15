(ns glosa.models.utils)

(defn get-unixtime-now
  "Get unixtime now"
  []
  (quot (System/currentTimeMillis) 1000))
