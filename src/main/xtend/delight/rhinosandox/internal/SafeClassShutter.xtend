package delight.rhinosandox.internal

import java.util.Set
import org.mozilla.javascript.ClassShutter
import java.util.HashSet

class SafeClassShutter implements ClassShutter {
	
	val public Set<String> allowedClasses
	
	override visibleToScripts(String fullClassName) {
		println("test "+fullClassName+" has "+allowedClasses)
		return allowedClasses.contains(fullClassName)
	}
	
	new() {
		this.allowedClasses = new HashSet
	}
	
}