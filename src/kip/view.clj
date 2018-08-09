(ns kip.view
  (:require [kip.structure :as kst]
            [hiccup.core :as hc]))

(defn show-ticket [t]
  "format ticket 't' to html style"
  (format "<div class=\"ticket\"> </div>"))

