(ns back-wheel.services.bike-stations
  (:require [clj-http.client :as client]))

(defn call [context]
  {:bike-stations [{:name "Station 1"}]})
