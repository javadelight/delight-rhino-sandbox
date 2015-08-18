package delight.rhinosandox.internal

import org.mozilla.javascript.Callable
import org.mozilla.javascript.Context
import org.mozilla.javascript.ContextFactory
import org.mozilla.javascript.Scriptable
import delight.rhinosandox.exceptions.ScriptDurationException
import delight.rhinosandox.exceptions.ScriptCPUAbuseException

/**
 * see http://www-archive.mozilla.org/rhino/apidocs/org/mozilla/javascript/ContextFactory.html
 * 
 * Also see https://github.com/flozano/rhino-sandbox-test/blob/master/src/main/java/com/flozano/rhino/sandbox/SandboxContextFactory.java
 */
class SafeContext extends ContextFactory {

	final static int INSTRUCTION_STEPS = 10000;

    public var long maxRuntimeInMs
    public var int maxInstructions

	static class CountContext extends Context {
		long startTime
		long instructions
	}

	override Context makeContext() {
		val CountContext cx = new CountContext()

		cx.setOptimizationLevel(-1)
		cx.setInstructionObserverThreshold(INSTRUCTION_STEPS)

		return cx;
	}

	override boolean hasFeature(Context cx, int featureIndex) {
		// Turn on maximum compatibility with MSIE scripts
		switch (featureIndex) {
			case Context.FEATURE_NON_ECMA_GET_YEAR: return true
			case Context.FEATURE_MEMBER_EXPR_AS_FUNCTION_NAME: return true
			case Context.FEATURE_RESERVED_KEYWORD_AS_IDENTIFIER: return true
			case Context.FEATURE_PARENT_PROTO_PROPERTIES: return false
		}
		return super.hasFeature(cx, featureIndex);
	}

	override void observeInstructionCount(Context cx, int instructionCount) {
		val CountContext mcx = cx as CountContext;
		val long currentTime = System.currentTimeMillis();
		if (maxRuntimeInMs > 0 && currentTime - mcx.startTime > maxRuntimeInMs) {
			throw new ScriptDurationException
		}
		
		mcx.instructions = mcx.instructions + INSTRUCTION_STEPS
		
		if (maxInstructions > 0 && mcx.instructions > maxInstructions) {
			
			throw new ScriptCPUAbuseException
		}
	}

	override Object doTopCall(Callable callable, Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
		val CountContext mcx = cx as CountContext;
		mcx.startTime = System.currentTimeMillis();
		mcx.instructions = 0
		return super.doTopCall(callable, cx, scope, thisObj, args);
	}
	
	
	
}