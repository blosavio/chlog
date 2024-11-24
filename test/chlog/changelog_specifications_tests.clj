(ns chlog.changelog-specifications-tests
  "Tests for the changelog predicates."
  (:require
   [chlog.changelog-specifications :refer :all]
   [clojure.test :refer [are
                         deftest
                         is
                         run-tests
                         testing]]))


(deftest year?-tests
  (are [x] (true? x)
    (year? 2000)
    (year? 3000))
  (are [x] (false? x)
    (year? -1)
    (year? "abc")))


(deftest day?-tests
  (are [x] (true? x)
    (day? 1)
    (day? 31))
  (are [x] (false? x)
    (day? 0)
    (day? 32)))


(deftest ticket?-tests
  (are [x] (true? x)
    (ticket? "")
    (ticket? "abc")
    (ticket? #uuid "dd60a7cf-146b-4ee0-bc32-311442b5a278"))
  (are [x] (false? x)
    (ticket? 'foo)
    (ticket? :foo)))


(defn url? [s] (and (string? s) (boolean (re-find (get reference-spec :url) s))))


(deftest ref-url-tests
  (are [x] (true? x)
    (url? "https://foo")
    (url? "https://example.com"))
  (are [x] (false? x)
    (url? "")
    (url? "http:")
    (url? "https://")))


(deftest breaking?-tests
  (are [x] (true? x)
    (breaking? true)
    (breaking? false)
    (breaking? nil))
  (are [x] (false? x)
    (breaking? :true)
    (breaking? "true")))


(defn email? [s] (and (string? s) (boolean (re-find (get person-spec :email) s))))


(deftest person-email-tests
  (are [x] (true? x)
    (email? "foo@example.com")
    (email? "a@b"))
  (are [x] (false? x)
    (email? :foo)
    (email? "foo_at_example.com")
    (email? "@example.com")
    (email? "foo@")))


(deftest version?-tests
  (are [x] (true? x)
    (version? 0)
    (version? 1)
    (version? 99))
  (are [x] (false? x)
    (version? "0")
    (version? :0)
    (version? 1.0)))


(def req-keys-1? (contains-required-keys? #{:a :b :c}))


(deftest contains-required-keys?-tests
  (are [x] (true? x)
    (req-keys-1? {:a 1 :b 2 :c 3})
    (req-keys-1? {:a 1 :b 2 :c 3 :d 4}))
  (are [x] (false? x)
    (req-keys-1? {})
    (req-keys-1? {:a 1 :b 2})))


(deftest properly-incrementing-versions?-tests
  (are [x] (true? x)
    (properly-incrementing-versions? [])
    (properly-incrementing-versions? [{:version 0}])
    (properly-incrementing-versions? [{:version 0}
                                      {:version 1}
                                      {:version 2}]))
  (are [x] (false? x)
    (properly-incrementing-versions? [{:version 0}
                                      {:version 0}])
    (properly-incrementing-versions? [{:version 0}
                                      {:version 1}
                                      {:version 99}])))


(run-tests)