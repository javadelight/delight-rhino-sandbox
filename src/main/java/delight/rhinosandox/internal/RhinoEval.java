package delight.rhinosandox.internal;

import java.lang.reflect.Member;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.FunctionObject;
import org.mozilla.javascript.Scriptable;

@SuppressWarnings("all")
public class RhinoEval extends FunctionObject {
  public RhinoEval(final String name, final Member methodOrConstructor, final Scriptable scope) {
    super(name, methodOrConstructor, scope);
  }
  
  @Override
  public Object call(final Context cx, final Scriptable scope, final Scriptable thisObj, final Object[] args) {
    final String script = args[0].toString();
    final String toFind = "//# sourceURL=";
    final int idx = script.lastIndexOf(toFind);
    String scriptUrl = null;
    if ((idx != (-1))) {
      int _length = toFind.length();
      int _plus = (idx + _length);
      scriptUrl = script.substring(_plus);
      scriptUrl = scriptUrl.replace("\n", "").replace(" ", "");
    }
    return cx.evaluateString(scope, script, scriptUrl, 1, null);
  }
}
