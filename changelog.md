
  <body>
    <h1>
      Chlog library changelog
    </h1><a href="https://github.com/blosavio/chlog">changelog info</a>
    <section>
      <h3 id="v5">
        version 5
      </h3>
      <p>
        2025 October 27<br>
        Brad Losavio (blosavio@sagevisuals.com)<br>
        <em>Description:</em> Adjusting ReadMoi dependency version.<br>
        <em>Project status:</em> <a href="https://github.com/metosin/open-source/blob/main/project-status.md">active</a><br>
        <em>Urgency:</em> medium<br>
        <em>Breaking:</em> yes
      </p>
      <p></p>
      <div>
        <h4>
          Breaking changes
        </h4>
        <ul>
          <li>
            <div>
              Sync to ReadMoi release version 6.
            </div>
          </li>
        </ul>
        <h4>
          Non-breaking changes
        </h4>
        <ul></ul>
      </div>
      <hr>
    </section>
    <section>
      <h3 id="v4">
        version 4
      </h3>
      <p>
        2025 October 13<br>
        Brad Losavio (blosavio@sagevisuals.com)<br>
        <em>Description:</em> Adjusting ReadMoi dependency version.<br>
        <em>Project status:</em> <a href="https://github.com/metosin/open-source/blob/main/project-status.md">active</a><br>
        <em>Urgency:</em> medium<br>
        <em>Breaking:</em> yes
      </p>
      <p></p>
      <div>
        <h4>
          Breaking changes
        </h4>
        <ul>
          <li>
            <div>
              Chlog v3 required a ReadMoi snapshot. This version, v4, requires the release version.
            </div>
          </li>
        </ul>
        <h4>
          Non-breaking changes
        </h4>
        <ul></ul>
      </div>
      <hr>
    </section>
    <section>
      <h3 id="v3">
        version 3
      </h3>
      <p>
        2025 October 13<br>
        Brad Losavio (blosavio@sagevisuals.com)<br>
        <em>Description:</em> Made compatible with latest version of a dependency.<br>
        <em>Project status:</em> <a href="https://github.com/metosin/open-source/blob/main/project-status.md">active</a><br>
        <em>Urgency:</em> medium<br>
        <em>Breaking:</em> yes
      </p>
      <p></p>
      <div>
        <h4>
          Breaking changes
        </h4>
        <ul>
          <li>
            <div>
              Version 5 ReadMoi, a dependency, introduces a `-main` function which conflicts with Chlog. This version implements an exclude pattern that fixes
              the problem described by the warning.
            </div>
          </li>
          <li>
            <div>
              Fixed two improper dependency levels. Promoted ReadMoi to a full dependency because it is required by `core` ns. Demoted Speculoos because it is
              merely a dev-time requirement.
            </div>
          </li>
        </ul>
        <h4>
          Non-breaking changes
        </h4>
        <ul></ul>
      </div>
      <hr>
    </section>
    <section>
      <h3 id="v2">
        version 2
      </h3>
      <p>
        2025 October 4<br>
        Brad Losavio (blosavio@sagevisuals.com)<br>
        <em>Description:</em> Various quality-of-life improvements.<br>
        <em>Project status:</em> <a href="https://github.com/metosin/open-source/blob/main/project-status.md">active</a><br>
        <em>Urgency:</em> medium<br>
        <em>Breaking:</em> no
      </p>
      <p></p>
      <div>
        <em>added functions:</em> <code>-main</code>
      </div>
      <p></p>
      <div>
        <h4>
          Breaking changes
        </h4>
        <ul></ul>
        <h4>
          Non-breaking changes
        </h4>
        <ul>
          <li>
            <div>
              <a href="https://github.com/blosavio/chlog/issues/1">GitHub Issue #1</a>: Added html IDs to section headers to enable hyperlinking to specific
              changelog entries.
            </div>
          </li>
          <li>
            <div>
              <a href="https://github.com/blosavio/chlog/issues/2">GitHub Issue #2</a>: Loading changelog entry files is now automatic, eliminating the
              requirement for manually maintaining a global &apos;changelog.edn&apos;.
            </div>
          </li>
          <li>
            <div>
              <a href="https://github.com/blosavio/chlog/issues/3">GitHub Issue #3</a>: Added a `-main` entry point, so that generating changelogs may be
              initiated from a repl or the command line.
            </div>
          </li>
          <li>
            <div>
              <a href="https://github.com/blosavio/chlog/issues/4">GitHub Issue #4</a>: The new `-main` function accepts alternative options file locations.
            </div>
          </li>
        </ul>
      </div>
      <hr>
    </section>
    <section>
      <h3 id="v1">
        version 1
      </h3>
      <p>
        2024 December 6<br>
        Brad Losavio (blosavio@sagevisuals.com)<br>
        <em>Description:</em> Added html tidy-ing option.<br>
        <em>Project status:</em> <a href="https://github.com/metosin/open-source/blob/main/project-status.md">active</a><br>
        <em>Urgency:</em> medium<br>
        <em>Breaking:</em> no
      </p>
      <p></p>
      <div>
        <h4>
          Breaking changes
        </h4>
        <ul></ul>
        <h4>
          Non-breaking changes
        </h4>
        <ul>
          <li>
            <div>
              Added option to tidy html in output files, with associated additional defaults.
            </div>
          </li>
        </ul>
      </div>
      <hr>
    </section>
    <section>
      <h3 id="v0">
        version 0
      </h3>
      <p>
        2024 November 21<br>
        Brad Losavio (blosavio@sagevisuals.com)<br>
        <em>Description:</em> Initial public release.<br>
        <em>Project status:</em> <a href="https://github.com/metosin/open-source/blob/main/project-status.md">active</a><br>
        <em>Urgency:</em> low<br>
        <em>Breaking:</em> no
      </p>
      <p></p>
      <div>
        <h4>
          Breaking changes
        </h4>
        <ul></ul>
        <h4>
          Non-breaking changes
        </h4>
        <ul></ul>
      </div>
      <hr>
    </section>
    <p id="page-footer">
      Copyright © 2024–2025 Brad Losavio.<br>
      Compiled by <a href="https://github.com/blosavio/chlog">Chlog</a> on 2025 October 27.<span id="uuid"><br>
      d571f801-3b49-4fd9-a5f3-620e034d0a8d</span>
    </p>
  </body>
</html>
