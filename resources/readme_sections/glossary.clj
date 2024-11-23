[:section#glossary
 [:h2 "Glossary"]

 [:dl
  [:dt#changelog "changelog"]
  [:dd [:p "A sequence of notable versions of project. Concretely, a tail-appended vector containing one or more " [:a {:href "#version"} "version"] " hash-maps. Typically resides in an " ".edn" " file where Chlog can locate it. The changelog contains information about the software that helps people understand what will happend when switching from one version of the software to another."]]]
 
 [:dl
  [:dt#change "change"]
  [:dd [:p "Within a version, a report of some facet of the software that is different. Concretely, a hash-map containing information about the change kind (e.g., added function, deprecated function, implementation change, performance improvement, bug-fix, etc.), person responsible, namespaced function symbols involved, and an issue referenece (e.g., GitHub issue number, JIRA ticket, etc.)."]]

  [:dt#version "version"]
  [:dd [:p "A notable release of software, labeled by a version number. Concretely, a hash-map containing information about the release date, the person responsible, the urgency of switching, breakage, free-form description, and a listing of zero or more detailed " [:a {:href "#change"} "changes"] "."]]]]