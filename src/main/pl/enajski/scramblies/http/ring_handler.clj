(ns pl.enajski.scramblies.http.ring-handler
  (:require [com.stuartsierra.component :as component]
            [reitit.ring]
            [reitit.ring.middleware.muuntaja]
            [muuntaja.core :as muuntaja]
            [ring.middleware.cors :refer [wrap-cors]]))

(defn resolve-routes [sym]
  (var-get (or (resolve sym)
               (do (-> sym namespace symbol require)
                   (resolve sym)))))


(defn new-handler [{:keys [opts] :as app}]
  (reitit.ring/ring-handler
   (reitit.ring/router (mapv #((resolve-routes %) app) (:routes opts))
                       {:data {:muuntaja   muuntaja/instance
                               :middleware [reitit.ring.middleware.muuntaja/format-middleware
                                            #(wrap-cors %
                                                        :access-control-allow-origin [#".*"] 
                                                        :access-control-allow-methods [:options :post]
                                                        ;; :access-control-allow-headers ["Content-Type"]
                                                        )]}})
   reitit.ring/default-options-handler))


(defrecord RingHandler [_opts instance]
 component/Lifecycle
 (start [this]
   (if instance
     this
     (assoc this :instance (new-handler this))))

  (stop [this]
    (if-not instance
      this
      (assoc this :instance nil))))


(defn init [opts]
  (map->RingHandler {:opts opts}))
