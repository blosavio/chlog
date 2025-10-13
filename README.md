
  <body>
    <a href="https://clojars.org/com.sagevisuals/chlog"><img src="https://img.shields.io/clojars/v/com.sagevisuals/chlog.svg"></a><br>
    <a href="#quick">Quick summary</a><br>
    <a href="#setup">Setup</a><br>
    <a href="https://blosavio.github.io/chlog/index.html">API</a><br>
    <a href="https://github.com/blosavio/chlog/blob/main/changelog.md">Changelog</a><br>
    <a href="#intro">Introduction</a><br>
    <a href="#ideas">Ideas</a><br>
    <a href="#usage">Usage</a><br>
    <a href="#possibilities">Possibilities</a><br>
    <a href="#critique">Critique</a><br>
    <a href="#alternatives">Alternatives</a><br>
    <a href="#examples">Examples</a><br>
    <a href="#glossary">Glossary</a><br>
    <a href="https://github.com/blosavio">Contact</a><br>
    <h1>
      Chlog
    </h1><em>A Clojure library for maintaining an edn changelog</em><br>
    <section id="quick">
      <h2>
        Quick summary
      </h2>
      <ol>
        <li>
          <p>
            A version number is merely a label; it conveys no other meaning.
          </p>
        </li>
        <li>
          <p>
            The changelog contains any and all information concerning switching from one version to another.
          </p>
        </li>
      </ol>
    </section>
    <section id="setup">
      <h2>
        Setup
      </h2>
      <h3>
        Leiningen/Boot
      </h3>
      <pre><code>[com.sagevisuals/chlog &quot;4&quot;]</code></pre>
      <h3>
        Clojure CLI/deps.edn
      </h3>
      <pre><code>com.sagevisuals/chlog {:mvn/version &quot;4&quot;}</code></pre>
      <h3>
        Require
      </h3>
      <pre><code>(require &apos;[chlog.core :refer [-main]])</code></pre>
    </section>
    <section id="intro">
      <h2>
        Introduction
      </h2>
      <p>
        Q: <em>What will happen if we switch from this version to that &nbsp;version?</em>
      </p>
      <p>
        A: <em>Consult the changelog, not the version number.</em>
      </p>
      <p>
        We ask too much of our software version numbers, and we should expect more &nbsp;from our changelogs. A <code>major.minor.patch</code> sequence of
        numbers simply doesn&apos;t have enough bandwidth to tell us how to &nbsp;make good decisions about switching versions. On the other hand, <span class=
        "small-caps">html</span>/markdown changelogs could potentially convey that information, but require &nbsp;a person to read and interpret it.
      </p>
      <p>
        Ideally, a changelog would contain a compact representation of what &nbsp;changed from one version to the next, and operational implications of those
        &nbsp;changes. If we use functions <code>foo</code> and <code>bar</code>, but only function <code>baz</code> changed, it&apos;s safe to switch. Or,
        function <code>baz</code> changed and we use it, but the change is non-breaking, so, again, it&apos;s safe &nbsp;to switch. Or, maybe <code>baz</code>
        was changed in a way that <em>does</em> break the way we use it, but the changelog tells us why it changed and how &nbsp;to manage the switch.
      </p>
      <p>
        Changelogs are often served as <a href="https://clojure.org/releases/devchangelog"><span class="small-caps">html</span></a> or <a href=
        "https://codeberg.org/leiningen/leiningen/raw/branch/main/resources/leiningen/new/template/CHANGELOG.md">markdown</a> files, intended for people to
        read. But to convey the depth of information &nbsp;we&apos;re talking about requires a high-level of discipline by the authors to &nbsp;comprehensively
        write all that out. Mistakes and omissions are bound to happen &nbsp;with free-form text, not to mention irregularities that thwart parsing, and in
        &nbsp;the end, a person has to synthesize a lot of bits to make a decision. <span class="small-caps">html</span>/markdown is ill-suited for this
        purpose.
      </p>
      <p>
        Storing a changelog in Clojure data structures offers many potential &nbsp;benefits. The data can be written, stored, retrieved, and manipulated
        &nbsp;programmatically. It can be <a href="https://github.com/blosavio/speculoos">validated</a> for correctness and completeness. And we could write
        <a href="#possibilities">utilities</a> that answer detailed questions about what it would be like to switch from &nbsp;one version to another.
      </p>
      <p>
        Chlog is a library that tests these ideas. It encompasses several parts. &nbsp;First, it promotes a set of <a href="#ideas">ideas</a>, some of which
        we&apos;ve already mentioned. Second, Chlog proposes a set of &nbsp;specifications for such a changelog. Third, Chlog offers an experimental
        &nbsp;implementation that maintains an <em>extensible data notation (</em><a href="https://github.com/edn-format/edn"><code>edn</code></a>) changelog
        specification, validates it, and generates easily-readable <span class="small-caps">html</span> and markdown webpages based upon the changelog data.
      </p>
      <p>
        The resulting changelog looks like <a href="https://github.com/blosavio/chlog/blob/main/changelog.md">this</a>.
      </p>
    </section>
    <section id="ideas">
      <h2>
        Ideas
      </h2>
      <h3>
        A version number is just a number
      </h3>
      <p>
        Tagging software with <code>major.minor.patch</code> numbers attempts to convey the notion <em>Yes, we can safely upgrade to such-and-such
        version</em>. But the granularity is poor. What if a dependency does have a breaking &nbsp;change, but the breaking change is in a portion of the
        dependency that we don&apos;t &nbsp;use. Version numbers ought to merely be a label to differentiate one release &nbsp;from another.
      </p>
      <p>
        If a version number is merely a label without semantics, how would someone &nbsp;judge whether to switch from one version to another? A detailed,
        concise, &nbsp;regularly-formatted changelog could convey all the information necessary to &nbsp;make an informed decision about if there is any
        benefit to changing versions, &nbsp;if changing versions will require updates on the consuming side, and if so, &nbsp;what updates are necessary.
      </p>
      <p>
        A later version is not promised to be <em>better</em>, merely different. The changelog authors will provide dispassionate &nbsp;information about the
        changes, and the people using the software can decide &nbsp;whether it is worth switching.
      </p>
      <p>
        Chlog is an experiment to detangle version numbers from changelog &nbsp;information. A version number&nbsp;<code>n</code> makes no claim other than it
        was released some time later than version&nbsp;<code>n-1</code>.
      </p>
      <h3>
        A changelog is data
      </h3>
      <p>
        The changelog <code>edn</code> files are the canonical sources of information. All other representations (<span class=
        "small-caps">html</span>/markdown, etc) are derived from that, and are merely conveniences.
      </p>
      <p id="info">
        A human- and machine-readable <code>edn</code> file will accompany each version. Each file contains a hashmap detailing &nbsp;that version. The global
        changelog is a sequence constructed by concatenating &nbsp;those hashmaps from all previous releases, i.e., &nbsp;the per-version hashmaps contained in
        <code>changelog-v<em>N</em>.edn</code> files located in a designated sub-directory.
      </p>
      <h3>
        A low threshold for breakage
      </h3>
      <p>
        The Chlog experiment advocates the changelog being the sole source of &nbsp;information on what will happen when switching versions. For that to
        succeed, &nbsp;the entries must accurately communicate whether a change is breaking. Not every &nbsp;change can be objectively categorized as either
        breaking or non-breaking (more &nbsp;on that in a moment). To have empathy for other people is tricky. If all &nbsp;changes are claimed as breaking,
        the concept loses its meaning and purpose. But &nbsp;if a supposedly safe change ends up breaking for someone else, trust is lost.
      </p>
      <p>
        A changelog that declares <code>:breaking? false</code> stipulates that switching to that version will work as it worked before with &nbsp;zero other
        changes (including changes in dependencies). Otherwise, the change &nbsp;is a <a href="#breaking"><em>breaking change</em></a>, explicitly indicated by
        <code>:breaking? true</code>.
      </p>
      <p>
        As a rough starting guideline, the following kinds of changes are <strong>probably</strong> breaking.
      </p>
      <ul>
        <li>all regressions (performance, memory, network)
        </li>
        <li>added or changed dependencies (see note below)
        </li>
        <li>removed or renamed namespaces
        </li>
        <li>moved, renamed, or removed functions
        </li>
        <li>stricter input requirements
        </li>
        <li>decreased return
        </li>
        <li>different default
        </li>
      </ul>
      <p>
        Likewise, the following kinds of changes are <strong>probably</strong> non-breaking.
      </p>
      <ul>
        <li>all improvements (performance, memory, network)
        </li>
        <li>removed dependencies
        </li>
        <li>added or deprecated namespaces
        </li>
        <li>added or deprecated functions
        </li>
        <li>relaxed input requirements
        </li>
        <li>increased returns
        </li>
        <li>implementation
        </li>
        <li>source code formatting
        </li>
        <li>documentation
        </li>
      </ul>
      <p>
        These are just starting guidelines. Careful judgment may say that a change &nbsp;in a function&apos;s defaults will in all cases be a non-breaking
        change. Or, a &nbsp;change in the documentation might be so severe that it&apos;s elevated to a breaking &nbsp;change.
      </p>
      <p>
        One important kind of change that sorta defies categorization is &nbsp;bug-fixes. According to the notion that a non-breaking change must be a perfect
        &nbsp;drop-in replacement, a bug fix would classify as a breaking change. Tentative &nbsp;policy: Bug fixes are non-breaking changes, but it depends on
        the scenario.
      </p>
      <h3>
        Formal specifications state required information
      </h3>
      <p>
        Each version has required information that is explicitly delineated in &nbsp;the <a href=
        "https://github.com/blosavio/chlog/blob/main/src/chlog/changelog_specifications.clj">specifications</a>. Correctness of a changelog, or any
        sub-component of the changelog, may be &nbsp;verified by <a href="https://blosavio.github.io/chlog/chlog.changelog-validations.html">validating</a> the
        changelog against those specifications.
      </p>
      <h3>
        <em>Et cetera</em>
      </h3>
      <ul>
        <li>
          <p>
            A changelog is mutable. Corrections are encouraged and additions are &nbsp;okay. The changelog itself is versioned-controlled data, and the
            <span class="small-caps">html</span>/markdown documents that are generated from the changelog data are also &nbsp;under version-control.
          </p>
        </li>
        <li>
          <p>
            Yanked or retracted releases can simply be noted by revising the &nbsp;changelog data.
          </p>
        </li>
        <li>
          <p>
            Much of the changelog data is objective (e.g., dates, email), but &nbsp;some is merely the changelog author&apos;s opinions. That&apos;s okay. The
            changelog &nbsp;author is communicating that opinion to the person considering switching &nbsp;versions. The changelog author may consider a
            particular bug-fix <code>:high</code> urgency, but the person using the software may not.
          </p>
        </li>
      </ul>
    </section>
    <section id="usage">
      <h2>
        Usage
      </h2>
      <p>
        There are four steps to using Chlog.
      </p>
      <ol>
        <li>
          <p>
            Maintain changelog data in <code>edn</code> files.
          </p>
        </li>
        <li>
          <p>
            <a href="#setup">Declare and require</a> the dependency.
          </p>
        </li>
        <li>
          <p>
            Create an <em>options</em> file.
          </p>
        </li>
        <li>
          <p>
            Generate the changelog documents.
          </p>
        </li>
      </ol>
      <h3>
        Changelog information is Clojure data
      </h3>
      <p>
        Changelog information is built by concatenating hashmaps contained in <code>edn</code> files located in the <a href="#entries">changelog entries
        directory</a>, one file per version. Every <a href="#version"><em>version</em></a> is represented by the following hashmap.
      </p>
      <pre><code>{:version ___
&nbsp;:date {:year ___
&nbsp;       :month ___
&nbsp;       :day ___ }
&nbsp;:responsible {:name ___
&nbsp;              :email ___ }
&nbsp;:project-status ___
&nbsp;:breaking? ___
&nbsp;:urgency ___
&nbsp;:comment ___
&nbsp;:changes [...]}</code></pre>
      <p>
        This hashmap (and all the following) is formally and canonically <a href=
        "https://github.com/blosavio/chlog/blob/main/src/chlog/changelog_specifications.clj">specified</a> with a <a href=
        "https://github.com/blosavio/speculoos">Speculoos</a> style specification.
      </p>
      <p>
        Briefly, the hashmap values:
      </p>
      <ul>
        <li>
          <strong>version</strong> is an integer.
        </li>
        <li id="date">
          <strong>date</strong> is a nested hashmap of integer year, string &nbsp;month, and integer day.
        </li>
        <li>
          <strong>responsible</strong> is a nested hashmap of a <code>:name</code> string and an <code>:email</code> string.
        </li>
        <li>
          <strong>project-status</strong> is one of enumerated keywords borrowed from &nbsp;the <a href=
          "https://github.com/metosin/open-source/blob/main/project-status.md">Metosin description</a>.
        </li>
        <li>
          <strong>breaking?</strong> is a boolean or <code>nil</code> (the later is only valid for the initial release).
        </li>
        <li>
          <strong>urgency</strong> is one of <code>:low</code>, <code>:medium</code>, or <code>:high</code>.
        </li>
        <li>
          <strong>comment</strong> is a free-form string.
        </li>
        <li>
          <strong>changes</strong> is a nested vector of <em>change</em> hashmaps (discussed soon).
        </li>
      </ul>
      <p>
        A <a href="#changelog">changelog</a> is a tail-appended sequence of one or more such <em>version</em> hashmaps. Furthermore, a version hashmap may have
        zero or more <a href="#change"><em>change</em></a> hashmaps associated to the <code>:changes</code> key. A <em>change</em> hashmap looks like this.
      </p>
      <pre><code>{:description ___
&nbsp;:reference {:source ___
&nbsp;            :url ___}
&nbsp;:change-type ___
&nbsp;:breaking? ___
&nbsp;:altered-functions []
&nbsp;:date {:year ___
&nbsp;       :month ___
&nbsp;       :day ___ }
&nbsp;:responsible {:name ___
&nbsp;              :email ___ }}</code></pre>
      <p>
        Besides the sequence of <code>:altered-functions</code> seen above, a change hashmap may also contain sequences of <code>:added-functions</code>,
        <code>:deprecated-functions</code>, <code>:moved-functions</code>, <code>:removed-functions</code>, and <code>:renamed-functions</code>.
      </p>
      <p>
        The parts of a change hashmap are:
      </p>
      <ul>
        <li>
          <strong>date</strong> analogous to the <a href="#date">date</a> of a version.
        </li>
        <li>
          <strong>reference</strong> (optional) a nested hashmap of <code>:source</code> string (e.g., a GitHub Issue, JIRA ticket, etc.), and
          <code>:url</code> string (creates a <a href=
          "https://github.com/blosavio/chlog/blob/main/resources/test_changelog/test_outputs/test_changelog.md#----------non-breaking-changes---------1">line-leading
          hyperlink</a>).
        </li>
        <li>
          <strong>breaking?</strong> a boolean.
        </li>
        <li>
          <strong>altered-functions</strong> a nested vector of symbols that were &nbsp;altered in this change.
        </li>
        <li>
          <strong>responsible</strong> a nested hashmap of a <code>:name</code> string and an <code>:email</code> string.
        </li>
        <li>
          <strong>change-type</strong> a keyword from this <a href=
          "https://github.com/blosavio/chlog/blob/2304b2e780c23d7094872b0c58bf6a94277c77d2/src/chlog/changelog_specifications.clj#L43">enumerated list</a>.
        </li>
        <li>
          <strong>description</strong> a free-form string.
        </li>
      </ul>
      <p>
        The changelog data may be manipulated and queried with any suitable &nbsp;Clojure function, such as <code>get-in</code>, <code>assoc-in</code>,
        <code>update-in</code>, etc. The Chlog library includes specifications for changelog data and &nbsp;utilities for performing those validations, but any
        Clojure validation &nbsp;facility, such as <code>clojure.spec.alpha</code>, may be used once suitable specification are written.
      </p>
      <p>
        Note: Chlog consults <em>every</em> file in the changelog entries directory whose filename begins with <code>changelog_v</code> and ends with
        <code>.edn</code>. Between those required bookends, files may be named according to whatever &nbsp;may be convenient, e.g., integers padded with zeros
        for human-readable sorting &nbsp;in the local filesystem or desktop environment.
      </p>
      <h3>
        Creating an <em>options</em> file
      </h3>
      <p>
        The <em>options</em> file is an <a href="https://github.com/edn-format/edn">edn</a> file (<a href=
        "https://github.com/blosavio/chlog/blob/main/resources/chlog_options.edn">example</a>) that contains a hashmap which supplies required information for
        generating a &nbsp;changelog. It also declares preferences for other optional settings.
      </p>
      <p>
        Required keys:
      </p>
      <ul>
        <li>
          <p>
            <code>:project-name-formatted</code> Project name (string) to display on changelog html/markdown &nbsp;documents.
          </p>
        </li>
        <li>
          <p>
            <code>:copyright-holder</code> Name displayed in the copyright statement in the footer of the &nbsp;changelog.
          </p>
        </li>
        <li>
          <p>
            <code>:UUID</code> Version 4 <strong>U</strong>niversally <strong>U</strong>nique <strong>Id</strong>entifier. Suggestion: eval-and-replace
            <code>(random-uuid)</code>. Default <code>nil</code>.
          </p>
        </li>
      </ul>
      <p>
        Optional keys (defaults supplied by <a href=
        "https://github.com/blosavio/chlog/blob/main/src/chlog/chlog_defaults.clj"><code>chlog_defaults.clj</code></a>):
      </p>
      <ul>
        <li id="entries">
          <p>
            <code>:changelog-entries-directory</code> Alternative directory to find changelog <code>edn</code> files. Include trailing &apos;/&apos;. Defaults
            to <code>resources/changelog_entries/</code>.
          </p>
        </li>
        <li>
          <p>
            <code>:changelog-html-directory</code> Alternative output <span class="small-caps">html</span> directory (string). Include trailing &apos;/&apos;.
            Defaults to <code>doc/</code>.
          </p>
        </li>
        <li>
          <p>
            <code>:changelog-html-filename</code> Alternative output <span class="small-caps">html</span> filename (string). Defaults to
            <code>changelog.html</code>.
          </p>
        </li>
        <li>
          <p>
            <code>:changelog-markdown-directory</code> Alternative output markdown directory (string). Include trailing `/`. &nbsp;Defaults to
            <code>&apos;&apos;</code> (i.e., project&apos;s root directory).
          </p>
        </li>
        <li>
          <p>
            <code>:changelog-markdown-filename</code> Alternative output markdown filename (string). Defaults to <code>changelog.md</code>.
          </p>
        </li>
        <li>
          <p>
            <code>:tidy-html?</code> Indent and wrap <span class="small-caps">html</span> and markdown files. Defaults to <code>false</code>.
          </p>
        </li>
      </ul>
      <h3>
        Generating the changelog documents
      </h3>
      <p>
        The Chlog library generates <span class="small-caps">html</span> and markdown changelog files from <a href=
        "https://github.com/weavejester/hiccup">hiccup</a> source. To generate the <span class="small-caps">html</span> and markdown files. We could evaluate…
      </p>
      <pre><code>(-main &quot;resources/chlog_options.edn&quot;)</code></pre>
      <p>
        …in our <span class="small-caps">repl</span>-attached editor. Or, we could run the <code>-main</code> function from command line something like this.
      </p>
      <pre><code>$ lein run -m chlog.core</code></pre>
      <p>
        Chlog produces two files. The first is a &apos;markdown&apos; file that&apos;s actually plain old <span class="small-caps">html</span>, abusing the
        fact that <span class="small-caps">html</span> passes through the markdown converter. By default, this markdown file is written to the project&apos;s
        root directory where GitHub can find and display the changelog. We don&apos;t need a dedicated markdown converter to view this file; copy it to a
        <a href="https://gist.github.com/">GitHub gist</a> and it&apos;ll display similarly to when we view it on GitHub. The second file, by default written
        to the <code>resources/</code> directory, is a proper <span class="small-caps">html</span> document with a <code>&lt;head&gt;</code>, etc., that is
        viewable in any browser as-is. Copy over the <a href="https://github.com/blosavio/chlog/blob/main/doc/project.css">css file</a> for some lightweight
        styling.
      </p>
    </section>
    <section id="possibilities">
      <h2>
        Possibilities
      </h2>
      <p>
        When changelog information is stored as Clojure data, it opens many intriguing possibilities.
      </p>
      <ul>
        <li>
          <p>
            Changelog data could be used to generate formatted <span class="small-caps">html</span> or markdown webpages for casual reading. Chlog currently
            implements this.
          </p>
        </li>
        <li>
          <p>
            A <code>js/cljs</code> widget embedded in a webpage that presents a <em>current version</em> selector and a <em>target version</em> selector. Then,
            based on each selection, the utility would collapse all the intervening versions and list the breaking and non-breaking changes. Someone
            considering switching versions could quickly click around and compare the available versions. <em>Switching from version&nbsp;3 to version&nbsp;4
            introduces one breaking change</em> whereas <em>Switching from version&nbsp;3 to version&nbsp;5 involves that same breaking change, plus another
            breaking change in a function we don&apos;t use.</em> Therefore, switching to version&nbsp;4 or version&nbsp;5 is equivalent.
          </p>
        </li>
        <li>
          <p>
            A utility that could scan the codebase and list all the functions used from a particular dependency. If we were curious about switching versions of
            that dependency, that list of functions would be compared to the list of functions with breaking changes. If there was no intersection of the
            lists, it&apos;s safe to switch versions. If the codebase <em>did</em> use a function with a breaking change, the changelog would communicate what
            changed, and how involved it would be to switch versions.
          </p>
        </li>
      </ul>
    </section>
    <section id="critique">
      <h2>
        Critique
      </h2>
      <p>
        On the one hand, making the changelog normalized data lends itself to straightforward analysis and display on the web. On the other hand, is it a
        strategic mistake to require that every change be a member of an enumerated set? Is having a change-kind <code>:other</code> a red flag? It&apos;s
        fairly typical for one of my commits to include an update to the source code, accompanied by some additional unit tests, and some kind of change in the
        documentation. At the moment, I would categorize it as a <code>:altered-function</code>, with the implicit understanding that someone reading the
        changelog would care about the changed function the most, and that the accompanying unit tests and docs are subordinate.
      </p>
      <p>
        But the rigidity causes concern, and perhaps only extended use will reveal if it&apos;s a deal-breaker.
      </p>
    </section>
    <section id="alternatives">
      <h2>
        Alternatives
      </h2>
      <ul>
        <li>
          <p>
            <strong>Classic markdown/<span class="small-caps">html</span> files</strong> Discussed above.
          </p>
        </li>
        <li>
          <p>
            <strong>Changelog generated from version control commit log</strong> At first glance, a changelog generated from a version control commit log seems
            natural. It is certainly easy, and there are many concepts in common. However, the purpose of a changelog is different enough to merit its own
            focus.
          </p>
          <p>
            Version control logs communicate information about the development history to developers of that software. Changelogs communicate the details of
            the released versions to people who consume the software. Related, but subtly different.
          </p>
          <p>
            A commit message notes fine-grained changes to the software, somewhat like a diary of software development. It would feel a bit oppressive to have
            to consider how every commit message would appear in a public changelog. The evolution of software is noisy. Commit messages may involve false
            starts, mistakes, and dead ends. They are meant to be read by people <em>developing</em> the software itself. Plus, writing commit messages that
            also serve as a changelog entry would require some kind of standards or specifications, or heroic discipline by the authors. Finally, commit
            messages typically do not concern themselves about whether the change is breaking for the people using the software.
          </p>
          <p>
            Changelogs, on the other hand, should clearly and concisely communicate, to people <em>using</em> the software, the differences between one version
            and another. It could be fine- or coarse-grained, but the freedom to decide should be independent of the version control commit log. Authoring a
            changelog requires care, judgment, and empathy for people ultimately using the software, and is a task somewhat different from wrangling version
            control commit messages.
          </p>
        </li>
      </ul>
      <p>
        Even at this early stage of its life, Chlog can alleviate most of that labor. Keep the changelog as <code>edn</code> data, and Chlog will take care of
        the <span class="small-caps">html</span>/markdown.
      </p>
    </section>
    <section id="examples">
      <h2>
        Examples
      </h2>
      <p>
        <a href="https://github.com/blosavio/speculoos/blob/main/changelog.md">Changelog</a> for a specification library.
      </p>
      <p>
        <a href="https://github.com/blosavio/fn-in/blob/master/changelog.md">Changelog</a> for a collection manipulation library.
      </p>
      <p>
        <a href="https://github.com/blosavio/readmoi/blob/main/changelog.md">Changelog</a> for a ReadMe generator library.
      </p>
      <p>
        <a href="https://github.com/blosavio/chlog/blob/main/changelog.md">Changelog</a> for a changelog generator library.
      </p>
      <p>
        <a href="https://github.com/blosavio/chlog/blob/main/resources/test_changelog/test_outputs/test_changelog.md">Changelog</a> for a fictitious library.
      </p>
    </section>
    <section id="glossary">
      <h2>
        Glossary
      </h2>
      <dl>
        <dt id="breaking">
          breaking
        </dt>
        <dd>
          <p>
            Any change that is <strong>not</strong> <a href="#non-breaking"><em>non-breaking</em></a>.
          </p>
        </dd>
      </dl>
      <dl>
        <dt id="changelog">
          changelog
        </dt>
        <dd>
          <p>
            A sequence of notable versions of project. Concretely, a tail-appended sequence containing one or more <a href="#version">version</a> hashmaps.
            Typically organized into multiple <code>edn</code> files located in a known directory where Chlog can locate it. The changelog contains information
            about the software that helps people understand what will happen when switching from one version of the software to another.
          </p>
        </dd>
      </dl>
      <dl>
        <dt id="change">
          change
        </dt>
        <dd>
          <p>
            Within a version, a report of some facet of the software that is different. Concretely, a hashmap containing information about the change kind
            (e.g., added function, deprecated function, implementation change, performance improvement, bug-fix, etc.), person responsible, namespaced function
            symbols involved, and an issue reference (e.g., GitHub issue number, JIRA ticket, etc.).
          </p>
        </dd>
      </dl>
      <dl>
        <dt id="non-breaking">
          non-breaking
        </dt>
        <dd>
          <p>
            A change that can be installed with zero other adjustments and the consuming software will work exactly as before. Otherwise, the change is
            <a href="#breaking"><em>breaking</em></a>.
          </p>
        </dd>
      </dl>
      <dl>
        <dt id="version">
          version
        </dt>
        <dd>
          <p>
            A notable release of software, labeled by a version number. Concretely, a hashmap containing information about the release date, the person
            responsible, the urgency of switching, breakage, free-form description, and a listing of zero or more detailed <a href="#change">changes</a>.
          </p>
        </dd>
      </dl>
    </section><br>
    <h2>
      License
    </h2>
    <p></p>
    <p>
      This program and the accompanying materials are made available under the terms of the <a href="https://opensource.org/license/MIT">MIT License</a>.
    </p>
    <p></p>
    <p id="page-footer">
      Copyright © 2024–2025 Brad Losavio.<br>
      Compiled by <a href="https://github.com/blosavio/readmoi">ReadMoi</a> on 2025 October 13.<span id="uuid"><br>
      e0d63371-4eb7-4431-a5f1-1cf0f5c46a72</span>
    </p>
  </body>
</html>
