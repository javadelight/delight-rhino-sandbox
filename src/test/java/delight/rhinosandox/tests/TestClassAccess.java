package delight.rhinosandox.tests;

import delight.rhinosandox.RhinoSandbox;
import delight.rhinosandox.RhinoSandboxes;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.junit.Test;

@SuppressWarnings("all")
public class TestClassAccess {
  @Test
  public void test() {
    final RhinoSandbox sandbox = RhinoSandboxes.create();
    final Object res = sandbox.eval("var x=1+1;java.lang.System.out.println(x);");
    InputOutput.<Object>println(res);
  }
}
