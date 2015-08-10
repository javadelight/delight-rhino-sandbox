package delight.rhinosandox.internal;

import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.Scriptable;

@SuppressWarnings("all")
public class SafeNativeJavaObject extends NativeJavaObject {
  public SafeNativeJavaObject(final Scriptable scope, final Object javaObject, final Class<?> staticType) {
    super(scope, javaObject, staticType);
  }
  
  @Override
  public Object get(final String name, final Scriptable start) {
    return super.get(name, start);
  }
}
