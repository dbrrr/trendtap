(ns trend.rest-api.server
  (:require [trend.rest-api.core :as core]
            [com.stuartsierra.component :as component]
            [org.httpkit.server :as http-kit]
            [trend.database.interface :as db]))

(defonce ^:private server-ref (atom nil))

(defn start!
  [port]
  (if-let [_server @server-ref]
    (println "server already running!")
    (do
      (println "Starting server on port: " port)
      #_(reset! system/system (component/start (db/create)))
      (reset! server-ref
              (http-kit/run-server core/handler
                                   {:port port
                                    :join? false})))))

(defn stop! []
  (if-let [server @server-ref]
    (do
      (println "Stopping server")
      #_(component/stop @system/system)
      (server)
      (reset! server-ref nil))
    (println "No server")))

(start! 6003)
(stop!)

(defn -main [& _args]
  (start! (Integer/valueOf
           (or (System/getenv "port")
               "6003")
           10)))
