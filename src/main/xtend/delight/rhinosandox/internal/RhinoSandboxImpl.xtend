package delight.rhinosandox.internal

import delight.rhinosandox.RhinoSandbox
import java.util.HashMap
import java.util.Map
import org.mozilla.javascript.Context
import org.mozilla.javascript.ContextFactory
import org.mozilla.javascript.Scriptable
import org.mozilla.javascript.ScriptableObject

class RhinoSandboxImpl implements RhinoSandbox {

	var SafeContext contextFactory
	var ScriptableObject globalScope
	var ScriptableObject safeScope
	var int instructionLimit
	var long maxDuration
	var boolean useSafeStandardObjects;

	val Map<String, Object> inScope

	var SafeClassShutter classShutter

	/**
	 * see https://developer.mozilla.org/en-US/docs/Mozilla/Projects/Rhino/Scopes_and_Contexts
	 */
	def void assertContextFactory() {
		if (contextFactory != null) {
			return
		}

		contextFactory = new SafeContext()

		if (!ContextFactory.hasExplicitGlobal) {
			ContextFactory.initGlobal(contextFactory)

		}
		contextFactory.maxInstructions = instructionLimit
		contextFactory.maxRuntimeInMs = maxDuration

		try {
			val Context context = contextFactory.enterContext
			globalScope = context.initStandardObjects(null, false)

			for (entry : inScope.entrySet) {
				globalScope.put(entry.key, globalScope, Context.toObject(entry.value, globalScope))
			}

		} finally {
			Context.exit
		}
	}

	def void assertSafeScope(Context context) {
		if (safeScope != null) {
			return
		}

		if (useSafeStandardObjects) {
			safeScope = context.initSafeStandardObjects(globalScope, true)
			return
		}

		context.classShutter = classShutter
		context.wrapFactory = new SafeWrapFactory

		safeScope = globalScope

	}

	override Object evalWithGlobalScope(String sourceName, String js) {
		assertContextFactory

		try {
			val context = contextFactory.enterContext
			return context.evaluateString(globalScope, js, sourceName, 1, null)
		} finally {
			Context.exit
		}
	}

	override Object eval(String sourceName, String js, Map<String, Object> variables) {
		assertContextFactory

		try {
			val context = contextFactory.enterContext

			assertSafeScope(context)
			// FIXME Is there a way to seal objects with the GWT libraries?
			//globalScope.sealObject
			
			// any new globals will not be available in global scope
			val Scriptable instanceScope = context.newObject(safeScope);
			instanceScope.setPrototype(safeScope);
			instanceScope.setParentScope(null);

			for (entry : variables.entrySet) {
				allow(entry.value.class)
				instanceScope.put(entry.key, instanceScope, Context.toObject(entry.value, instanceScope))
			}

			return context.evaluateString(instanceScope, js, sourceName, 1, null)

		} finally {
			Context.exit
		}
	}

	override Object eval(String sourceName, String js) {
		eval(sourceName, js, new HashMap)

	}

	override RhinoSandbox setInstructionLimit(int limit) {
		this.instructionLimit = limit

		if (contextFactory != null) {
			contextFactory.maxInstructions = instructionLimit
		}

		this
	}

	/**
	 * Sets the maximum allowed duration for scripts.
	 */
	override RhinoSandbox setMaxDuration(int limitInMs) {
		this.maxDuration = limitInMs

		if (contextFactory != null) {
			contextFactory.maxRuntimeInMs = maxDuration
		}

		this
	}

	override RhinoSandbox setUseSafeStandardObjects(boolean useSafeStandardObjects) {
		this.useSafeStandardObjects = useSafeStandardObjects
		this
	}

	override RhinoSandbox allow(Class<?> clazz) {
		this.classShutter.allowedClasses.add(clazz.name)

		// evalWithGlobalScope('importClass('+clazz.name+');')
		this
	}

	override RhinoSandbox inject(Class<ScriptableObject> clazz) {
		ScriptableObject.defineClass(globalScope, clazz)
		allow(clazz)
		this
	}

	override RhinoSandbox inject(String variableName, Object object) {
		injectInt(variableName, object)

		allow(object.class)

		this
	}

	private def void injectInt(String variableName, Object object) {
		if (this.inScope.containsKey(variableName)) {
			throw new IllegalArgumentException(
				'A variable with the name [' + variableName + '] has already been defined.')
		}
		if (contextFactory == null) {
			this.inScope.put(variableName, object)
		} else {
			try {
			contextFactory.enterContext
			globalScope.put(variableName, globalScope, Context.toObject(object, globalScope))
			
			} finally {
				Context.exit();
			}
		}
	}

	

	new() {
		this.inScope = new HashMap<String, Object>
		this.useSafeStandardObjects = false
		this.classShutter = new SafeClassShutter
	}

}