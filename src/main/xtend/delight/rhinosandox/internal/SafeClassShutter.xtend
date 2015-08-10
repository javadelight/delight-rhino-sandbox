package delight.rhinosandox.internal

import org.mozilla.javascript.ClassShutter

class SafeClassShutter implements ClassShutter {
	
	override visibleToScripts(String fullClassName) {
		return false
	}
	
}