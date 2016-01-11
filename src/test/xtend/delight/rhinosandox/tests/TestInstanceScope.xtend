package delight.rhinosandox.tests

import delight.rhinosandox.RhinoSandboxes
import org.junit.Assert
import org.junit.Test

class TestInstanceScope {
	
	@Test(expected=Exception)
	def void test_isloated_instance_scopes() {
		
		val sandbox = RhinoSandboxes.create
		
		sandbox.eval("Test_"+this.class,'var s="me";');
		
		sandbox.eval("Test_"+this.class,'s;')		
	}
	
	@Test
	def void test_global_scope() {
		val sandbox = RhinoSandboxes.create
		
		sandbox.evalWithGlobalScope("Test_"+this.class,'var s="me";');
		
		Assert.assertEquals("me", sandbox.eval("Test_"+this.class,'s;'))
		
		sandbox.eval("Test_"+this.class,'s="newvalue"; s;')
		
		Assert.assertEquals("me", sandbox.eval("Test_"+this.class,'s;'))		
		
		
	}
	
}