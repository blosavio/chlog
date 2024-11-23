[:section#ideas
 [:h2 "Chlog ideas"]

 [:h3 "A version number is just a number"]

 [:p "Software versioning doesn't serve us as well. " [:code "major.minor.patch"] " version numbers attempt to convey the notion " [:em "Yes, we can safely upgrade to such-and-such version"] ". But the granularity is poor. What if a dependency does have a breaking change, but the breaking change is in a portion of the dependency that we don't use. Version numbers ought to merely be a label to differentiate one release from another."]

 [:p "If a version number is merely a label without semantics, how would someone judge whether to change from one version to another? A detailed, concise, regularly-formatted changelog conveys all the information necessary to make an informed decision about if there is any benefit to change versions, if changing version will require changes to on the consuming side, and if so, what changes are necessary."]

 [:p "A later version is not promised to be " [:em "better"] ", merely different. The changelog authors will provide dispassionate information about the changes, and the consumers can decide whether it is worth switching."]
 
 [:p "Chlog is an experiment to detangle version numbers from changelog information. A version number " [:code "n"] " makes no claim other than it was released some time later than version " [:code "n-1"] "."]

 [:h3 "A changelog is data"]
 
 [:p "The changelog.edn is the single, canonical source of information. All other representations (html/markdown, etc) are derived from that."]
 
 [:p#info "A human- and machine-readable " [:code "changelog.edn"] " will accompany each version at the project's root directory. " [:code "changelog.edn"] " is tail-appended file constructed from all previous releases, possibly automatically-composed of per-version " [:code "changelog-v" [:em "N"] ".edn"] " files in a sub-directory."]
 
 [:p "A " [:code "changelog.md"] " file, intended for display on the web, is generated by a script. This script also contains specifications describing the changelog data."]

 [:h3 "A low threshold for breakage"]

 [:p "Req judgment, consideration/empathy for other people."]
 
 [:p "Tentative policy: Bug fixes are non-breaking changes."]

 [:h3 "Required information for each version"]

 [:p "Explicit, programmatic specifications"]
 
 ]