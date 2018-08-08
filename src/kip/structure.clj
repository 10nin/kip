(ns kip.structure)

(defrecord Account [id, account-name, e-mail])
(defrecord Ticket [no, status, person, subject, detail])

; master-ticket-list and last index
(def tickets (atom nil))
(def last-ticket-no (atom 0))

(defn get-id []
  "generate id for new Account"
  "000000000")

(defn clear-ticket-list []
  "reset ticket list and last no"
  (reset! tickets nil)
  (reset! last-ticket-no 0))

(defn ticket? [t]
  "Ticket is must need assinged account and subject"
  (and (instance? Ticket t)
       (instance? Account (:person t))
       (< 0 (count (:subject t)))))

(defn get-ticket-by-no
  "find ticket in ticket list by ticket no. if can't find a ticket then return nil."
  ([ticket-no ticket-list]
   (when (first ticket-list)
     (if (= (:no (first ticket-list)) ticket-no) (first ticket-list)
         (recur ticket-no (rest ticket-list)))))
  ([ticket-no] ;for outer interface
   (get-ticket-by-no ticket-no @tickets)))

(defn find-tickets-by-account
  "find ticket list in ticket list by assigned account. if can't find a ticket then return nil."
  "TODO: make return value *TICKET LIST* not a ticket."
  ([a ticket-list]
   (when (instance? Account a)
     (if (= a (:person (first ticket-list)))
       (first ticket-list)
       (recur a (rest ticket-list)))))
   ([a] ;for outer interface
    (find-tickets-by-account a @tickets)))

(defn remove-ticket-by-no [n]
  "remove ticket in ticket list by ticket 'no'"
  (reset! tickets (remove (get-ticket-by-no n) @tickets)))

(defn make-ticket [person subject detail]
  "create new ticket"
  (->Ticket @last-ticket-no 0 person subject detail))

(defn add-ticket [t]
  "add ticket 't' to ticket list. if invalidate t then return nil and don't add to ticket list."
  (when (ticket? t)
    (swap! tickets conj t)
    (reset! last-ticket-no (+ @last-ticket-no 1))))
