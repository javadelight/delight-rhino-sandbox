package delight.rhinosandox.internal;

import com.google.common.base.Objects;
import delight.rhinosandox.RhinoSandbox;
import delight.rhinosandox.internal.SafeContext;
import delight.rhinosandox.internal.SandboxClassShutter;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.ScriptableObject;

@SuppressWarnings("all")
public class RhinoSandboxImpl implements RhinoSandbox {
  private ContextFactory contextFactory;
  
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
    try {
      final Context context = this.contextFactory.enterContext();
      ScriptableObject _initSafeStandardObjects = context.initSafeStandardObjects(null, true);
      this.scope = _initSafeStandardObjects;
    } finally {
      Context.exit();
    }
  }
}
