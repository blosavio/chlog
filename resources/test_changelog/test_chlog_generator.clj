(ns test-chlog-generator
  "CIDER eval buffer C-c C-k generates a 'changelog.md' in the project's top
  level directory and 'changelog.html' in the 'resources/' directory."
  {:no-doc true}
  (:require
   [chlog.core :refer [-main]]))


(-main "resources/test_changelog/test_chlog_options.edn")

