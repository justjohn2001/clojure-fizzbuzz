(ns fizzbuzz.core
  (:gen-class))

(defn make-fb
  [[k s]]
  (map (fn [[n fs :as p]]
         (if (zero? (mod n k))
           (vector n ((fnil str "") fs s))
           p))))

(defn fb-transducer
  [m]
  (comp (reduce #(comp %1 (make-fb %2))
                (map #(vector % nil))
                (sort-by first m))
        (map (fn [[n s]] (or s n)))))

(defn -main
  [& args]
  (println
   (clojure.string/join ", "
                        (sequence (fb-transducer {3 "fizz" 5 "buzz" 7 "baz"})
                                  (range 1 20)))))
