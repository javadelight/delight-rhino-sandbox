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
    Class<? extends TestInstanceScope> _class = this.getClass();
    String _plus = ("Test_" + _class);
    sandbox.eval(_plus, "var s=\"me\";");
    Class<? extends TestInstanceScope> _class_1 = this.getClass();
    String _plus_1 = ("Test_" + _class_1);
    sandbox.eval(_plus_1, "s;");
  }
  
  @Test
  public void test_global_scope() {
    final RhinoSandbox sandbox = RhinoSandboxes.create();
    Class<? extends TestInstanceScope> _class = this.getClass();
    String _plus = ("Test_" + _class);
    sandbox.evalWithGlobalScope(_plus, "var s=\"me\";");
    Class<? extends TestInstanceScope> _class_1 = this.getClass();
    String _plus_1 = ("Test_" + _class_1);
    Assert.assertEquals("me", sandbox.eval(_plus_1, "s;"));
    Class<? extends TestInstanceScope> _class_2 = this.getClass();
    String _plus_2 = ("Test_" + _class_2);
    sandbox.eval(_plus_2, "s=\"newvalue\"; s;");
    Class<? extends TestInstanceScope> _class_3 = this.getClass();
    String _plus_3 = ("Test_" + _class_3);
    Assert.assertEquals("me", sandbox.eval(_plus_3, "s;"));
  }
}
