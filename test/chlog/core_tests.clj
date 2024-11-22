(ns chlog.core-tests
  (:require
   [clojure.test :refer [deftest is are testing run-tests]]
   [chlog.core :refer :all]))


(deftest rename-fns-tests
  (are [x y] (= x y)
    (renamed-fns {})
    [:ul]

    (renamed-fns [{:old-function-name 'foo
                   :new-function-name 'bar}])
    [:ul
     [:li [:code "foo"] " → " [:code "bar"]]]

    (renamed-fns [{:old-function-name 'foo
                   :new-function-name 'bar}
                  {:old-function-name 'baz
                   :new-function-name 'qux}])
    [:ul
     [:li [:code "baz"] " → " [:code "qux"]]
     [:li [:code "foo"] " → " [:code "bar"]]]))


(deftest something-ed-fns-tests
  (are [x] (empty? x)
    (something-ed-fns [] :altered-functions)

    (something-ed-fns [{:moved-functions ['+]}] :removed-functions))
  (are [x y] (= x y)

    (something-ed-fns [{:removed-functions ['+]}] :removed-functions)
    [[:code "+"]]

    (something-ed-fns [{:altered-functions ['+ '- '*]}] :altered-functions)
    [[:code "*"] ", " [:code "+"] ", " [:code "-"]]))


(deftest change-details-tests
  (are [x y] (= x y)
    (change-details [])
    [:div
     [:h4 "Breaking changes"]
     [:ul]
     [:h4 "Non-breaking changes"]
     [:ul]]

    (change-details [{:breaking? true
                      :description "This is an example breaking change."}
                     {:breaking? false
                      :description "This in an example non-breaking change."}])
    [:div
     [:h4 "Breaking changes"]
     [:ul
      [:li [:div nil nil "This is an example breaking change."]]]
     [:h4 "Non-breaking changes"]
     [:ul [:li [:div nil nil "This in an example non-breaking change."]]]]))


(deftest generate-version-section-test
  (are [x y] (= x y)
    (generate-version-section {:version 99
                               :date {:year 1999
                                      :month "Month"
                                      :day 0}
                               :responsible {:name "Foo Bar"
                                             :email "FooBar@example.com"}
                               :comment "Example comment"
                               :project-status :active
                               :urgency :low
                               :breaking? true
                               :changes [{:added-functions ['+]
                                          :renamed-functions [{:old-function-name 'foo
                                                               :new-function-name 'bar}]
                                          :moved-functions ['*]
                                          :removed-functions ['-]
                                          :altered-functions ['/]
                                          :breaking? true
                                          :description "This is an example of a breaking change."}]})
    [:section
     [:h3 "version 99"]
     [:p "1999 Month 0" [:br]
      "Foo Bar (FooBar@example.com)" [:br]
      [:em "Description: "] "Example comment" [:br]
      [:em "Project status: "] [:a {:href "https://github.com/metosin/open-source/blob/main/project-status.md"} "active"] [:br]
      [:em "Urgency: "] "low" [:br]
      [:em "Breaking: "] "yes"]
     [:p
      [:div [:em "added functions: "] [:code "+"]]
      [:div [:em "renamed functions: "]
       [:ul
        [:li [:code "foo"] " → " [:code "bar"]]]]
      [:div [:em "moved functions: "] [:code "*"]]
      [:div [:em "removed functions: "] [:code "-"]]
      [:div [:em "altered functions: "] [:code "/"]]]
     [:div [:h4 "Breaking changes"]
      [:ul
       [:li [:div nil nil "This is an example of a breaking change."]]]
      [:h4 "Non-breaking changes"] [:ul]]
     [:hr]]))


(deftest changelog-md-footer-tests
  (are [x y] (= x y)
    (changelog-md-footer {:copyright-holder "Foo Bar"
                          :changelog-UUID "Example Changelog UUID"})
    (assoc [:p#page-footer
            (readmoi.core/copyright "Foo Bar") [:br]
            "Compiled by " [:a {:href "https://github.com/blosavio/chlog"} "Chlog"] " on " "<auto-insert current date>" "."
            [:span#uuid [:br]
             "Example Changelog UUID"]]
           6
           (readmoi.core/short-date))))


(run-tests)