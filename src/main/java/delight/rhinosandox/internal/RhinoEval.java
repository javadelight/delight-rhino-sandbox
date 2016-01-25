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
    Object _get = args[0];
    final String script = ((String) _get);
    final String toFind = "//# sourceURL=";
    final int idx = script.indexOf(toFind);
    String scriptUrl = null;
    if ((idx != (-1))) {
      int _length = toFind.length();
      int _plus = (idx + _length);
      int _length_1 = toFind.length();
      int _plus_1 = (idx + _length_1);
      String _substring = script.substring(_plus_1);
      int _indexOf = _substring.indexOf("\n");
      String _substring_1 = script.substring(_plus, _indexOf);
      scriptUrl = _substring_1;
    }
    return cx.evaluateString(scope, script, scriptUrl, 1, null);
  }
}
