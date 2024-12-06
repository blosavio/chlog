(def ^{:no-doc true}
  chlog-defaults-docstring
  "A hash-map residing in `chlog_defaults.clj` that supplies the default values
  for the following options keys:

  * `:changelog-html-directory`
  * `:changelog-html-filename`
  * `:changelog-markdown-directory`
  * `:changelog-markdown-filename`
  * `:changelog-entries-directory`
  * `:changelog-data-file`
  * `:changelog-policies-section`
  * `:tidy-html?`

  Override default values by associating new values into the Chlog _options_
  hash-map. See [[generate-all-changelogs]].")


(def ^{:doc chlog-defaults-docstring
       :UUIDv4 #uuid "d0dae3d9-ee53-40a4-ab86-2ee31fc4cd6b"}
  chlog-defaults {:changelog-html-directory "doc/"
                  :changelog-html-filename "changelog.html"

                  :changelog-markdown-directory ""
                  :changelog-markdown-filename "changelog.md"

                  :changelog-entries-directory "resources/changelog_entries/"
                  :changelog-data-file "changelog.edn"

                  :changelog-policies-section [:a {:href "https://github.com/blosavio/chlog"} "changelog info"]

                  :tidy-html? false})