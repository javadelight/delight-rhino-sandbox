package delight.rhinosandox.tests;

import delight.rhinosandox.RhinoSandbox;
import delight.rhinosandox.RhinoSandboxes;
import delight.rhinosandox.exceptions.ScriptCPUAbuseException;
import org.junit.Test;
import org.mozilla.javascript.ContextFactory;

@SuppressWarnings("all")
public class TestCPUViolation {

  @Test(expected = ScriptCPUAbuseException.class)
  public void test() {
    boolean _hasExplicitGlobal = ContextFactory.hasExplicitGlobal();
    if (_hasExplicitGlobal) {
      throw new ScriptCPUAbuseException();
    }
    final RhinoSandbox sandbox = RhinoSandboxes.create();
    sandbox.setInstructionLimit(50000);
    Class<? extends TestCPUViolation> _class = this.getClass();
    String _plus = ("Test_" + _class);
    sandbox.eval(_plus, "while (true) { };");
  }
  
  @Test
  public void test_all_okay() {
    boolean _hasExplicitGlobal = ContextFactory.hasExplicitGlobal();
    if (_hasExplicitGlobal) {
      return;
    }
    final RhinoSandbox sandbox = RhinoSandboxes.create();
    sandbox.setInstructionLimit(200000);
    Class<? extends TestCPUViolation> _class = this.getClass();
    String _plus = ("Test_" + _class);
    sandbox.eval(_plus, "for (var i=0;i<=10000;i++) { };");
  }
}
