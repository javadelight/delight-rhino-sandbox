package delight.rhinosandox;

@SuppressWarnings("all")
public interface RhinoSandbox {
  /**
   * <p>Add a new class to the list of allowed classes.
   * <p>WARNING: Adding a new class, AFTER a script has been evaluated, will destroy the engine and recreate it. The script context will thus be lost.
   */
  public abstract RhinoSandbox allow(final Class<?> clazz);
  
  /**
   * Sets the maximum instructions allowed for script execution.
   */
  public abstract RhinoSandbox setInstructionLimit(final int limit);
  
  /**
   * Sets the maximum allowed duration for scripts.
   */
  public abstract RhinoSandbox setMaxDuration(final int limitInMs);
  
  /**
   * Evaluate the given script with the global scope. That is all new gobal variables written will be avaialble to all other scripts.
   */
  public abstract Object evalWithGlobalScope(final String js);
  
  /**
   * Evaluate a script with its own scope. It has access to all objects in the global scope but cannot add new ones.
   */
  public abstract Object eval(final String js);
}
