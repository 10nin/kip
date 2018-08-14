(ns kip.structure)

(defrecord Account [id, account-name, e-mail])
(defrecord Ticket [no, status, person, subject, detail])

; master-ticket-list and last index
(def tickets (atom nil))
(def latest-ticket-no (ref 0))
(def latest-account-id (ref 0))

(defn get-id []
  "generate id for new Account"
  (dosync
   (ref-set latest-account-id (+ 1 @latest-account-id))))

(defn clear-ticket-list []
  "reset ticket list and last no"
  (dosync 
   (reset! tickets nil)
   (reset! latest-ticket-no 0)
   (reset! latest-account-id 0)))

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
  ([a ticket-list]
   (filter #((and (instance? Account a) (= a (:person (first %))))) ticket-list))
  ([a] ;for outer interface
   (find-tickets-by-account a @tickets)))

(defn remove-ticket-by-no [n]
  "remove ticket in ticket list by ticket 'no'"
  (reset! tickets (remove (get-ticket-by-no n) @tickets)))

(defn make-ticket [person subject detail]
  "create new ticket"
  (->Ticket @latest-ticket-no 0 person subject detail))

(defn add-ticket [t]
  "add ticket 't' to ticket list. if invalidate t then return nil and don't add to ticket list."
  (when (ticket? t)
    (dosync
     (swap! tickets conj t)
     (reset! latest-ticket-no (+ @latest-ticket-no 1)))))

(defn ticket-to-summary [t]
  {:no (:no t)
   :status (:status t)
   :assign (:account-name (:person t))
   :subject (:subject t)})

(defn ticket-list-to-summary [ticket-list]
  (map ticket-to-summary ticket-list))
