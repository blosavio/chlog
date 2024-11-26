(ns chlog.changelog-validations
  "Functions to validate changelogs. Specifications referred from `chlog.changelog-specifications`.

  See also Speculoos library [documentation](https://github.com/blosavio/speculoos)."
  (:require
   [chlog.changelog-specifications :refer :all]
   [speculoos.core :refer [only-invalid
                           valid-collections?
                           valid-scalars?
                           valid?
                           validate
                           validate-collections
                           validate-scalars]]
   [speculoos.utility :refer [collections-without-predicates
                              scalars-without-predicates
                              thoroughly-valid?
                              thoroughly-valid-scalars?]]))


(comment
  (def changelog-pathfilename "resources/changelog_entries/changelog.edn")
  (def changelog-data (load-file changelog-pathfilename))
  )


;;;; scalar validations


(defn validate-one-version-scalars
  "Given index changelog file `cf` and index `idx` to a version, validates that
  version's scalars.

  Note: `idx` is the index into the sequence of versions, but not necessarily
  the version number.

  See also [[validate-one-version-collections]]."
  {:UUIDv4 #uuid "0b33a25d-d06c-48c0-90ef-9bca32baa2bc"}
  [cf idx]
  (validate-scalars (get (load-file cf) idx) version-scalar-spec))


(comment
  (validate-one-version-scalars changelog-pathfilename 0)
  (only-invalid (validate-one-version-collection changelog-pathfilename 0))
  )


(defn validate-changelog-scalars
  "Given changelog file `cf`, returns only invalid scalar validation results. If
  all scalars are valid, returns `[]`, i.e., an empty sequential.

  Example:
  ```clojure
  (validate-changelog-scalars changelog-pathfilename)
  ```

  If the `:datum` entries are too verbose, use [[elide-datums]] with this
  pattern:
  ```clojure
  (elide-datums (validate-changelog-scalars changelog-pathfilename))
  ```"
  {:UUIDv4 #uuid "cb3082d7-0f7e-4a47-ad43-5555e66d7bf6"}
  [cf]
  (only-invalid (validate-scalars (load-file cf) changelog-scalar-spec)))


(defn elide-datums
  "Replace `:datum` validation entries `v` with '...' so that the viewing the
  results are clearer."
  {:UUIDv4 #uuid "e9ffa7a4-c375-434b-a7f1-2b6d9685e2d0"}
  [v]
  (vec (map #(assoc % :datum '...) v)))


(comment
  ;; Note: This evaluation may take a 10s of seconds with CIDER/nREPL
  (elide-datums (validate-changelog-scalars changelog-pathfilename))
  )


(defn all-changelog-scalars-have-predicates?
  "Given changelog file `cf`, returns `true` if all scalars in the changelog
  data are paired with a predicate.

  Note: Bug inherited from `scalars-without-predicates` results in this function
  not properly working with specifications composed with non-terminating
  sequences."
  {:UUIDv4 #uuid "8dc5c146-1cc9-4dce-9f42-2843b85af6bc"
   :no-doc true}
  [cf]
  (empty? (scalars-without-predicates (load-file cf) changelog-scalar-spec)))


(comment
  (all-changelog-scalars-have-predicates? changelog-pathfilename)
  )



;;;; collection validations

(defn validate-one-version-collections
  "Given changelog file `cf` and index `idx` to a version, validates that
  version's collections.

  Note: `idx` is the index into the sequence of versions, but not necessarily
  the version number.

  See also [[validate-one-version-scalars]]."
  {:UUIDv4 #uuid "a2487774-7d7c-4eaa-b699-6541e7c82b98"}
  [cf idx]
  (validate-collections (get (load-file cf) idx) version-coll-spec))


(comment
  (validate-one-version-collections changelog-pathfilename 0)
  (only-invalid (validate-one-version-collections changelog-pathfilename 0))
  )


(defn all-changelog-collections-have-predicates?
  "Given changelog file `cf`, returns `true` if all collections in the changelog
  data are paired with a predicate.

  Note: You probably don't need this utility. Don't stress about every
  collection having a predicate, except for very simple situations."
  {:UUIDv4 #uuid "8f42d121-6cde-4b35-a0c0-f67b2d014669"}
  [cf]
  (empty? (collections-without-predicates (load-file cf) changelog-coll-spec)))


(comment
  (all-changelog-collections-have-predicates? changelog-pathfilename)
  )


(defn validate-changelog-collections
  "Given changelog file `cf`, returns invalid collections.

  Example:
  ```clojure
  (validate-changelog-collections changelog-pathfilename)
  ```

  For large data, use [[elide-datums]] like this:
  ```clojure
  (elide-datums (validate-changelog-collections changelog-pathfilename))
  ```"
  {:UUIDv4 #uuid "d559f4f5-a292-490a-ae1c-bf5dd96f953b"}
  [cf]
  (only-invalid (validate-collections (load-file cf) changelog-coll-spec)))


(comment
  (validate-collections (load-file changelog-pathfilename) changelog-coll-spec)
  (validate-changelog-collections changelog-pathfilename)
  )



;;;; Combo validations


(defn valid-changelog?
  "Given changelog file `cf`, returns `true` if all scalars and all collections
  satisfy their corresponding predicates. Otherwise, returns `false`.

  See [[validate-changelog]] for a more details validation report.

  Example:
  ```clojure
  (valid-changelog? changelog-pathfilename)
  ```"
  {:UUIDv4 #uuid "8683d12e-fcd6-4473-a7c3-325e7b3dd1bb"}
  [cf]
  (valid? (load-file cf)
          changelog-scalar-spec
          changelog-coll-spec))


(defn validate-changelog
  "Given changelog file `cf`, returns a detailed validation report for all
  scalars and all collections.

  Example:
  ```clojure
  (validate-changelog changelog-pathfilename)
  ```

  Use the following pattern to focus on invalid elements:
  ```clojure
  (only-invalid (validate-changelog ...))
  ```

  See [[valid-changelog?]] for a terse validation summary."
  {:UUIDv4 #uuid "f9c27282-f618-4209-aab2-8930c3c13f1d"}
  [cf]
  (only-invalid (validate (load-file cf)
                          changelog-scalar-spec
                          changelog-coll-spec)))


(comment
  (valid-changelog? changelog-pathfilename)

  (only-invalid (validate-changelog changelog-pathfilename))

  (only-invalid (validate (load-file changelog-pathfilename)
                          changelog-scalar-spec
                          changelog-coll-spec))
  )
