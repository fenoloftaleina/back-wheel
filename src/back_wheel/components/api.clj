(ns back-wheel.components.api
  (:require
    [bidi.bidi :as bidi]
    [yada.yada :refer [yada] :as yada]))

(defn- api-index-resource []
  (yada/resource
    {:properties {}
     :produces {:media-type "application/json"}
     :methods
     {:get {:response (fn [ctx] {:asdf "fdsa"})}}}))

(defn- api []
  ["" [["/api" (yada (api-index-resource))]]])

(defrecord ApiComponent []
  bidi/RouteProvider
  (routes [_] (api)))

(defn api-component []
  (map->ApiComponent {}))
