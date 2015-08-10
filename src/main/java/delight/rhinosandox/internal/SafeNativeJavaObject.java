package delight.rhinosandox.internal;

import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.mozilla.javascript.Scriptable;

@SuppressWarnings("all")
public class SafeNativeJavaObject {
  public SafeNativeJavaObject(final Scriptable scope, final Object javaObject, final Class<?> staticType) {
    throw new Error("Unresolved compilation problems:"
      + "\nInvalid number of arguments. The constructor Object() is not applicable for the arguments (Scriptable,Object,Class<?>)");
  }
  
  /* @Override
   */public Object get;
  
  private Procedure1<? super String> name;
}
