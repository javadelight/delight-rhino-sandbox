package delight.rhinosandox.internal;

import delight.rhinosandox.RhinoSandbox;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeFunction;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
@SuppressWarnings("all")
public class RhinoSandboxImpl implements RhinoSandbox {
    protected SafeContext contextFactory;

    protected ScriptableObject globalScope;

    protected ScriptableObject safeScope;

    protected int instructionLimit;

    protected long maxDuration;

    protected boolean useSafeStandardObjects;

    protected boolean sealScope;

    protected final Map<String, Object> inScope;

    protected SafeClassShutter classShutter;

    protected static final Object ctxFactoryLock = new Object();

    /**
     * see https://developer.mozilla.org/en-US/docs/Mozilla/Projects/Rhino/Scopes_and_Contexts
     */
    public void assertContextFactory() {
        if ((this.contextFactory != null)) {
            return;
        }
        SafeContext _safeContext = new SafeContext();
        this.contextFactory = _safeContext;
        synchronized (RhinoSandboxImpl.ctxFactoryLock) {
            boolean _hasExplicitGlobal = ContextFactory.hasExplicitGlobal();
            boolean _not = (!_hasExplicitGlobal);
            if (_not) {
                ContextFactory.initGlobal(this.contextFactory);
            }
        }
        this.contextFactory.maxInstructions = this.instructionLimit;
        this.contextFactory.maxRuntimeInMs = this.maxDuration;
        try {
            final Context context = this.contextFactory.enterContext();
            this.globalScope = context.initStandardObjects(null, false);
            Set<Map.Entry<String, Object>> _entrySet = this.inScope.entrySet();
            for (final Map.Entry<String, Object> entry : _entrySet) {
                this.globalScope.put(entry.getKey(), this.globalScope, Context.toObject(entry.getValue(), this.globalScope));
            }
            final Class[] parameters = {String.class};
            Method dealMethod;
            try {
                dealMethod = RhinoEvalDummy.class.getMethod("eval", parameters);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (SecurityException e) {
                throw new RuntimeException(e);
            }
            RhinoEval _rhinoEval = new RhinoEval("eval", dealMethod, this.globalScope);
            this.globalScope.defineProperty("eval", _rhinoEval, ScriptableObject.DONTENUM);
        } finally {
            Context.exit();
        }

    }

    public void assertSafeScope(final Context context) {
        if ((this.safeScope != null)) {
            context.setClassShutter(this.classShutter);
            return;
        }
        if (this.useSafeStandardObjects) {
            this.safeScope = context.initSafeStandardObjects(this.globalScope, true);
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
    public Object evalWithGlobalScope(String sourceName, Reader js) throws IOException {
        this.assertContextFactory();
        try {
            final Context context = this.contextFactory.enterContext();
            return context.evaluateReader(this.globalScope, js, sourceName, 1, null);
        } finally {
            Context.exit();
        }
    }

    @Override
    public Object eval(final String sourceName, final String js, final Map<String, Object> variables) {
        return this.evalWithScope(sourceName, js, (context, instanceScope) -> {
            // Add variables to the instance scope
            Set<Map.Entry<String, Object>> _entrySet = variables.entrySet();
            for (final Map.Entry<String, Object> entry : _entrySet) {
                this.allow(entry.getValue().getClass());
                instanceScope.put(entry.getKey(), instanceScope, Context.toObject(entry.getValue(), instanceScope));
            }
            return instanceScope;
        });
    }

    /**
     * Evaluates JavaScript code with a custom Scriptable object as the execution scope.
     * This method provides more flexibility by allowing custom Scriptable objects while
     * maintaining all security features of the sandbox.
     *
     * @param sourceName the name of the source file or context
     * @param js the JavaScript code to evaluate
     * @param scriptableObject the Scriptable object to use as the execution scope
     * @return the result of the JavaScript evaluation
     */
    public Object eval(final String sourceName, final String js, final Scriptable scriptableObject) {
        return this.evalWithScope(sourceName, js, (context, instanceScope) -> {
            // Configure the scriptable object to use instanceScope as its parent
            scriptableObject.setParentScope(instanceScope);
            return scriptableObject;
        });
    }

    /**
     * Common evaluation logic that sets up the execution context and delegates
     * scope-specific configuration to a callback function.
     *
     * @param sourceName the name of the source file or context
     * @param js the JavaScript code to evaluate
     * @param scopeConfigurer callback that configures the execution scope
     * @return the result of the JavaScript evaluation
     */
    private Object evalWithScope(final String sourceName, final String js,
            final ScopeConfigurer scopeConfigurer) {
        this.assertContextFactory();
        try {
            final Context context = this.contextFactory.enterContext();
            this.assertSafeScope(context);
            if (this.sealScope) {
                this.globalScope.sealObject();
            }

            // Set up the scope chain properly
            final Scriptable instanceScope = context.newObject(this.safeScope);
            instanceScope.setPrototype(this.safeScope);
            instanceScope.setParentScope(null);

            // Let the caller configure the execution scope
            final Scriptable executionScope = scopeConfigurer.configureScope(context, instanceScope);

            return context.evaluateString(executionScope, js, sourceName, 1, null);

        } finally {
            Context.exit();
        }
    }

    /**
     * Common evaluation logic for Reader input that sets up the execution context and delegates
     * scope-specific configuration to a callback function.
     *
     * @param sourceName the name of the source file or context
     * @param js the JavaScript reader to evaluate
     * @param scopeConfigurer callback that configures the execution scope
     * @return the result of the JavaScript evaluation
     * @throws IOException if there's an error reading from the Reader
     */
    private Object evalWithScope(final String sourceName, final Reader js,
            final ScopeConfigurer scopeConfigurer) throws IOException {
        this.assertContextFactory();
        try {
            final Context context = this.contextFactory.enterContext();
            this.assertSafeScope(context);
            if (this.sealScope) {
                this.globalScope.sealObject();
            }

            // Set up the scope chain properly
            final Scriptable instanceScope = context.newObject(this.safeScope);
            instanceScope.setPrototype(this.safeScope);
            instanceScope.setParentScope(null);

            // Let the caller configure the execution scope
            final Scriptable executionScope = scopeConfigurer.configureScope(context, instanceScope);

            return context.evaluateReader(executionScope, js, sourceName, 1, null);

        } finally {
            Context.exit();
        }
    }

    /**
     * Functional interface for configuring the execution scope.
     */
    @FunctionalInterface
    private interface ScopeConfigurer {
        Scriptable configureScope(Context context, Scriptable instanceScope);
    }

    @Override
    public Object eval(final String sourceName, Reader js, Map<String, Object> variables) throws IOException {
        return this.evalWithScope(sourceName, js, (context, instanceScope) -> {
            // Add variables to the instance scope
            Set<Map.Entry<String, Object>> _entrySet = variables.entrySet();
            for (final Map.Entry<String, Object> entry : _entrySet) {
                this.allow(entry.getValue().getClass());
                instanceScope.put(entry.getKey(), instanceScope, Context.toObject(entry.getValue(), instanceScope));
            }
            return instanceScope;
        });
    }

    @Override
    public Object eval(final String sourceName, final String js) {
        return this.eval(sourceName, js, new HashMap<String, Object>());
    }

    @Override
    public Object callFunction(NativeFunction function, Object[] args) {
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
            for (final Object argument : args) {
                this.allow(argument.getClass());
            }
            return function.call(context, instanceScope, instanceScope, args);

        } finally {
            Context.exit();
        }
    }

    @Override
    public Object eval(String sourceName, Reader js) throws IOException {
        HashMap<String, Object> _hashMap = new HashMap<String, Object>();
        return this.eval(sourceName, js, _hashMap);
    }

    @Override
    public RhinoSandbox setInstructionLimit(final int limit) {
        RhinoSandboxImpl _xblockexpression = null;
        {
            this.instructionLimit = limit;
            if ((this.contextFactory != null)) {
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
            if ((this.contextFactory != null)) {
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
            this.classShutter.allowedClasses.add(clazz.getName());
            _xblockexpression = this;
        }
        return _xblockexpression;
    }

    @Override
    public RhinoSandbox inject(final Class<ScriptableObject> clazz) {
        RhinoSandboxImpl _xblockexpression = null;
        {
            try {
                ScriptableObject.<ScriptableObject>defineClass(this.globalScope, clazz);
            } catch (IllegalAccessException e) {
               throw new RuntimeException(e); 
            } catch (InstantiationException e) {
               throw new RuntimeException(e); 
            } catch (InvocationTargetException e) {
               throw new RuntimeException(e); 
            }
            this.allow(clazz);
            _xblockexpression = this;
        }
        return _xblockexpression;
       
    }

    @Override
    public RhinoSandbox inject(final String variableName, final Object object) {
        RhinoSandboxImpl _xblockexpression = null;
        {
            this.injectInt(variableName, object);
            this.allow(object.getClass());
            _xblockexpression = this;
        }
        return _xblockexpression;
    }

    protected void injectInt(final String variableName, final Object object) {
        boolean _containsKey = this.inScope.containsKey(variableName);
        if (_containsKey) {
            throw new IllegalArgumentException(
                    (("A variable with the name [" + variableName) + "] has already been defined."));
        }
        if ((this.contextFactory == null)) {
            this.inScope.put(variableName, object);
        } else {
            try {
                this.contextFactory.enterContext();
                this.globalScope.put(variableName, this.globalScope, Context.toObject(object, this.globalScope));
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
