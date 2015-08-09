package delight.rhinosandox.internal;

import com.google.common.base.Objects;
import delight.rhinosandox.RhinoSandbox;
import delight.rhinosandox.internal.SafeContext;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

@SuppressWarnings("all")
public class RhinoSandboxImpl implements RhinoSandbox {
  private SafeContext contextFactory;
  
  private ScriptableObject scope;
  
  private int instructionLimit;
  
  private long maxDuration;
  
  private final Map<String, Object> inScope;
  
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
    this.contextFactory.maxInstructions = this.instructionLimit;
    this.contextFactory.maxRuntimeInMs = this.maxDuration;
    try {
      final Context context = this.contextFactory.enterContext();
      ScriptableObject _initSafeStandardObjects = context.initSafeStandardObjects(null, true);
      this.scope = _initSafeStandardObjects;
      Set<Map.Entry<String, Object>> _entrySet = this.inScope.entrySet();
      for (final Map.Entry<String, Object> entry : _entrySet) {
        String _key = entry.getKey();
        Object _value = entry.getValue();
        Scriptable _object = Context.toObject(_value, this.scope);
        this.scope.put(_key, this.scope, _object);
      }
    } finally {
      Context.exit();
    }
  }
  
  @Override
  public Object evalWithGlobalScope(final String js) {
    this.assertContext();
    try {
      final Context context = Context.enter();
      return context.evaluateString(this.scope, js, "js", 1, null);
    } finally {
      Context.exit();
    }
  }
  
  @Override
  public Object eval(final String js, final Map<String, Object> variables) {
    this.assertContext();
    try {
      final Context context = Context.enter();
      this.scope.sealObject();
      final Scriptable instanceScope = context.newObject(this.scope);
      instanceScope.setPrototype(this.scope);
      instanceScope.setParentScope(null);
      return context.evaluateString(instanceScope, js, "js", 1, null);
    } finally {
      Context.exit();
    }
  }
  
  @Override
  public Object eval(final String js) {
    HashMap<String, Object> _hashMap = new HashMap<String, Object>();
    return this.eval(js, _hashMap);
  }
  
  @Override
  public RhinoSandbox setInstructionLimit(final int limit) {
    RhinoSandboxImpl _xblockexpression = null;
    {
      this.instructionLimit = limit;
      boolean _notEquals = (!Objects.equal(this.contextFactory, null));
      if (_notEquals) {
        this.contextFactory.maxInstructions = this.instructionLimit;
      }
      _xblockexpression = this;
    }
    return _xblockexpression;
  }
  
  /**
   * Sets the maximum allowed duration for scripts.
   */
  @Override
  public RhinoSandbox setMaxDuration(final int limitInMs) {
    RhinoSandboxImpl _xblockexpression = null;
    {
      this.maxDuration = limitInMs;
      boolean _notEquals = (!Objects.equal(this.contextFactory, null));
      if (_notEquals) {
        this.contextFactory.maxRuntimeInMs = this.maxDuration;
      }
      _xblockexpression = this;
    }
    return _xblockexpression;
  }
  
  @Override
  public RhinoSandbox inject(final String variableName, final Object object) {
    RhinoSandboxImpl _xblockexpression = null;
    {
      boolean _containsKey = this.inScope.containsKey(variableName);
      if (_containsKey) {
        throw new IllegalArgumentException(
          (("A variable with the name [" + variableName) + "] has already been defined."));
      }
      this.inScope.put(variableName, object);
      _xblockexpression = this;
    }
    return _xblockexpression;
  }
  
  public RhinoSandboxImpl() {
    HashMap<String, Object> _hashMap = new HashMap<String, Object>();
    this.inScope = _hashMap;
  }
}
