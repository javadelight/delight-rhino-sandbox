package delight.rhinosandox.tests

import delight.rhinosandox.RhinoSandboxes
import delight.rhinosandox.exceptions.ScriptCPUAbuseException
import org.junit.Test
import org.mozilla.javascript.ContextFactory

class TestCPUViolationAndCatch {
	
	@Test(expected=ScriptCPUAbuseException)
	def void test_catch() {
		if (ContextFactory.hasExplicitGlobal) {
			// this test needs to set the global context factory to succeed.
			throw new ScriptCPUAbuseException();
		}
		
		val sandbox = RhinoSandboxes.create
		
		sandbox.instructionLimit = 500000
		
		sandbox.eval("Test_"+this.class, "try { while (true) { }; } catch (e) { }")
		
	}
	
}