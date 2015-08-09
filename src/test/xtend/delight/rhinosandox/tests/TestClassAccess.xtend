package delight.rhinosandox.tests

import org.junit.Test
import delight.rhinosandox.RhinoSandboxes

class TestClassAccess {
	
	static class Test {
		def void printThis(String s) {
			println(s)
		}
	}
	
	@Test
	def void test() {
		val sandbox = RhinoSandboxes.create
		
		sandbox.allow(new Test)
		
		val res = sandbox.eval("var x=1+1;"+Test.name+".printThis(x);")
		
		println(res)
		
		
	}
	
}