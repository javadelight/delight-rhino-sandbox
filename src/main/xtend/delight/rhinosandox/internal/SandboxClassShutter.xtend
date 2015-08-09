package delight.rhinosandox.internal

import java.util.HashSet
import java.util.Set
import org.mozilla.javascript.ClassShutter

class SandboxClassShutter implements ClassShutter{
	
	val public Set<String> allowedClasses
	
	override visibleToScripts(String className) {
		if (allowedClasses.contains(className)) {
			return true
		} else {
			return false
		}
	}
	
	new() {
		this.allowedClasses = new HashSet()

	}
	
}