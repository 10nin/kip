(ns kip.structure)

; master-ticket-list
(defrecord Account [id, account-name, e-mail])
(defrecord Ticket [no, status, person, subject, detail])
(defonce tickets (atom nil :validator (fn [a] (or (instance? Ticket a) (nil? a)))))

(defn account-equals [a b]
  (if (and
         (= (:id a) (:id b))
         (= (:account-name a) (:account-name b))
         (= (:e-mail a) (:e-mail b)))
    true
    false))

(defn get-latest-ticket-no []
  0)

(defn get-ticket-by-no
  ([ticket-no ticket-list]
   (when (first @ticket-list)
     (if (= (:no (first ticket-list)) ticket-no) (first ticket-list)
         (get-ticket-by-no [ticket-no (rest ticket-list)]))))
  ([ticket-no]
   (get-ticket-by-no ticket-no @tickets)))

(defn find-tickets-by-account
  ([a ticket-list]
   (when (instance? Account a)
     (if (account-equals a (:person (first ticket-list)))
       (first ticket-list)
       (find-tickets-by-account a (rest ticket-list)))))
   ([a]
    (find-tickets-by-account a @tickets)))

(defn make-ticket [person subject detail]
  (->Ticket (get-latest-ticket-no) 0 person subject detail))

(defn add-ticket [t]
  (swap! tickets conj t))
