[![Build Status](https://travis-ci.org/javadelight/delight-rhino-sandbox.svg?branch=master)](https://travis-ci.org/javadelight/delight-rhino-sandbox) [![Maven Central](https://img.shields.io/maven-central/v/org.javadelight/delight-rhino-sandbox.svg)](https://search.maven.org/#search%7Cga%7C1%7Cdelight-rhino-sandbox)

# Rhino Sandbox

A sandbox to execute JavaScript code with Rhino in Java.

Also see [Nashorn Sandbox](https://github.com/javadelight/delight-nashorn-sandbox).

## Usage

By default, access to **all Java classes is blocked**.

```java
RhinoSandbox sandbox = RhinoSandboxes.create();

sandbox.eval("java.lang.System.out.println('hello');");
// --> Exception
```

Java objects must be made explicitly available.

```java
RhinoSandbox sandbox = RhinoSandboxes.create();

sandbox.inject("fromJava", new Object());

sandbox.eval("fromJava.getClass();");
```

To protect against CPU abusive scripts, limits on the number of instructions allowed for the script can be set.

```java
RhinoSandbox sandbox = RhinoSandboxes.create();

sandbox.setInstructionLimit(1000000);

sandbox.eval("while (true) { }");
// --> results in ScriptCPUAbuseException
```

## Limitations

The instruction limit (CPU abuse protection) is enforced by Rhino's bytecode counter, which only
tracks interpreted JavaScript — not work performed inside native Java calls. Scripts that do heavy
work in few bytecodes (e.g. `Array.concat` in a loop doubling an array) can run far more native
allocations than the instruction limit suggests.

The duration watchdog partially mitigates this by enforcing a wall-clock timeout regardless of
bytecode count.

See [#31](https://github.com/javadelight/delight-rhino-sandbox/issues/31).

## Versions

- 0.2.0: Requiring Java 1.8, allowing to provide scritable parameter for [#27](https://github.com/javadelight/delight-rhino-sandbox/issues/27)
- 0.1.18: [Migrating Maven Namespace to Central Portal](https://maxrohde.com/2025/05/08/migrating-maven-namespace-to-central-portal/)
- 0.1.14: Fixing dependency to Guava 21 ([PR 20](https://github.com/javadelight/delight-rhino-sandbox/pull/20) by [candrews](https://github.com/candrews))
- 0.1.13: Improving concurrency ([PR 18](https://github.com/javadelight/delight-rhino-sandbox/pull/18) [PR 19](https://github.com/javadelight/delight-rhino-sandbox/pull/19) by [Tamil Selva Kaushik G](https://github.com/gtskaushik))
- 0.0.11: Fixing concurrency issue ([PR 9](https://github.com/javadelight/delight-rhino-sandbox/pull/9) by [kamac](https://github.com/kamac))

## Contributors

- [Tamil Selva Kaushik G](https://github.com/gtskaushik)

## Maven

Just add the following dependency to your projects.

```xml
<dependency>
    <groupId>org.javadelight</groupId>
    <artifactId>delight-rhino-sandbox</artifactId>
    <version>[get latest version from maven]</version>
</dependency>
```

This artifact is available on [Maven Central](https://search.maven.org/#search%7Cga%7C1%7Cdelight-rhino-sandbox) and 
[BinTray](https://bintray.com/javadelight/javadelight/delight-rhino-sandbox).

[![Maven Central](https://img.shields.io/maven-central/v/org.javadelight/delight-rhino-sandbox.svg)](https://search.maven.org/#search%7Cga%7C1%7Cdelight-rhino-sandbox)

