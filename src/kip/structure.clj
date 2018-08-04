(ns kip.structure)

(defrecord Account [id, account-name, e-mail])
(defrecord Ticket [no, status, person, subject, details])
