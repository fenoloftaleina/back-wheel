(ns back-wheel.services.random-secret)

(defn call [context]
  {:secret "candy"}) ;; Guaranteed to be random.
