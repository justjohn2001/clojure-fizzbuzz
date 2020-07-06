(ns fizzbuzz.core
  (:require [clojure.string :as string]))

(def rules (sorted-map 3 "fizz" 5 "buzz" #_#_7 "bazz"))

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

(def fizz-buzz (fb-fn rules))

(defn lazy-fb
  ([] (lazy-fb 1))
  ([n] (cons (fizz-buzz n)
             (lazy-seq (lazy-fb (inc n))))))

(def fb-transducer (map fizz-buzz))

(defn -main
  [& args]
  (println (string/join ", "
                        (sequence fb-transducer
                                  (range 1 21))))
  (println (string/join ", "
                        (take 20 (lazy-fb)))))
