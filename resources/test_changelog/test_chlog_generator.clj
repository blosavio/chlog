(ns test-chlog-generator
  "CIDER eval buffer C-c C-k generates a 'changelog.md' in the project's top
  level directory and 'changelog.html' in the 'resources/' directory."
  {:no-doc true}
  (:require
   [hiccup2.core :as h2]
   [chlog.core :refer [generate-all-changelogs]]))


(def chlog-options (load-file "resources/test_changelog/test_chlog_options.edn"))


(generate-all-changelogs chlog-options)


(defn -main
  [& args]
  {:UUIDv4 #uuid "dacb9527-553b-464a-bbf1-53ea692c29ee"}
  (println "generated test changelog"))
