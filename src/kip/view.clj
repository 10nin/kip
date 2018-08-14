(ns kip.view
  (:require [hiccup.core :as hc]
            [hiccup.table :as ht]))

(defn gen-summary-table [ticket-summary]
  "get hiccup style table from 'ticket-summary'.
ticket-summary contains {ticket-no, ticket-status, assined-account-name, ticket-subject}, it is not Ticket structure."
  (ht/to-table1d ticket-summary
                 [:no "#", :status "Status", :assign "Assin-to", :subject "Subject"]))

(defn make-summary-html-table [ticket-summary]
  (hc/html (gen-summary-table ticket-summary)))
