(ns kip.core
  (:require [ring.adapter.jetty :as s]))

(defonce server (atom nil))

(defn handler [req]
  {:status 200,
   :headers {"Content-Type" "text/plain"},
   :body "Hello, world"})

(defn start-server []
  (when-not @server
    (reset! server (s/run-jetty handler {:port 3000, :join? false}))))

(defn stop-server []
  (when @server
    (.stop @server)
    (reset! server nil)))

(defn restart-server []
  (when @server
    (stop-server)
    (start-server)))

