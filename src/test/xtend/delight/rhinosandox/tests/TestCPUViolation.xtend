package delight.rhinosandox.tests

import org.junit.Test
import delight.rhinosandox.RhinoSandboxes
import delight.rhinosandox.exceptions.ScriptCPUAbuseException

class TestCPUViolation {
	
	@Test(expected=ScriptCPUAbuseException)
	def void test() {
		
		val sandbox = RhinoSandboxes.create
		
		sandbox.instructionLimit = 50000
		
		sandbox.eval("Test_"+this.class, "while (true) { };")
		
	}
	
	@Test
	def void test_all_okay() {
		
		val sandbox = RhinoSandboxes.create
		
		sandbox.instructionLimit = 200000
		
		sandbox.eval("Test_"+this.class, "for (var i=0;i<=10000;i++) { };")
		
	}
	
	
	
}