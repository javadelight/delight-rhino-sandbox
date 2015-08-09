package delight.rhinosandox.tests;

import delight.rhinosandox.RhinoSandbox;
import delight.rhinosandox.RhinoSandboxes;
import org.junit.Test;

@SuppressWarnings("all")
public class TestCPUViolation {
  @Test
  public void test() {
    final RhinoSandbox sandbox = RhinoSandboxes.create();
    sandbox.setInstructionLimit(50000);
    sandbox.eval("while (true) { };");
  }
}
