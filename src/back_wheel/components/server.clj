(ns back-wheel.components.server
  (:require
    [aleph.http :as http]
    [bidi.bidi :as bidi]
    [bidi.ring :refer [make-handler]]
    [com.stuartsierra.component :refer [using Lifecycle]]
    [yada.yada :refer [yada] :as yada]))

(defn create-routes [api port]
  ["" [(bidi/routes api)
       [true (yada nil)]]])

(defrecord ServerComponent [api port]
  Lifecycle

  (start [component]
    (let [routes (create-routes api port)]
      (assoc component
             :routes routes
             :server
             (http/start-server
               (make-handler routes)
               {:port port :raw-stream? true}))))

  (stop [component]
    (when-let [server (:server component)]
      (.close server))
    (dissoc component :server)))

(defn server-component [config]
  (map->ServerComponent config))
