package delight.rhinosandox.tests

import delight.rhinosandox.RhinoSandboxes
import org.junit.Assert
import org.junit.Test

import static delight.rhinosandox.tests.TestClassAccess.*

class TestClassAccess {
	
	static String value
	
	static class TestEmbed {
		def void printThis(String s) {
			value = s
		}
	}
	
	@Test
	def void test() {
		val sandbox = RhinoSandboxes.create
		
		sandbox.inject("test",new TestEmbed)
		
		
		sandbox.eval("var x=1+1;test.printThis(''+x);")
		
		Assert.assertEquals("2", value)
		
		
	}
	
}