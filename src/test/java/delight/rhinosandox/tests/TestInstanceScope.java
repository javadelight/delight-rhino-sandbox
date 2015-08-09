package delight.rhinosandox.tests;

import delight.rhinosandox.RhinoSandbox;
import delight.rhinosandox.RhinoSandboxes;
import org.junit.Test;

@SuppressWarnings("all")
public class TestInstanceScope {
  @Test(expected = Exception.class)
  public void test_instance_scopes() {
    final RhinoSandbox sandbox = RhinoSandboxes.create();
    sandbox.eval("var s=\"me\";");
    sandbox.eval("s;");
  }
}
