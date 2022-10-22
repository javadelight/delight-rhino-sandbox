package delight.rhinosandox.tests;

import delight.rhinosandox.RhinoSandbox;
import delight.rhinosandox.RhinoSandboxes;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("all")
public class TestES6 {
 
  
  @Test
  public void test_es6_syntax() {
    final RhinoSandbox sandbox = RhinoSandboxes.create();
    final TestClassAccess.TestEmbed embedded = new TestClassAccess.TestEmbed();
    
    Object res = sandbox.eval("myscript", "let x=1+1;x;");
    Assert.assertEquals(Double.valueOf("2.0"), res);
  }
  
}
