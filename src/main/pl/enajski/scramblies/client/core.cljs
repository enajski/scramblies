(ns pl.enajski.scramblies.client.core
  (:require [reagent.core :as reagent]
            [reagent.dom]
            [pl.enajski.scramblies.client.data :as data]))

(defonce state (reagent/atom {}))


(defn update-input [key]
  (fn [e]
    (swap! state assoc key (-> e .-target .-value))))


(defn TitleComponent []
  [:h1 "Scramblies"])


(defn InputsComponent []
  [:div
   [:span
    "Can a portion of "
    [:input#str1 {:type      "text"
                  :value     (:str1 @state)
                  :on-change (update-input :str1)}]
    " be rearranged to produce "
    [:input#str2 {:type      "text"
                  :value     (:str2 @state)
                  :on-change (update-input :str2)}]
    "? "]
   [:button#submit {:on-click (fn [_e]
                                (data/fetch-result state))}
     "SCRAMBLE"]])


(defn ResultComponent []
  [:div
   (when-some [result (:result @state)]
     [:h2 (if (true? result) "Yes" "No")])])


(defn RootComponent []
  [:div
   [TitleComponent]
   [InputsComponent]
   [ResultComponent]])


(defn mount-reagent []
  (reagent.dom/render [RootComponent]
                      (js/document.getElementById "root")))


(defn init []
  (mount-reagent)
  (println "Running"))

