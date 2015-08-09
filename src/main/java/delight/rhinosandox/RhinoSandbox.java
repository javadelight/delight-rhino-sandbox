package delight.rhinosandox;

import java.util.Map;

@SuppressWarnings("all")
public interface RhinoSandbox {
  /**
   * Will add a global variable available to all scripts executed with this sandbox.
   */
  public abstract RhinoSandbox inject(final String variableName, final Object object);
  
  /**
   * Sets the maximum instructions allowed for script execution.
   */
  public abstract RhinoSandbox setInstructionLimit(final int limit);
  
  /**
   * Sets the maximum allowed duration for scripts.
   */
  public abstract RhinoSandbox setMaxDuration(final int limitInMs);
  
  /**
   * Evaluate the given script with the global scope. That is all new global variables written will be available to all other scripts.
   */
  public abstract Object evalWithGlobalScope(final String js);
  
  /**
   * Evaluate a script with its own scope. It has access to all objects in the global scope but cannot add new ones.
   */
  public abstract Object eval(final String js);
  
  /**
   * Evaluate a script with its own scope. It has access to all objects in the global scope but cannot add new ones.
   */
  public abstract Object eval(final String js, final Map<String, Object> variables);
}
