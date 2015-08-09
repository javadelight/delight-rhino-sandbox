package delight.rhinosandox.internal

import delight.rhinosandox.RhinoSandbox
import java.util.HashMap
import java.util.Map
import org.mozilla.javascript.Context
import org.mozilla.javascript.ContextFactory
import org.mozilla.javascript.Scriptable
import org.mozilla.javascript.ScriptableObject

class RhinoSandboxImpl implements RhinoSandbox {

	var ContextFactory contextFactory
	var ScriptableObject scope
	var int instructionLimit
	

	val Map<String, Object> inScope

	/**
	 * see https://developer.mozilla.org/en-US/docs/Mozilla/Projects/Rhino/Scopes_and_Contexts
	 */
	def void assertContext() {
		if (contextFactory != null) {
			return
		}

		contextFactory = new SafeContext()

		ContextFactory.initGlobal(contextFactory)

		try {
			val Context context = contextFactory.enterContext
			scope = context.initSafeStandardObjects(null, true)
			
			for (entry: inScope.entrySet) {
				scope.put(entry.key, scope, Context.toObject(entry.value, scope))
			}
			
		} finally {
			Context.exit
		}
	}

	override Object evalWithGlobalScope(String js) {
	}
	
	override Object eval(String js, Map<String, Object> variables) {
		assertContext

		try {
			val context = Context.enter
			
			// any new globals will not be avaialbe in global scope
			val Scriptable instanceScope = context.newObject(scope);
			instanceScope.setPrototype(scope);
			instanceScope.setParentScope(null);

			return context.evaluateString(scope, js, "js", 1, null)

		} finally {
			Context.exit
		}
	}
	
	override Object eval(String js) {
		eval(js, new HashMap)

	}

	override RhinoSandbox setInstructionLimit(int limit) {
		instructionLimit = limit;
	}

	/**
	 * Sets the maximum allowed duration for scripts.
	 */
	override RhinoSandbox setMaxDuration(int limitInMs) {
		this.maxDuration = limitInMs
	}

	override RhinoSandbox inject(String variableName, Object object) {
		if (this.inScope.containsKey(variableName)) {
			throw new IllegalArgumentException('A variable with the name ['+variableName+'] has already been defined.')
		}
		
		this.inScope.put(variableName, object)
		
		this
	}

	

	new() {	
		this.inScope = new HashMap<String, Object>
	}

}