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

## Versions

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
    <version>0.0.14</version>
</dependency>
```

This artifact is available on [Maven Central](https://search.maven.org/#search%7Cga%7C1%7Cdelight-rhino-sandbox) and 
[BinTray](https://bintray.com/javadelight/javadelight/delight-rhino-sandbox).

[![Maven Central](https://img.shields.io/maven-central/v/org.javadelight/delight-rhino-sandbox.svg)](https://search.maven.org/#search%7Cga%7C1%7Cdelight-rhino-sandbox)

