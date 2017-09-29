package delight.rhinosandox.tests

import delight.rhinosandox.RhinoSandboxes
import delight.rhinosandox.exceptions.ScriptCPUAbuseException
import org.junit.Test
import org.mozilla.javascript.ContextFactory

class TestCPUViolation {
	
	@Test(expected=ScriptCPUAbuseException)
	def void test() {
		
		if (ContextFactory.hasExplicitGlobal) {
			// this test needs to set the global context factory to succeed.
			throw new ScriptCPUAbuseException();
		}
		
		val sandbox = RhinoSandboxes.create
		
		sandbox.instructionLimit = 50000
		
		sandbox.eval("Test_"+this.class, "while (true) { };")
		
	}
	
	@Test
	def void test_all_okay() {
		
		if (ContextFactory.hasExplicitGlobal) {
			// this test needs to set the global context factory to succeed.
			throw new ScriptCPUAbuseException();
		}
		
		val sandbox = RhinoSandboxes.create
		
		sandbox.instructionLimit = 200000
		
		sandbox.eval("Test_"+this.class, "for (var i=0;i<=10000;i++) { };")
		
	}
	
	
	
}