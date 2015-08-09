package delight.rhinosandox.tests

import org.junit.Test
import delight.rhinosandox.RhinoSandboxes

class TestCPUViolation {
	
	@Test
	def void test() {
		
		val sandbox = RhinoSandboxes.create
		
		sandbox.instructionLimit = 50000
		
		sandbox.eval("while (true) { };")
		
	}
	
}