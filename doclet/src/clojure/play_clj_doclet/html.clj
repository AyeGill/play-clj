(ns play-clj-doclet.html
  (:require [clojure.string :as string]
            [hiccup.core :refer :all]))

(defn param
  [[type-name param-name]]
  (html [:span {}
         [:span {:class "type-name"} "^" type-name]
         " "
         param-name]))

(defn item
  [{:keys [name text type args]}]
  [:div
   [:p
    [:b (str name)]
    " "
    (string/join ", " (map param args))]
   (when text
     [:i text])])

(defn create-from-file
  [{:keys [ns groups] :as parsed-file}]
  [:div
   (when (and ns (> (count groups) 0))
     [:h1 ns])
   (for [group groups]
     [:div
      [:h2 {} (:name group)]
      (:docstring group)
      (for [[name items] (:java group)]
        [:div
         (when (not= (:name group) name)
           [:h3 name])
         (map item items)])])])

(defn create
  [parsed-files]
  (html (map create-from-file parsed-files)))