package delight.rhinosandox.internal;

import com.google.common.base.Objects;
import delight.rhinosandox.RhinoSandbox;
import delight.rhinosandox.internal.SafeContext;
import delight.rhinosandox.internal.SandboxClassShutter;
import java.util.HashSet;
import java.util.Set;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

@SuppressWarnings("all")
public class RhinoSandboxImpl implements RhinoSandbox {
  private ContextFactory contextFactory;
  
  private ScriptableObject scope;
  
  private final SandboxClassShutter classShutter;
  
  public final Set<Object> inScope;
  
  /**
   * see https://developer.mozilla.org/en-US/docs/Mozilla/Projects/Rhino/Scopes_and_Contexts
   */
  public void assertContext() {
    boolean _notEquals = (!Objects.equal(this.contextFactory, null));
    if (_notEquals) {
      return;
    }
    SafeContext _safeContext = new SafeContext();
    this.contextFactory = _safeContext;
    ContextFactory.initGlobal(this.contextFactory);
    try {
      final Context context = this.contextFactory.enterContext();
      ScriptableObject _initSafeStandardObjects = context.initSafeStandardObjects(null, true);
      this.scope = _initSafeStandardObjects;
      for (final Object o : this.inScope) {
        Class<?> _class = o.getClass();
        String _simpleName = _class.getSimpleName();
        Scriptable _object = Context.toObject(o, this.scope);
        this.scope.put(_simpleName, this.scope, _object);
      }
    } finally {
      Context.exit();
    }
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
      context.setClassShutter(this.classShutter);
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
      this.inScope.add(object);
      _xblockexpression = this;
    }
    return _xblockexpression;
  }
  
  public RhinoSandboxImpl() {
    SandboxClassShutter _sandboxClassShutter = new SandboxClassShutter();
    this.classShutter = _sandboxClassShutter;
    HashSet<Object> _hashSet = new HashSet<Object>();
    this.inScope = _hashSet;
  }
}
