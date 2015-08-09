package delight.rhinosandox.internal

import delight.rhinosandox.RhinoSandbox
import java.util.HashSet
import java.util.Set
import org.mozilla.javascript.Context
import org.mozilla.javascript.ContextFactory
import org.mozilla.javascript.Scriptable
import org.mozilla.javascript.ScriptableObject
import com.google.common.base.Preconditions

class RhinoSandboxImpl implements RhinoSandbox {

	var ContextFactory contextFactory
	var ScriptableObject scope

	val SandboxClassShutter classShutter

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

			context.classShutter = classShutter
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

	override RhinoSandbox allow(Class<?> clazz) {
		this.classShutter.allowedClasses.add(clazz.name)

		this
	}

	new() {
		this.classShutter = new SandboxClassShutter

	}

}