[:section#glossary

 [:h2 "Glossary"]

 [:dl
  [:dt#breaking "breaking"]
  [:dd
   [:p "Any change that is "
    [:strong "not"]
    " "
    [:a {:href "#non-breaking"} [:em "non-breaking"]]
    "."]]]

 [:dl
  [:dt#changelog "changelog"]
  [:dd
   [:p "A sequence of notable versions of project. Concretely, a tail-appended
 sequence containing one or more "
    [:a {:href "#version"} "version"]
    " hashmaps. Typically organized into multiple "
    [:code "edn"]
    " files located in a known directory where Chlog can locate it. The
 changelog contains information about the software that helps people understand
 what will happen when switching from one version of the software to
 another."]]]

 [:dl
  [:dt#change "change"]
  [:dd
   [:p "Within a version, a report of some facet of the software that is
 different. Concretely, a hashmap containing information about the change kind
 (e.g., added function, deprecated function, implementation change, performance
 improvement, bug-fix, etc.), person responsible, namespaced function symbols
 involved, and an issue reference (e.g., GitHub issue number, JIRA ticket,
 etc.)."]]]

 [:dl
  [:dt#non-breaking "non-breaking"]
  [:dd
   [:p "A change that can be installed with zero other adjustments and the
 consuming software will work exactly as before. Otherwise, the change is "
    [:a {:href "#breaking"} [:em "breaking"]]
    "."]]]

 [:dl
  [:dt#version "version"]
  [:dd
   [:p "A notable release of software, labeled by a version number. Concretely, a
 hashmap containing information about the release date, the person responsible,
 the urgency of switching, breakage, free-form description, and a listing of
 zero or more detailed "
    [:a {:href "#change"} "changes"]
    "."]]]]

