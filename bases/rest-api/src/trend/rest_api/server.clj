(ns trend.rest-api.server
  (:require
   [org.httpkit.server :as http-kit]
   [trend.rest-api.core :as core]
   [com.stuartsierra.component :as component]
   [trend.database.interface :as db]
   [trend.rest-api.system :as system]
   [trend.tenant.interface :as tenant]
   [trend.util.interface :as util]))

(defonce ^:private server-ref (atom nil))

(defn start!
  [port]
  (if-let [_server @server-ref]
    (println "server already running!")
    (do
      (println "Starting server on port: " port)
      (let [db (db/start)
            tenant (or (tenant/find-first! {:db db})
                       (tenant/create! {:db db} "My tenant"))]
        (reset! system/system {:db db
                               :tenant-id (util/id tenant)}))
      (reset! server-ref
              (http-kit/run-server core/handler
                                   {:port port
                                    :join? false})))))

(defn stop! []
  (if-let [server @server-ref]
    (do
      (println "Stopping server")
      (component/stop (:db @system/system))
      (server)
      (reset! server-ref nil))
    (println "No server")))

#_(db/truncate-all (:db @system/system))

(start! 6003)
(stop!)

(defn -main [& _args]
  (start! (Integer/valueOf
           (or (System/getenv "port")
               "6003")
           10)))
