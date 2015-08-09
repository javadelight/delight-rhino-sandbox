package delight.rhinosandox.tests;

import delight.rhinosandox.RhinoSandbox;
import delight.rhinosandox.RhinoSandboxes;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class TestClassAccess {
  public static class Test {
    public void printThis(final String s) {
      InputOutput.<String>println(s);
    }
  }
  
  /* @
   */public void test() {
    final RhinoSandbox sandbox = RhinoSandboxes.create();
    TestClassAccess.Test _test = new TestClassAccess.Test();
    sandbox.allow(_test);
    String _name = TestClassAccess.Test.class.getName();
    String _plus = ("var x=1+1;" + _name);
    String _plus_1 = (_plus + ".printThis(x);");
    final Object res = sandbox.eval(_plus_1);
    InputOutput.<Object>println(res);
  }
}
