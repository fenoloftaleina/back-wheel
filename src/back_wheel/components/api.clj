(ns back-wheel.components.api
  (:require
    [bidi.bidi :as bidi]
    [back-wheel.utils.core :refer [json-endpoint with-get-method secret-secret]]
    [back-wheel.services bike-stations random-secret]))

(defn- api []
  [""
   [["/bike-stations"
     (-> back-wheel.services.bike-stations/call
         (with-get-method)
         (json-endpoint))]

    ["/random-secret"
     (-> back-wheel.services.random-secret/call
         (with-get-method)
         (json-endpoint secret-secret))
     ]]])

(defrecord ApiComponent []
  bidi/RouteProvider
  (routes [_] (api)))

(defn api-component []
  (map->ApiComponent {}))
