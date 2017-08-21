(ns back-wheel.main
  (:require
    [back-wheel.components api server]
    [com.stuartsierra.component :refer [system-map using]]))

(defn app [config]
  (system-map
   :api (back-wheel.components.api/api-component)
   :server (using
             (back-wheel.components.server/server-component config)
             [:api])))
