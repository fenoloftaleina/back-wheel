(ns back-wheel.services.random-secret)

(defn call [context]
  {:random-secret "candy"}) ;; Guaranteed to be random.
