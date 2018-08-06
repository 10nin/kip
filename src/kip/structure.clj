(ns kip.structure)

; master-ticket-list
(defonce tickets (atom nil))
(defrecord Account [id, account-name, e-mail])
(defrecord Ticket [no, status, person, subject, detail])

(defn get-latest-ticket-no []
  0)

(defn get-ticket-by-no
  ([ticket-no ticket-list]
   (when (first @ticket-list)
     (if (= (:no (first ticket-list)) ticket-no) (first ticket-list)
         (get-ticket-by-no [ticket-no (rest ticket-list)]))))
  ([ticket-no]
   (get-ticket-by-no ticket-no @tickets)))

(defn find-tickets-by-person [p]
  nil)

(defn make-ticket [person subject detail]
  (->Ticket (get-latest-ticket-no) 0 person subject detail))

(defn add-ticket [t]
  (swap! tickets conj t))
