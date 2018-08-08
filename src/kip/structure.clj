(ns kip.structure)

(defrecord Account [id, account-name, e-mail])
(defrecord Ticket [no, status, person, subject, detail])
; master-ticket-list
(defonce tickets (atom nil))
(defonce last-ticket-index (atom 0))

(defn clear-ticket-list []
  (reset! tickets nil)
  (reset! last-ticket-index 0))

(defn ticket? [t]
  "Ticket is must need assinged account and subject"
  (and (instance? Ticket t)
       (instance? Account (:person t))
       (< 0 (count (:subject t)))))

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
  (->Ticket @last-ticket-index 0 person subject detail))

(defn add-ticket [t]
  (when (ticket? t)
    (swap! tickets conj t)
    (reset! last-ticket-index (+ @last-ticket-index 1))))
