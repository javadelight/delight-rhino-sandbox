package delight.rhinosandox.tests;

import delight.rhinosandox.RhinoSandbox;
import delight.rhinosandox.RhinoSandboxes;
import delight.rhinosandox.exceptions.ScriptCPUAbuseException;
import org.junit.Test;

@SuppressWarnings("all")
public class TestCPUViolationAndCatch {
  @Test(expected = ScriptCPUAbuseException.class)
  public void test_catch() {
    final RhinoSandbox sandbox = RhinoSandboxes.create();
    sandbox.setInstructionLimit(200000);
    sandbox.eval("try { while (true) { }; } catch (e) { }");
  }
}
