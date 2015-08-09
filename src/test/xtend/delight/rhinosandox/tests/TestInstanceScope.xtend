package delight.rhinosandox.tests

import delight.rhinosandox.RhinoSandboxes
import org.junit.Assert
import org.junit.Test

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
		
		Assert.assertEquals("me", sandbox.eval('s;'))
		
		sandbox.eval('s="newvalue";')
		
		Assert.assertEquals("me", sandbox.eval('s;'))		
	}
	
}