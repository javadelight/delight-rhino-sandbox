package delight.rhinosandox.internal

import java.util.Set
import org.mozilla.javascript.ClassShutter
import java.util.HashSet

class SafeClassShutter implements ClassShutter {
	
	val public Set<String> allowedClasses
	
	override visibleToScripts(String fullClassName) {
		if (fullClassName.startsWith("adapter")) {
			return true
		}
		return allowedClasses.contains(fullClassName)
	}
	
	new() {
		this.allowedClasses = new HashSet
		
		this.allowedClasses.add(org.mozilla.javascript.EcmaError.name)
	}
	
}