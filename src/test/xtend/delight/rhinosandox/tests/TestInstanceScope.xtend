package delight.rhinosandox.tests

import org.junit.Test
import delight.rhinosandox.RhinoSandboxes

class TestInstanceScope {
	
	@Test(expected=Exception)
	def void test_instance_scopes() {
		
		val sandbox = RhinoSandboxes.create
		
		sandbox.eval('var s="me";');
		
		sandbox.eval('s;')		
	}
	
}