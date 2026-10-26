# Agent notes: JavaRush game projects

## Publication target

These projects are not published by running a normal JVM. JavaRush compiles the game and
**transpiles it to JavaScript**. The runtime only implements a subset of the JDK, so code that
compiles with `javac` can still fail to publish.

The project README lists the main limits:

- no Java newer than 8;
- no file I/O;
- no networking;
- no multithreading;
- no Reflection API;
- “and much more” — the JRE emulation is partial.

## Rule of thumb

Stick to APIs already used by the project and the game engine. Convenience APIs from the full
JDK are not automatically available on the JavaRush side.

Known-safe examples already used here: `String`, `StringBuilder`, `String.charAt/length`,
`String.valueOf`, collections, streams, enums, lambdas, `Date`, and the game engine API.

Avoid unless already present in the project:

- `String.format` and `java.util.Formatter`
- `java.util.Locale` (especially the `String.format(Locale, ...)` overload)
- regex-heavy APIs (`replaceAll`, `matches`, `split` with patterns)
- `java.time`, `java.nio`, `java.util.Optional` and similar newer convenience APIs
- files, sockets, threads, reflection

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

A second risk was relying on a newly added `Language.java` file. The enum was moved into the
existing `snake/model/Options.java`, so the publication no longer depends on a new file being
picked up. When adding new source files, verify they exist in the JavaRush web project before
publishing.

## Verification checklist

1. Compile with an actual JDK 8 (`javac 1.8.x`), not only `javac --release 8` on a newer JDK.
2. Search the changed code for APIs outside the supported subset.
3. Check that all referenced source files are present in the JavaRush project.
4. Publish, or at minimum test the game locally with the JavaRush engine jar.
5. Do not treat “compiles locally” as “publishes on JavaRush.”
