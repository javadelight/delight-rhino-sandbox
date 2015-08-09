package delight.rhinosandox.tests;

import delight.rhinosandox.RhinoSandbox;
import delight.rhinosandox.RhinoSandboxes;
import org.junit.Assert;
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
    Assert.assertEquals("me", _eval);
    sandbox.eval("s=\"newvalue\";");
    Object _eval_1 = sandbox.eval("s;");
    Assert.assertEquals("me", _eval_1);
  }
}
