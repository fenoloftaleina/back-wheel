(ns back-wheel.main
  (:require
    [aleph.http :as http]
    [bidi.bidi :as bidi]
    [bidi.ring :refer [make-handler]]
    [com.stuartsierra.component :refer [system-map Lifecycle system-using using]]
    [yada.yada :refer [yada] :as yada]))

(defn api-index-resource []
  (yada/resource
   {:properties {}
    :produces {:media-type "application/json"}
    :methods
    {:get {:response (fn [ctx] {:asdf "fdsa"})}}}))

(defn api []
  ["" [["/api" (yada (api-index-resource))]]])

(defrecord ApiComponent []
  bidi/RouteProvider
  (routes [_] (api)))

(defn new-api-component []
  (map->ApiComponent {}))

(defn create-routes [api port]
  ["" [(bidi/routes api)
       [true (yada/yada nil)]]])

(defrecord ServerComponent [api port]
  Lifecycle
  (start [component]
    (let [routes (create-routes api port)]
      (assoc component
             :routes routes
             :server (http/start-server (make-handler routes) {:port port :raw-stream? true}))))
  (stop [component]
    (when-let [server (:server component)]
      (.close server))
    (dissoc component :server)))

(defn new-server-component [config]
  (map->ServerComponent config))

(defn new-system-map [config]
  (system-map
   :api (new-api-component)
   :server (new-server-component config)))

(defn new-dependency-map []
  {:api {}
   :server [:api]})

(defn new-app [config]
  (-> (new-system-map config)
      (system-using (new-dependency-map))))
