package delight.rhinosandox

interface RhinoSandbox {

	/**
	 * <p>Add a new class to the list of allowed classes.
	 * <p>WARNING: Adding a new class, AFTER a script has been evaluated, will destroy the engine and recreate it. The script context will thus be lost.
	 */
	def RhinoSandbox allow(Object object)

	/**
	 * Sets the maximum instructions allowed for script execution.
	 */
	def RhinoSandbox setInstructionLimit(int limit)
	
	/**
	 * Sets the maximum allowed duration for scripts.
	 */
	def RhinoSandbox setMaxDuration(int limitInMs)

	/**
	 * Evaluate the given script with the global scope. That is all new gobal variables written will be avaialble to all other scripts.
	 */
	def Object evalWithGlobalScope(String js)
	
	/**
	 * Evaluate a script with its own scope. It has access to all objects in the global scope but cannot add new ones.
	 */
	def Object eval(String js)

	
}