(ns chlog.core
  "Changelog aggregation, specification, validation, and webpage generation.

  Options map key-vals:

  * `:project-formatted-filename`
  * `:copyright-holder`
  * `:changelog-UUID`
  * `:changelog-html-directory`
  * `:changelog-html-filename`
  * `:changelog-markdown-directory`
  * `:changelog-markdown-filename`
  * `:changelog-entries-directory`
  * `:changelog-policies-section` (optional)"
  (:require
   [clojure.java.io :as io]
   [clojure.string :as str]
   [hiccup2.core :as h2]
   [hiccup.page :as page]
   [hiccup.element :as element]
   [hiccup.form :as form]
   [hiccup.util :as util]
   [readmoi.core :refer :all]))


(defn renamed-fns
  "Given a sequence `o2n` of 'old-fn-name'-to-'new-fn-name' maps, generate a
  hiccup/html unordered list of old to new."
  {:UUIDv4 #uuid "6f04837d-8314-4ef2-b729-14a1cb31b990"
   :no-doc true}
  [o2n]
  (let [sorted-oldnames (sort-by :old-function-name o2n)]
    (reduce #(conj %1 [:li [:code (name (:old-function-name %2))] " → " [:code (name (:new-function-name %2))]]) [:ul] sorted-oldnames)))


(defn moved-fns
  "Given a sequence `o2n` of `old-location`-to-`new-location` maps, generate a
  hiccup/html unordered list of old to new."
  {:UUIDv4 #uuid "5b5dd073-fd55-4bf7-91ae-b80657cb77c0"
   :no-doc true}
  [o2n]
  (let [sorted-fns (sort-by :fn-name o2n)]
    (reduce #(conj %1 [:li [:code (name (:fn-name %2))] " from " [:code (name (:old-location %2))] " to " [:code (name (:new-location %2))]]) [:ul] sorted-fns)))


(defn something-ed-fns
  "Given a sequence `changes` of changelog change maps, aggregate functions that
  have `change-type`, one of

  * `:added-functions`
  * `:altered-functions`
  * `:deprecated-functions`
  * `:function-arguments`
  * `:moved-functions`
  * `:removed-functions`
  * `:renamed-functions`

  See `chlog.changelog-specifications/change-kinds` for canonical listing."
  {:UUIDv4 #uuid "d9a2782a-c903-4338-93f2-78871d352cdd"
   :no-doc true}
  [changes change-type]
  (let [aggregation (reduce #(clojure.set/union %1 (set (change-type %2))) #{} changes)]
    (case change-type
      :renamed-functions [(renamed-fns aggregation)]
      :moved-functions [(moved-fns aggregation)]
      (->> aggregation
           vec
           sort
           (map #(vector :code (str %)))
           (interpose ", ")))))


(defn change-details
  "Given a sequence of `changes`, return a hiccup/html unordered list that lists
  the changes."
  {:UUIDv4 #uuid "5ed2bc16-9d57-4a88-acb4-ee5dae218110"
   :no-doc true}
  [changes]
  (let [grouped-changes (group-by #(:breaking? %) changes)
        breaking-changes (grouped-changes true)
        non-breaking-changes (concat (grouped-changes false)
                                     (grouped-changes nil))
        issue-reference #(if (:reference %) [:a {:href (:url (:reference %))} (:source (:reference %))] nil)
        issue-reference-seperator #(if (:reference %) ": " nil)]
    [:div
     [:h4 "Breaking changes"]
     (into [:ul] (map (fn [v] [:li [:div (issue-reference v) (issue-reference-seperator v) (str (:description v))]])) breaking-changes)
     [:h4 "Non-breaking changes"]
     (into [:ul] (map (fn [v] [:li [:div (issue-reference v) (issue-reference-seperator v) (str (:description v))]])) non-breaking-changes)]))


(defn generate-version-section
  "Given a map `m` that contains data on a single changelog version, generate
  hiccup/html for a section that displays that info."
  {:UUIDv4 #uuid "6d232a01-4cc1-4b91-8b63-7a5da9a96cb3"
   :no-doc true}
  [m]
  (let [changed-function-div (fn [label change-type] (let [something-ized-fn (something-ed-fns (m :changes) change-type)]
                                                       (if (empty? something-ized-fn)
                                                         nil
                                                         (into [:div [:em (str label " functions: ")]] something-ized-fn))))]
    [:section
     [(keyword (str "h3#v" (:version m))) (str "version " (:version m))]
     [:p
      (str (:year (:date m)) " "
           (:month (:date m)) " "
           (:day (:date m))) [:br]
      (str (:name (:responsible m)) " (" (:email (:responsible m)) ")") [:br]
      [:em "Description: "] (str (:comment m)) [:br]
      [:em "Project status: "] [:a {:href "https://github.com/metosin/open-source/blob/main/project-status.md"} (name (:project-status m))] [:br]
      [:em "Urgency: "] (name (:urgency m)) [:br]
      [:em "Breaking: "] (if (:breaking? m) "yes" "no")]
     [:p
      (changed-function-div "added" :added-functions)
      (changed-function-div "altered" :altered-functions)
      (changed-function-div "deprecated" :deprecated-functions)
      (let [possible-moves (something-ed-fns (m :changes) :moved-functions)]
        (if (= [[:ul]]  possible-moves)
          nil
          (into [:div [:em "moved functions: "]] possible-moves)))
      (let [possible-renames (something-ed-fns (m :changes) :renamed-functions)]
        (if (= [[:ul]] possible-renames)
          nil
          (into [:div [:em "renamed functions: "]] possible-renames)))
      (changed-function-div "removed" :removed-functions)]
     (change-details (m :changes))
     [:hr]]))


(defn changelog-md-footer
  "Given an options map `opt`, retruns a page footer with a copyright notice and a
  compiled on date, plus the changelog UUID."
  {:UUIDv4 #uuid "4c8f92ab-e956-44f5-9a60-73bc4c2baee2"
   :no-doc true}
  [opt]
  [:p#page-footer
   (copyright (opt :copyright-holder))
   [:br]
   "Compiled by " [:a {:href "https://github.com/blosavio/chlog"} "Chlog"] " on " (short-date) "."
   [:span#uuid [:br] (opt :changelog-UUID)]])


(defn generate-chlog-html
  "foobar"
  {:UUIDv4 #uuid "0184510a-85ae-49eb-9524-bae20cd26962"
   :no-doc true}
  [opt changelog-data]
  (spit (str (opt :changelog-html-directory) (opt :changelog-html-filename))
        (page-template
         (str (opt :project-formatted-name) " library changelog")
         (opt :changelog-UUID)
         (conj [:body
                [:h1 (str (opt :project-formatted-name) " library changelog")]
                (opt :changelog-policies-section)]
               (into (map #(generate-version-section %) (reverse changelog-data))))
         (opt :copyright-holder)
         [:a {:href "https://github.com/blosavio/chlog"} "Chlog"])))


(defn generate-chlog-markdown
  "foobar"
  {:UUIDv4 #uuid "2db604c4-7755-491d-a59d-2d2f49b1a60c"
   :no-doc true}
  [opt changelog-data]
  (spit (str (opt :changelog-markdown-directory) (opt :changelog-markdown-filename))
        (h2/html
         (vec (-> [:body
                   [:h1 (str (opt :project-formatted-name) " library changelog")]
                   (opt :changelog-policies-section)]
                  (into (map #(generate-version-section %) (reverse changelog-data)))
                  (conj (changelog-md-footer opt)))))))


(defn get-filenames
  "Given options hashmap `opt`, returns a sequence of filepath strings of all
  changelos in `:changelog-entries-directory`."
  {:UUIDv4 #uuid "61d88ebf-3896-44b4-8c29-97c0a0e9919a"
   :no-doc true}
  [opt]
  (let [prefix (str (opt :changelog-entries-directory)
                    "changelog_v")
        suffix ".edn"]
    (->> (opt :changelog-entries-directory)
         io/file
         file-seq
         (map #(.toString %))
         (filter #(and (str/starts-with? % prefix)
                       (str/ends-with? % suffix))))))


(defn load-changelogs
  "Given an options hashmap `opt`, returns a vector containing the changelogs,
  each element a hashmap."
  {:UUIDv4 #uuid "f182cd59-c234-4eb4-b30f-c1b3df95bdd3"
   :no-doc true}
  [opt]
  (sort-by :version (map #(load-file %) (get-filenames opt))))


(load "chlog_defaults")


(defn generate-all-changelogs
  "Given Chlog options `opt`, write-to-file html and markdown changeloges.

  See project documentation for details on the structure of the options map.

  Changelog data will be read from `resources/changelog_entries/changelog.edn`
  unless superseded by `:changelog-entries-directory` or
  `:changelog-data-file` values in the options map.

  Defaults supplied by `src/chlog_defaults.edn`"
  {:UUIDv4 #uuid "cb525541-2d98-4003-9ab7-777661933cf6"}
  [opt]
  (let [options-n-defaults (merge chlog-defaults opt)
        changelog-data (load-changelogs options-n-defaults)]
    (do (generate-chlog-html options-n-defaults changelog-data)
        (generate-chlog-markdown options-n-defaults changelog-data)
        (if (options-n-defaults :tidy-html?)
          (do (tidy-html-document (str (options-n-defaults :changelog-html-directory)
                                       (options-n-defaults :changelog-html-filename)))
              (tidy-html-body (str (options-n-defaults :changelog-markdown-directory)
                                   (options-n-defaults :changelog-markdown-filename))))))))


(defn -main
  "Generate an html and a markdown changelog, sourcing options from file
  `options-filename` if supplied, otherwise `resources/chlog_options.edn`.

  Examples:
  ```clojure
  ;; generate changelog using options from 'resources/chlog_options.edn'
  (-main)

  ;; generate changelog using options from 'other_directory/custom_changelog_opt.edn'
  (-main \"other_directory/custom_changelog_opt.edn\")
  ```

  From the command line, options file defaults to `resources/chlog_options.edn`:
  ```bash
  $ lein run -m chlog.core
  ```

  From the command line, explicit options file `other_directory/custom_changelog_opt.edn`:
  ```bash
  $ lein run -m chlog.core other_directory/custom_changelog_opt.edn
  ```"
  [& options-filename]
  {:UUIDv4 #uuid "5278c15f-6986-4868-b38a-e47234f19669"}
  (let [opt-fname (or (first options-filename)
                      "resources/chlog_options.edn")
        opt (load-file opt-fname)]
    (do
      (generate-all-changelogs opt)
      (if (not *repl*)
        (System/exit 0)))))

