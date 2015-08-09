package delight.rhinosandox.internal;

import delight.rhinosandox.RhinoSandbox;
import java.util.HashMap;
import java.util.Map;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

@SuppressWarnings("all")
public class RhinoSandboxImpl implements RhinoSandbox {
  private ContextFactory contextFactory;
  
  private ScriptableObject scope;
  
  public final Map<String, Object> inScope;
  
  /**
   * see https://developer.mozilla.org/en-US/docs/Mozilla/Projects/Rhino/Scopes_and_Contexts
   */
  public void assertContext() {
    throw new Error("Unresolved compilation problems:"
      + "\nType mismatch: cannot convert from Map<String, Object> to Iterable<?> | Object[]");
  }
  
  @Override
  public Object evalWithGlobalScope(final String js) {
    return null;
  }
  
  @Override
  public Object eval(final String js) {
    this.assertContext();
    try {
      final Context context = Context.enter();
      final Scriptable instanceScope = context.newObject(this.scope);
      instanceScope.setPrototype(this.scope);
      instanceScope.setParentScope(null);
      return context.evaluateString(this.scope, js, "js", 1, null);
    } finally {
      Context.exit();
    }
  }
  
  @Override
  public RhinoSandbox setInstructionLimit(final int limit) {
    return this.setInstructionLimit(limit);
  }
  
  /**
   * Sets the maximum allowed duration for scripts.
   */
  @Override
  public RhinoSandbox setMaxDuration(final int limitInMs) {
    return this.setMaxDuration(limitInMs);
  }
  
  @Override
  public RhinoSandbox inject(final Object object) {
    RhinoSandboxImpl _xblockexpression = null;
    {
      Class<?> _class = object.getClass();
      String _simpleName = _class.getSimpleName();
      this.inScope.put(_simpleName, object);
      _xblockexpression = this;
    }
    return _xblockexpression;
  }
  
  public RhinoSandboxImpl() {
    HashMap<String, Object> _hashMap = new HashMap<String, Object>();
    this.inScope = _hashMap;
  }
}
