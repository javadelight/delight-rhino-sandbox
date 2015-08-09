package delight.rhinosandox.tests;

import delight.rhinosandox.RhinoSandbox;
import delight.rhinosandox.RhinoSandboxes;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.junit.Test;

@SuppressWarnings("all")
public class TestInstanceScope {
  @Test(expected = Exception.class)
  public void test_isloated_instance_scopes() {
    final RhinoSandbox sandbox = RhinoSandboxes.create();
    sandbox.eval("var s=\"me\";");
    sandbox.eval("s;");
  }
  
  @Test
  public void test_global_scope() {
    final RhinoSandbox sandbox = RhinoSandboxes.create();
    sandbox.evalWithGlobalScope("var s=\"me\";");
    Object _eval = sandbox.eval("s;");
    InputOutput.<Object>println(_eval);
  }
}
