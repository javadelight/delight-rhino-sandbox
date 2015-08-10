package delight.rhinosandox.internal;

import delight.rhinosandox.internal.SafeNativeJavaObject;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.WrapFactory;

@SuppressWarnings("all")
public class SafeWrapFactory extends WrapFactory {
  @Override
  public Scriptable wrapAsJavaObject(final Context cx, final Scriptable scope, final Object javaObject, final Class<?> staticType) {
    return new SafeNativeJavaObject(scope, javaObject, staticType);
  }
}
