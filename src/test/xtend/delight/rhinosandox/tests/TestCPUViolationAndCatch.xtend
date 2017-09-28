package delight.rhinosandox.tests

import delight.rhinosandox.RhinoSandboxes
import delight.rhinosandox.exceptions.ScriptCPUAbuseException
import org.junit.Test

class TestCPUViolationAndCatch {
	
	@Test(expected=ScriptCPUAbuseException)
	def void test_catch() {
		
		val sandbox = RhinoSandboxes.create
		
		sandbox.instructionLimit = 50000
		
		sandbox.eval("Test_"+this.class, "try { while (true) { }; } catch (e) { }")
		
	}
	
}