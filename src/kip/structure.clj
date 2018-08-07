(ns kip.structure)

(defrecord Account [id, account-name, e-mail])
(defrecord Ticket [no, status, person, subject, detail])
; master-ticket-list
(defonce tickets (atom nil))

(defn ticket? [t]
  "Ticket is must need assinged account and subject"
  (and (instance? Ticket t)
       (instance? Account (:person t))
       (< 0 (count (:subject t)))))

(defn get-latest-ticket-no []
  0)

(defn get-ticket-by-no
  ([ticket-no ticket-list]
   (when (first @ticket-list)
     (if (= (:no (first ticket-list)) ticket-no) (first ticket-list)
         (recur ticket-no (rest ticket-list)))))
  ([ticket-no]
   (get-ticket-by-no ticket-no @tickets)))

(defn find-tickets-by-account
  ([a ticket-list]
   (when (instance? Account a)
     (if (= a (:person (first ticket-list)))
       (first ticket-list)
       (recur a (rest ticket-list)))))
   ([a]
    (find-tickets-by-account a @tickets)))

(defn make-ticket [person subject detail]
  (->Ticket (get-latest-ticket-no) 0 person subject detail))

(defn add-ticket [t]
  (if (ticket? t) (swap! tickets conj t)))
