package delight.rhinosandox.tests;

import delight.rhinosandox.RhinoSandbox;
import delight.rhinosandox.RhinoSandboxes;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("all")
public class TestClassAccess {
  public static class TestEmbed {
    public String value;
    
    public void setValue(final String s) {
      this.value = s;
    }
  }
  
  @Test
  public void test_access_allowed() {
    final RhinoSandbox sandbox = RhinoSandboxes.create();
    final TestClassAccess.TestEmbed embedded = new TestClassAccess.TestEmbed();
    sandbox.inject("test", embedded);
    sandbox.eval("var x=1+1;test.setValue(\'\'+x);");
    Assert.assertEquals("2", embedded.value);
  }
  
  @Test
  public void test_java_variable() {
    final RhinoSandbox sandbox = RhinoSandboxes.create();
    Object _object = new Object();
    sandbox.inject("fromJava", _object);
    sandbox.allow(String.class);
    final Object res = sandbox.eval("fromJava.toString();");
  }
  
  @Test(expected = Exception.class)
  public void test_system_out_forbidden() {
    final RhinoSandbox sandbox = RhinoSandboxes.create();
    sandbox.eval("java.lang.System.out.println(\'hello\');");
  }
}
