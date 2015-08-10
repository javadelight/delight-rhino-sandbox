package delight.rhinosandox

import java.util.Map
import org.mozilla.javascript.ScriptableObject

interface RhinoSandbox {

	def RhinoSandbox allow(Class<?> clazz)
	
	/**
	 * Will add a global variable available to all scripts executed with this sandbox.
	 */
	def RhinoSandbox inject(String variableName, Object object)
	
	def RhinoSandbox inject(Class<ScriptableObject> clazz) 
	
	/**
	 * Sets the maximum instructions allowed for script execution.
	 */
	def RhinoSandbox setInstructionLimit(int limit)
	
	/**
	 * Sets the maximum allowed duration for scripts.
	 */
	def RhinoSandbox setMaxDuration(int limitInMs)

	/**
	 * If .initSafeStandardObjects should be used.
	 */
	def RhinoSandbox setUseSafeStandardObjects(boolean useSafeStandardObject)

	/**
	 * Evaluate the given script with the global scope. That is all new global variables written will be available to all other scripts.
	 */
	def Object evalWithGlobalScope(String js)
	
	/**
	 * Evaluate a script with its own scope. It has access to all objects in the global scope but cannot add new ones.
	 */
	def Object eval(String js)
	
	/**
	 * <p>Evaluate a script with its own scope. It has access to all objects in the global scope but cannot add new ones.
	 * <p><code>variables</code> defines variables with Java objects which will be available for the execution of this script.
	 */
	def Object eval(String js, Map<String, Object> variables)

	
}