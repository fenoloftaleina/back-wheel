(ns back-wheel.utils.core
  (:require [yada.yada :refer [yada] :as yada]))

(def json-resource-template
  {:produces {:media-type "application/json"}
   :access-control {}})

(defn- cors [resource-map]
  (assoc-in resource-map [:access-control :allow-origin] "*"))

(defn json-endpoint
  ([methods-map]
   (json-endpoint methods-map {}))
  ([methods-map resource-map]
   (->
     (merge
       json-resource-template
       resource-map
       {:methods methods-map})
     (cors)
     (yada/resource))))

(defn with-get-method [handler]
  {:get {:response handler}})

(def proper-role :authorized-because-authenticated)

(def secret-secret
  {:access-control
   {:scheme "Basic"
    :verify (fn [[user pass]]
              ;; Let anyone who's brave enough to try any password in.
              (when (not (empty? pass))
                {:roles #{proper-role}}))
    :authorization {:methods {:get proper-role}}}})
