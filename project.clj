(defproject com.sagevisuals/chlog "4"
  :description "A Clojure library for maintaining an edn changelog."
  :url "https://github.com/blosavio/chlog"
  :license {:name "MIT License"
            :url "https://opensource.org/license/mit"
            :distribution :repo}
  :dependencies [[org.clojure/clojure "1.12.0"]
                 [com.sagevisuals/readmoi "5"]]
  :repl-options {:init-ns chlog.core}
  :plugins []
  :profiles {:dev {:dependencies [[org.clojure/test.check "1.1.1"]
                                  [hiccup "2.0.0-RC3"]
                                  [zprint "1.2.9"]
                                  [com.sagevisuals/speculoos "6"]]
                   :plugins [[dev.weavejester/lein-cljfmt "0.12.0"]
                             [lein-codox "0.10.8"]]}
             :repl {}}
  :aliases {"readmoi" ["run" "-m" "readmoi-generator"]
            "chlog" ["run" "-m" "chlog-generator"]}
  :codox {:metadata {:doc/format :markdown}
          :namespaces [#"^chlog\.(?!scratch)"]
          :target-path "doc"
          :output-path "doc"
          :doc-files []
          :source-uri "https://github.com/blosavio/chlog/blob/main/{filepath}#L{line}"
          :html {:transforms [[:div.sidebar.primary] [:append [:ul.index-link [:li.depth-1 [:a {:href "https://github.com/blosavio/chlog"} "Project home"]]]]]}
          :project {:name "Chlog" :version "version 4"}}
  :scm {:name "git" :url "https://github.com/blosavio/chlog"})

