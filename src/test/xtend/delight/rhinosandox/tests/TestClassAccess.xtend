package delight.rhinosandox.tests

import org.junit.Test
import delight.rhinosandox.RhinoSandboxes

class TestClassAccess {
	
	@Test
	def void test() {
		val sandbox = RhinoSandboxes.create
		
		val res = sandbox.eval("var x=1+1;x;")
		
		println(res)
		
		
	}
	
}