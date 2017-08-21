(ns back-wheel.services.bike-stations
  (:require [clj-http.client :as client]))

(def bike-stations-endpoint "https://api.tfl.gov.uk/BikePoint")
(def leyton-coordinates {:lat 51.5619474 :lon -0.0175164})
(def amount-of-closest-stations 5)
(def bikes-count-key "NbBikes")

(def data (client/get bike-stations-endpoint {:as :json}))
(defn- fetch-bike-stations []
  (:body data))

{:bikes (:value (last (filter (fn [property] (= (property :key) "NbBikes"))
                                   (:additionalProperties (first (:body data))))))}
(:commonName (first (:body data)))

(:additionalProperties (first (:body data)))

(def compute-distance-from-leyton
  (partial
    map
    (fn [bike-station]
      (assoc bike-station :distance
             (Math/sqrt (+
                         (Math/pow (- (:lat leyton-coordinates) (:lat bike-station)) 2)
                         (Math/pow (- (:lon leyton-coordinates) (:lon bike-station)) 2)))))))

(defn- bikes-count-property [bike-station]
  (Integer/parseInt
    (:value
      (last
        (filter
          #(= (:key %) bikes-count-key)
          (:additionalProperties bike-station))))))

(def present
    (partial
      map
      (fn [bike-station]
        {:name (:commonName bike-station)
         :bikes-count (bikes-count-property bike-station)})))

(defn call [context]
  {:bike-stations
   (->>
     (fetch-bike-stations)
     (compute-distance-from-leyton)
     (sort-by :distance)
     (take amount-of-closest-stations)
     (present))})
