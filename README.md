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
--> results in ScriptCPUAbuseException
```
