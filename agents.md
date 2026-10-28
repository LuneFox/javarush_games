# Agent notes: JavaRush game projects

## Leave changes uncommitted

Every change requested in this project must be left as **uncommitted changes** in the working
tree. Do not `git commit`, do not `git push`, do not amend, squash, rebase, tag, or create
branches. Do not run `git stash` on the user's work either. The user reviews the diff and
commits it themselves.

## Publication target

These projects are not published by running a normal JVM. JavaRush compiles the game and
**transpiles it to JavaScript**. The runtime only implements a subset of the JDK, so code that
compiles with `javac` can still fail to publish.

Publication itself is manual: only the user can upload the project and trigger publication in
the JavaRush web interface. The agent can prepare code, compile it locally, and inspect an
already published bundle, but cannot publish the project or verify the publication result.

The project README lists the main limits:

- no Java newer than 8;
- no file I/O;
- no networking;
- no multithreading;
- no Reflection API;
- “and much more” — the JRE emulation is partial.

## Published runtime is a GWT/JS app

The public JavaRush page is only an Angular shell. The game itself is a separately served,
GWT-compiled JavaScript application. For the Space Invaders publication this was:

- page: <https://javarush.com/projects/apps/50729> (`Galabugs Invaders`);
- game app: <https://apps.javarush.com/game-task/published/68556969/WebApp.html>;
- loader: `WebApp.nocache.js`, which loads a GWT permutation `*.cache.js`;
- GWT version: 2.8.2.

Consequences for format/porting work:

- The published artifact is obfuscated JavaScript, not a JVM application. Local `javac`
  success says nothing about the JavaScript runtime, and vice versa.
- Original Java source and source maps are not exposed. The `.cache.js.map` URL returned 404.
- Package and class names survive compilation. The published bundle still contained
  `com.javarush.games.spaceinvaders`, `SpaceInvadersGame`, `Arena`, `EnemyArmy`, `Mario`,
  `EagleBase`, `Eagle`, `BaseBrick`, `Sprite` and `CellLabel`. This is enough to inspect the
  class structure and some logic, but not to recover readable Java source.
- Rendering goes through GWT cell widgets from `com.javarush.engine.cell` (DOM cells), according
  to the bundle; it is not an HTML `<canvas>` renderer.
- The game app URL is publication-specific. If it changes after republishing, the current
  `appUrl` can be found in the page's serialized state/API response, for example the
  `/api/1.0/rest/apps/app50729` entry.
- Therefore, when a published game behaves differently from the local build, inspect the
  published GWT bundle/app as well as the local Java source. Do not assume the JavaRush web
  project is running the same artifact that was compiled locally.

Practical consequence for API experiments: local `javac` only proves that the code compiles
against the desktop JDK 8. The JavaRush/GWT server compiler has its own JRE emulation; a class
or method can compile locally but be rejected during GWT compilation or behave differently in
the published bundle. The agent cannot publish the project or verify the JavaRush publication:
that is a manual action performed by the user in the JavaRush web interface. When trying a new
Java API, flag the risk, keep a fallback implementation using APIs already proven in this
project, and ask the user to publish/check it.

## Rule of thumb

Stick to APIs already used by the project and the game engine. Convenience APIs from the full
JDK are not automatically available on the JavaRush side.

Known-safe examples already used here: `String`, `StringBuilder`, `String.charAt/length`,
`String.valueOf`, collections, streams, enums, lambdas, plain interfaces and abstract classes,
small utility classes, `Date`, and the game engine API.

Avoid unless already present in the project:

- `String.format` and `java.util.Formatter`
- `java.util.Locale` (especially the `String.format(Locale, ...)` overload)
- regex-heavy APIs (`replaceAll`, `matches`, `split` with patterns)
- `java.time`, `java.nio`, `java.util.Optional` and similar newer convenience APIs
- files, sockets, threads, reflection

## New types are fine

Adding new source files is allowed. If an interface, abstract class, value object or small
utility class makes the design clearer, extract it — the file being new is not a reason to
avoid it. New types must follow the same rules as the existing code:

- Java 8 and only the supported API subset listed above;
- no new external dependency, no file/network/thread/reflection APIs;
- the new file is present in the JavaRush web project and is picked up on publication.

## Incident that prompted this note

A localization update introduced:

```java
String.format(Locale.ROOT, template, args)
```

It compiled and ran locally on Java 8, but publication failed with:

> Не удалось опубликовать проект: он не скомпилировался.

The Java-to-JS runtime does not emulate `java.util.Formatter`/the `Locale` overload, so the
server rejected the class. The fix was a small `%s`/`%d` replacement helper using only
`StringBuilder`, `String.charAt/length`, and `toString()`.
See `snake/model/Strings.java`.

A second risk was relying on a newly added `Language.java` file that the JavaRush web project
did not pick up. The enum was moved into the existing `snake/model/Options.java` at the time.
That was a project-sync problem, not a ban on new files: new types are fine (see “New types
are fine” above), but verify that the JavaRush project contains the file before publishing.

## Verification checklist

1. Compile with an actual JDK 8 (`javac 1.8.x`), not only `javac --release 8` on a newer JDK.
2. Search the changed code for APIs outside the supported subset.
3. Check that all referenced source files (including newly added ones) are present in the
   JavaRush project.
4. Test the game locally with the JavaRush engine jar. Publication is manual and can only be
   performed by the user in the JavaRush web interface; do not claim to have published or
   verified it.
5. After the user publishes, if behavior differs, inspect the published GWT app/bundle (see
   “Published runtime is a GWT/JS app”) and compare it with the local Java source.
6. Do not treat “compiles locally” as “publishes on JavaRush.”
