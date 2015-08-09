package delight.rhinosandox.internal;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import delight.rhinosandox.RhinoSandbox;
import delight.rhinosandox.internal.SafeContext;
import delight.rhinosandox.internal.SandboxClassShutter;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

@SuppressWarnings("all")
public class RhinoSandboxImpl implements RhinoSandbox {
  private ContextFactory contextFactory;
  
  private Context context;
  
  private ScriptableObject scope;
  
  private final SandboxClassShutter classShutter;
  
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
    ScriptableObject _initSafeStandardObjects = this.context.initSafeStandardObjects(null, true);
    this.scope = _initSafeStandardObjects;
  }
  
  @Override
  public Object evalWithGlobalScope(final String js) {
    return null;
  }
  
  @Override
  public Object eval(final String js) {
    boolean _isSealed = this.scope.isSealed();
    boolean _not = (!_isSealed);
    if (_not) {
      this.scope.sealObject();
      boolean _isSealed_1 = this.scope.isSealed();
      Preconditions.checkState(_isSealed_1);
    }
    try {
      this.contextFactory.enterContext();
      final Scriptable instanceScope = this.context.newObject(this.scope);
      instanceScope.setPrototype(this.scope);
      instanceScope.setParentScope(null);
    } finally {
      this.contextFactory.exit();
    }
    return null;
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
  public RhinoSandbox allow(final Class<?> clazz) {
    RhinoSandboxImpl _xblockexpression = null;
    {
      String _name = clazz.getName();
      this.classShutter.allowedClasses.add(_name);
      _xblockexpression = this;
    }
    return _xblockexpression;
  }
  
  public RhinoSandboxImpl() {
    SandboxClassShutter _sandboxClassShutter = new SandboxClassShutter();
    this.classShutter = _sandboxClassShutter;
  }
}
