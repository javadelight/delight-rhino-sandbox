package delight.rhinosandox;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;
import org.mozilla.javascript.ScriptableObject;

@SuppressWarnings("all")
public interface RhinoSandbox {
  /**
   * <p>Will allow access to this class in Rhino scripts.
   * <p>Note that for classes in packages which don't start with java., com., net. etc. the class name needs to be prefixed with Packages.
   * <p>e.g. mypackage.Myclass will be Packages.mypackage.MyClass
   * <p><a href='https://developer.mozilla.org/en-US/docs/Mozilla/Projects/Rhino/New_in_Rhino_1.7R1#Common_package_names_preloaded'>see</a>
   */
  public abstract RhinoSandbox allow(final Class<?> clazz);
  
  /**
   * Will add a global variable available to all scripts executed with this sandbox.
   */
  public abstract RhinoSandbox inject(final String variableName, final Object object);
  
  /**
   * Will make this class available to instantiate in Rhino scripts.
   */
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
   * If the global scope should be sealed (default: true).
   */
  public abstract RhinoSandbox setUseSealedScope(final boolean value);
  
  /**
   * Evaluate the given script with the global scope. That is all new global variables written will be available to all other scripts.
   */
  public abstract Object evalWithGlobalScope(final String sourceName, final String js);

  /**
   * Evaluate the given script with the global scope. That is all new global variables written will be available to all other scripts.
   */
  public abstract Object evalWithGlobalScope(final String sourceName, final Reader js) throws IOException;
  /**
   * Evaluate a script with its own scope. It has access to all objects in the global scope but cannot add new ones.
   */
  public abstract Object eval(final String sourceName, final String js);

  /**
   * Evaluate a script with its own scope. It has access to all objects in the global scope but cannot add new ones.
   */
  public abstract Object eval(final String sourceName, final Reader js) throws IOException;
  /**
   * <p>Evaluate a script with its own scope. It has access to all objects in the global scope but cannot add new ones.
   * <p><code>variables</code> defines variables with Java objects which will be available for the execution of this script.
   */
  public abstract Object eval(final String sourceName, final String js, final Map<String, Object> variables);

  /**
   * <p>Evaluate a script with its own scope. It has access to all objects in the global scope but cannot add new ones.
   * <p><code>variables</code> defines variables with Java objects which will be available for the execution of this script.
   */
  public abstract Object eval(final String sourceName, final Reader js, final Map<String, Object> variables) throws IOException;
}
