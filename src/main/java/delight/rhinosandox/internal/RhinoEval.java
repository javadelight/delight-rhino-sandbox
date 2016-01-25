package delight.rhinosandox.internal;

import java.lang.reflect.Member;
import org.eclipse.xtext.xbase.lib.InputOutput;
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
    final int idx = script.lastIndexOf(toFind);
    String scriptUrl = null;
    if ((idx != (-1))) {
      int _length = toFind.length();
      int _plus = (idx + _length);
      String _substring = script.substring(_plus);
      scriptUrl = _substring;
    }
    InputOutput.<String>println("XXXXXXX");
    InputOutput.<String>println(script);
    InputOutput.<String>println(".....");
    InputOutput.<String>println(("scriptUrl " + scriptUrl));
    InputOutput.<String>println(".....");
    int _indexOf = script.indexOf("\\n");
    String _plus_1 = ("index " + Integer.valueOf(_indexOf));
    InputOutput.<String>println(_plus_1);
    return cx.evaluateString(scope, script, scriptUrl, 1, null);
  }
}
