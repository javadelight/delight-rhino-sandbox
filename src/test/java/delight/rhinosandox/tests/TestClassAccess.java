package delight.rhinosandox.tests;

import delight.rhinosandox.RhinoSandbox;
import delight.rhinosandox.RhinoSandboxes;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.junit.Test;

@SuppressWarnings("all")
public class TestClassAccess {
  public static class TestEmbed {
    public void printThis(final String s) {
      InputOutput.<String>println(s);
    }
  }
  
  @Test
  public void test() {
    final RhinoSandbox sandbox = RhinoSandboxes.create();
    TestClassAccess.TestEmbed _testEmbed = new TestClassAccess.TestEmbed();
    sandbox.allow(_testEmbed);
    String _simpleName = TestClassAccess.TestEmbed.class.getSimpleName();
    String _plus = ("var x=1+1;" + _simpleName);
    String _plus_1 = (_plus + ".printThis(\'\'+x);TestEmbed;");
    final Object res = sandbox.eval(_plus_1);
    InputOutput.<Object>println(res);
  }
}
