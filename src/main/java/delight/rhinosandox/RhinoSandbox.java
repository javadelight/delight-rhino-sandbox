package delight.rhinosandox;

import java.util.Map;
import org.mozilla.javascript.ScriptableObject;

@SuppressWarnings("all")
public interface RhinoSandbox {
  public abstract RhinoSandbox allow(final Class<?> clazz);
  
  /**
   * Will add a global variable available to all scripts executed with this sandbox.
   */
  public abstract RhinoSandbox inject(final String variableName, final Object object);
  
  public abstract RhinoSandbox inject(final Class<ScriptableObject> clazz);
  
  /**
   * Sets the maximum instructions allowed for script execution.
   */
  public abstract RhinoSandbox setInstructionLimit(final int limit);
  
  /**
   * Sets the maximum allowed duration for scripts.
   */
  public abstract RhinoSandbox setMaxDuration(final int limitInMs);
  
  /**
   * If .initSafeStandardObjects should be used.
   */
  public abstract RhinoSandbox setUseSafeStandardObjects(final boolean useSafeStandardObject);
  
  /**
   * Evaluate the given script with the global scope. That is all new global variables written will be available to all other scripts.
   */
  public abstract Object evalWithGlobalScope(final String js);
  
  /**
   * Evaluate a script with its own scope. It has access to all objects in the global scope but cannot add new ones.
   */
  public abstract Object eval(final String js);
  
  /**
   * <p>Evaluate a script with its own scope. It has access to all objects in the global scope but cannot add new ones.
   * <p><code>variables</code> defines variables with Java objects which will be available for the execution of this script.
   */
  public abstract Object eval(final String js, final Map<String, Object> variables);
}
