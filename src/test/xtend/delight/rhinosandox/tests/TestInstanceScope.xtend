package delight.rhinosandox.tests

import org.junit.Test
import delight.rhinosandox.RhinoSandboxes

class TestInstanceScope {
	
	@Test(expected=Exception)
	def void test_isloated_instance_scopes() {
		
		val sandbox = RhinoSandboxes.create
		
		sandbox.eval('var s="me";');
		
		sandbox.eval('s;')		
	}
	
	@Test
	def void test_global_scope() {
		val sandbox = RhinoSandboxes.create
		
		sandbox.evalWithGlobalScope('var s="me";');
		
		println(sandbox.eval('s;'))
		
	}
	
}