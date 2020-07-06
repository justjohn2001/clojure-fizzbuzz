(ns fizzbuzz.core
  (:require [clojure.string :as string]))

(defn make-fb
  [[k s]]
  (map (fn [[n fs :as p]]
         (if (zero? (mod n k))
           (vector n (str fs s))
           p))))

(defn fb-transducer
  [m]
  (comp (reduce #(comp %1 (make-fb %2))
                (map #(vector % nil))
                (sort-by first m))
        (map (fn [[n s]] (or s n)))))

(defn fb-fn
  [m]
  (fn [n]
    (or (reduce (fn [v [k s]]
                  (if (zero? (mod n k))
                    (str v s)
                    v))
                nil
                m)
        n)))

(def fizz-buzz (fb-fn {3 "fizz" 5 "buzz" 7 "bazz"}))

(defn lazy-fb
  ([] (lazy-fb 1))
  ([n] (cons (fizz-buzz n)
             (lazy-seq (lazy-fb (inc n))))))

(defn -main
  [& args]
  (println (string/join ", "
                        (sequence (fb-transducer {3 "fizz" 5 "buzz" 7 "baz"})
                                  (range 1 20))))
  (println (string/join ", "
                        (take 20 (lazy-fb)))))
