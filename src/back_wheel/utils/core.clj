(ns back-wheel.utils.core
  (:require [yada.yada :refer [yada] :as yada]))

(def json-resource-template
  {:produces {:media-type "application/json"}})

(defn json-endpoint
  ([methods-map]
   (json-endpoint {} methods-map))
  ([resource-details methods-map]
   (yada
     (yada/resource
       (merge
         json-resource-template
         resource-details
         {:methods methods-map})))))

(defn with-get-method [handler]
  {:get {:response handler}})
