(ns pl.enajski.scramblies.client.data
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require [cljs-http.client :as http]
            [cljs.core.async :refer [<! chan >!]]))

(defonce request-chan> (chan))


(defn fetch-result [state]
  (go (>! request-chan> state)))


(go-loop []
  (let [state    (<! request-chan>)
        response (<! (http/post "http://localhost:8888/scramble" {:edn-params        @state
                                                                  :with-credentials? false}))]
    (when (= 200 (:status response))
      (swap! state assoc :result (-> response :body :result)))

    (recur)))
