package delight.rhinosandox.tests;

import delight.rhinosandox.RhinoSandbox;
import delight.rhinosandox.RhinoSandboxes;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("all")
public class TestClassAccess {
  public static class TestEmbed {
    public void printThis(final String s) {
      TestClassAccess.value = s;
    }
  }
  
  private static String value;
  
  @Test
  public void test() {
    final RhinoSandbox sandbox = RhinoSandboxes.create();
    TestClassAccess.TestEmbed _testEmbed = new TestClassAccess.TestEmbed();
    sandbox.inject(_testEmbed);
    String _simpleName = TestClassAccess.TestEmbed.class.getSimpleName();
    String _plus = ("var x=1+1;" + _simpleName);
    String _plus_1 = (_plus + ".printThis(\'\'+x);");
    sandbox.eval(_plus_1);
    Assert.assertEquals("2", TestClassAccess.value);
  }
}
