(ns kip.structure)

; master-ticket-list
(defrecord Account [id, account-name, e-mail])
(defrecord Ticket [no, status, person, subject, detail])
(defonce tickets (atom nil :validator (fn [a] (or (instance? Ticket a) (nil? a)))))

(defn get-latest-ticket-no []
  0)

(defn get-ticket-by-nofn [a] (or (i
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
