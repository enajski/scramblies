(ns pl.enajski.scramblies.http.server
  (:require [com.stuartsierra.component :as component]
            [aleph.http :as http])
  (:import [java.io Closeable]))

(defrecord HttpServer [opts instance handler]
  component/Lifecycle
  (start [this]
    (if instance
      this
      (let [aleph-opts {:port (get opts :port)}
            instance   (http/start-server (get handler :instance) aleph-opts)]
        (println "Starting server with opts: " aleph-opts)
        (assoc this :instance instance))))

  (stop [this]
    (if-not instance
      this
      (try
        (.close ^Closeable instance)
        (assoc this :instance nil)))))


(defn init [opts]
  (map->HttpServer {:opts opts}))
