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

	

	val public Map<String, Object> inScope

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
			
			for (Object o: inScope) {
				
				scope.put(o.class.simpleName, scope, Context.toObject(o, scope))
			}
			
		} finally {
			Context.exit
		}
	}

	override Object evalWithGlobalScope(String js) {
	}

	override Object eval(String js) {
		assertContext

		try {

			val context = Context.enter

//			if (!scope.isSealed) {
//				scope.sealObject
//				Preconditions.checkState(scope.isSealed)
//			}

			
			// any new globals will not be avaialbe in global scope
			val Scriptable instanceScope = context.newObject(scope);
			instanceScope.setPrototype(scope);
			instanceScope.setParentScope(null);

			return context.evaluateString(scope, js, "js", 1, null)

		} finally {
			Context.exit
		}

	}

	override RhinoSandbox setInstructionLimit(int limit) {
		this.instructionLimit = limit;
	}

	/**
	 * Sets the maximum allowed duration for scripts.
	 */
	override RhinoSandbox setMaxDuration(int limitInMs) {
		this.maxDuration = limitInMs
	}

	override RhinoSandbox inject(Object object) {
		this.inScope.put(object.class.simpleName, object)

		this
	}

	new() {
		
		this.inScope = new HashMap<String, Object>
	}

}