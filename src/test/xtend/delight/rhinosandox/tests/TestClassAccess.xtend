package delight.rhinosandox.tests

import delight.rhinosandox.RhinoSandboxes
import org.junit.Assert
import org.junit.Test

import static delight.rhinosandox.tests.TestClassAccess.*

class TestClassAccess {
	 
	
	static class TestEmbed {
		public String value
		def void setValue(String s) {
			value = s
		}
	}
	
	@Test
	def void test() {
		val sandbox = RhinoSandboxes.create
		
		val embedded = new TestEmbed
		sandbox.inject("test",embedded)
		
		sandbox.eval("var x=1+1;test.setValue(''+x);")
		
		Assert.assertEquals("2", embedded.value)
		
		
	}
	
}