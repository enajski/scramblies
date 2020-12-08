(ns pl.enajski.scramblies.api
  (:require [pl.enajski.scramblies.core :as scramblies]))

(def scramble-handler
  (fn [{:keys [body-params]}]
    (let [{:keys [:str1 :str2]} body-params]
      {:body   {:result (scramblies/scramble str1 str2)}
       :status 200})))


(defn routes [_]
  ["/scramble"
   {:post    {:handler scramble-handler}
    :options (fn [_] {:status 200})}])
