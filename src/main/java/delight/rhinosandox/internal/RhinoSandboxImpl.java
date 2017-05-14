package delight.rhinosandox.internal;

import com.google.common.base.Objects;
import delight.rhinosandox.RhinoSandbox;
import delight.rhinosandox.internal.RhinoEval;
import delight.rhinosandox.internal.RhinoEvalDummy;
import delight.rhinosandox.internal.SafeClassShutter;
import delight.rhinosandox.internal.SafeContext;
import delight.rhinosandox.internal.SafeWrapFactory;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

@SuppressWarnings("all")
public class RhinoSandboxImpl implements RhinoSandbox {
  private SafeContext contextFactory;
  
  private ScriptableObject globalScope;
  
  private ScriptableObject safeScope;
  
  private int instructionLimit;
  
  private long maxDuration;
  
  private boolean useSafeStandardObjects;
  
  private boolean sealScope;
  
  private final Map<String, Object> inScope;
  
  private SafeClassShutter classShutter;
  
  /**
   * see https://developer.mozilla.org/en-US/docs/Mozilla/Projects/Rhino/Scopes_and_Contexts
   */
  public void assertContextFactory() {
    try {
      boolean _notEquals = (!Objects.equal(this.contextFactory, null));
      if (_notEquals) {
        return;
      }
      SafeContext _safeContext = new SafeContext();
      this.contextFactory = _safeContext;
      boolean _hasExplicitGlobal = ContextFactory.hasExplicitGlobal();
      boolean _not = (!_hasExplicitGlobal);
      if (_not) {
        ContextFactory.initGlobal(this.contextFactory);
      }
      this.contextFactory.maxInstructions = this.instructionLimit;
      this.contextFactory.maxRuntimeInMs = this.maxDuration;
      try {
        final Context context = this.contextFactory.enterContext();
        ScriptableObject _initStandardObjects = context.initStandardObjects(null, false);
        this.globalScope = _initStandardObjects;
        Set<Map.Entry<String, Object>> _entrySet = this.inScope.entrySet();
        for (final Map.Entry<String, Object> entry : _entrySet) {
          String _key = entry.getKey();
          Object _value = entry.getValue();
          Scriptable _object = Context.toObject(_value, this.globalScope);
          this.globalScope.put(_key, this.globalScope, _object);
        }
        final Class[] parameters = { String.class };
        final Method dealMethod = RhinoEvalDummy.class.getMethod("eval", parameters);
        RhinoEval _rhinoEval = new RhinoEval("eval", dealMethod, this.globalScope);
        this.globalScope.defineProperty("eval", _rhinoEval, ScriptableObject.DONTENUM);
      } finally {
        Context.exit();
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public void assertSafeScope(final Context context) {
    boolean _notEquals = (!Objects.equal(this.safeScope, null));
    if (_notEquals) {
      return;
    }
    if (this.useSafeStandardObjects) {
      ScriptableObject _initSafeStandardObjects = context.initSafeStandardObjects(this.globalScope, true);
      this.safeScope = _initSafeStandardObjects;
      return;
    }
    context.setClassShutter(this.classShutter);
    SafeWrapFactory _safeWrapFactory = new SafeWrapFactory();
    context.setWrapFactory(_safeWrapFactory);
    this.safeScope = this.globalScope;
  }
  
  @Override
  public Object evalWithGlobalScope(final String sourceName, final String js) {
    this.assertContextFactory();
    try {
      final Context context = this.contextFactory.enterContext();
      return context.evaluateString(this.globalScope, js, sourceName, 1, null);
    } finally {
      Context.exit();
    }
  }
  
  @Override
  public Object eval(final String sourceName, final String js, final Map<String, Object> variables) {
    this.assertContextFactory();
    try {
      final Context context = this.contextFactory.enterContext();
      this.assertSafeScope(context);
      if (this.sealScope) {
        this.globalScope.sealObject();
      }
      final Scriptable instanceScope = context.newObject(this.safeScope);
      instanceScope.setPrototype(this.safeScope);
      instanceScope.setParentScope(null);
      Set<Map.Entry<String, Object>> _entrySet = variables.entrySet();
      for (final Map.Entry<String, Object> entry : _entrySet) {
        {
          Object _value = entry.getValue();
          Class<?> _class = _value.getClass();
          this.allow(_class);
          String _key = entry.getKey();
          Object _value_1 = entry.getValue();
          Scriptable _object = Context.toObject(_value_1, instanceScope);
          instanceScope.put(_key, instanceScope, _object);
        }
      }
      return context.evaluateString(instanceScope, js, sourceName, 1, null);
    } finally {
      Context.exit();
    }
  }
  
  @Override
  public Object eval(final String sourceName, final String js) {
    HashMap<String, Object> _hashMap = new HashMap<String, Object>();
    return this.eval(sourceName, js, _hashMap);
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
  public RhinoSandbox setUseSafeStandardObjects(final boolean useSafeStandardObjects) {
    RhinoSandboxImpl _xblockexpression = null;
    {
      this.useSafeStandardObjects = useSafeStandardObjects;
      _xblockexpression = this;
    }
    return _xblockexpression;
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
  
  @Override
  public RhinoSandbox inject(final Class<ScriptableObject> clazz) {
    try {
      RhinoSandboxImpl _xblockexpression = null;
      {
        ScriptableObject.<ScriptableObject>defineClass(this.globalScope, clazz);
        this.allow(clazz);
        _xblockexpression = this;
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Override
  public RhinoSandbox inject(final String variableName, final Object object) {
    RhinoSandboxImpl _xblockexpression = null;
    {
      this.injectInt(variableName, object);
      Class<?> _class = object.getClass();
      this.allow(_class);
      _xblockexpression = this;
    }
    return _xblockexpression;
  }
  
  private void injectInt(final String variableName, final Object object) {
    boolean _containsKey = this.inScope.containsKey(variableName);
    if (_containsKey) {
      throw new IllegalArgumentException(
        (("A variable with the name [" + variableName) + "] has already been defined."));
    }
    boolean _equals = Objects.equal(this.contextFactory, null);
    if (_equals) {
      this.inScope.put(variableName, object);
    } else {
      try {
        this.contextFactory.enterContext();
        Scriptable _object = Context.toObject(object, this.globalScope);
        this.globalScope.put(variableName, this.globalScope, _object);
      } finally {
        Context.exit();
      }
    }
  }
  
  public RhinoSandboxImpl() {
    HashMap<String, Object> _hashMap = new HashMap<String, Object>();
    this.inScope = _hashMap;
    this.useSafeStandardObjects = false;
    this.sealScope = true;
    SafeClassShutter _safeClassShutter = new SafeClassShutter();
    this.classShutter = _safeClassShutter;
  }
  
  @Override
  public RhinoSandbox setUseSealedScope(final boolean value) {
    this.sealScope = value;
    return this;
  }
}
