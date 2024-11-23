[:section#intro
 [:h2 "Introduction"]

 [:p "The big question: " [:em "What will happen if we switch from this version to that version?"] ""]

 [:p "We ask too much of our software version numbers, and we should expect more from our changelogs. A " [:code "major.minor.patch"] " sequence of numbers simply doesn't have enough bandwidth to tell us how to make good decisions about switching versions. On the other hand, html/markdown changelogs could potentially convey that information, but require a person to read and interpret it."]

 [:p "Ideally, a changelog would contain a compact representation of what changed from one version to the next, and operational implications of those changes. We use functions " [:code "foo"] " and " [:code "bar"] ", but only function " [:code "baz"] " changed. Safe to switch. Or, we do use function " [:code "baz"] ", but the change is non-breaking, so, again, it's safe to switch. Or, maybe " [:code "baz"] " was changed in a way that does break the way we use it, but the changelog tells us why it changed and how to manage the switch." ]

 [:p "Changelogs are often presented as " [:a {:href "https://clojure.org/releases/devchangelog"} [:span.small-caps "html"]] " or " [:a {:href "https://codeberg.org/leiningen/leiningen/raw/branch/main/resources/leiningen/new/template/CHANGELOG.md"} "markdown"] " files, intended for people to read. But to convey the depth of information we're talking about requires a high-level of discipline by the authors to comprehensively write all that out. Mistakes and omissions are bound to happen with free-form text, not to mention irregularities, and in the end, a person has to synthesize a lot of bits to make a decision. html/markdown is ill-suited for this purpose."]

 [:p "Storing a changelog in Clojure data structures offers many potential benefits. The data can be written, stored, retrieved, and manipulated programmatically. It can be " [:a {:href "https://github.com/blosavio/speculoos"} "validated"] " for correctness and completeness. And we could write " [:a {:href "#possibilities"} "utilities"] " that answer detailed questions about what it would be like to switch from one version to another."]

 [:p "Chlog is a library that tests these ideas. It encompasses several parts. First, it promotes a set of principles, some of which we've already mentioned. Second, Chlog proposes a set of specifications for such a changelog. Third, Chlog offers an experimental implementation that maintains an " [:code ".edn"] " changelog, validates it, and generates easily-readable html and markdown webpages based upon the changelog data."]

 [:p [:a {:href "https://github.com/weavejester/hiccup"} "Hiccup"] " is a wonderful utility that consumes Clojure code and outputs " [:span.small-caps "html"] "."]

 [:p "The Chlog library generates " [:span.small-caps "html"] " and markdown changelog files from hiccup source."

  [:p "The resulting changelog is structured exactly as you see " [:a {:href "https://github.com/blosavio/chlog/blob/main/changelog.md"} "here"] "."]]]