package delight.rhinosandox.tests;

import delight.rhinosandox.RhinoSandbox;
import delight.rhinosandox.RhinoSandboxes;
import delight.rhinosandox.exceptions.ScriptCPUAbuseException;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.junit.Test;
import org.mozilla.javascript.ContextFactory;

@SuppressWarnings("all")
public class TestCPUViolationAndCatch {
  @Test(expected = ScriptCPUAbuseException.class)
  public void test_catch() {
    try {
      boolean _hasExplicitGlobal = ContextFactory.hasExplicitGlobal();
      if (_hasExplicitGlobal) {
        throw new ScriptCPUAbuseException();
      }
      final RhinoSandbox sandbox = RhinoSandboxes.create();
      sandbox.setInstructionLimit(500000);
      Class<? extends TestCPUViolationAndCatch> _class = this.getClass();
      String _plus = ("Test_" + _class);
      sandbox.eval(_plus, "try { while (true) { }; } catch (e) { }");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
