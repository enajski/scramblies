(ns pl.enajski.scramblies.system
  (:require [com.walmartlabs.schematic :as sc]
            [com.stuartsierra.component :as component]
            [clojure.java.io :as jio]
            [clojure.edn :as edn]

            [pl.enajski.scramblies.http.ring-handler]
            [pl.enajski.scramblies.http.server]))

(defn read-config [path]
  (let [file (jio/resource path)]
    (if file
      (edn/read (java.io.PushbackReader. (jio/reader file)))
      (throw (ex-info (str "Missing system config: " path) {:path path})))))


(defn start-system []
  (->> (sc/assemble-system (read-config "sys-config.edn"))
       (component/start-system))
  (println "READY"))


(defn -main []
  (start-system))

