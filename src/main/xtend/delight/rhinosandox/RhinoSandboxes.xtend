package delight.rhinosandox

import delight.rhinosandox.internal.RhinoSandboxImpl

class RhinoSandboxes {
	
	def static RhinoSandbox create() {
		return new RhinoSandboxImpl
	}
	
}