package delight.rhinosandox

import java.util.Map
import org.mozilla.javascript.ScriptableObject

interface RhinoSandbox {

	/**
	 * <p>Will allow access to this class in Rhino scripts.
	 * <p>Note that for classes in packages which don't start with java., com., net. etc. the class name needs to be prefixed with Packages.
	 * <p>e.g. mypackage.Myclass will be Packages.mypackage.MyClass
	 * <p><a href='https://developer.mozilla.org/en-US/docs/Mozilla/Projects/Rhino/New_in_Rhino_1.7R1#Common_package_names_preloaded'>see</a>
	 * 
	 */
	def RhinoSandbox allow(Class<?> clazz)
	
	/**
	 * Will add a global variable available to all scripts executed with this sandbox.
	 */
	def RhinoSandbox inject(String variableName, Object object)
	
	/**
	 * Will make this class available to instantiate in Rhino scripts.
	 */
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
	 * If the global scope should be sealed (default: true).
	 */
	def RhinoSandbox setUseSealedScope(boolean value)

	/**
	 * Evaluate the given script with the global scope. That is all new global variables written will be available to all other scripts.
	 */
	def Object evalWithGlobalScope(String sourceName, String js)
	
	/**
	 * Evaluate a script with its own scope. It has access to all objects in the global scope but cannot add new ones.
	 */
	def Object eval(String sourceName, String js)
	
	/**
	 * <p>Evaluate a script with its own scope. It has access to all objects in the global scope but cannot add new ones.
	 * <p><code>variables</code> defines variables with Java objects which will be available for the execution of this script.
	 */
	def Object eval(String soureName, String js, Map<String, Object> variables)

	
}