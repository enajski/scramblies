(ns pl.enajski.scramblies.core)

(defn scramble
  "I finally settled on this solution.
   While it does a full scan of str1
   the performance is acceptable for
   even very long str1."
  [str1 str2]
  (loop [f1   (frequencies str1)
         str2 str2]
    (if-not (seq str2)
      true
      (if (zero? (get f1 (first str2) 0))
        false
        (recur (update f1 (first str2) dec) (rest str2))))))
