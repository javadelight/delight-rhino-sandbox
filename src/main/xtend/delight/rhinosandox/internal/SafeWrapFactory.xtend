package delight.rhinosandox.internal

import org.mozilla.javascript.Context
import org.mozilla.javascript.Scriptable
import org.mozilla.javascript.WrapFactory

class SafeWrapFactory extends WrapFactory {
	override Scriptable wrapAsJavaObject(Context cx, Scriptable scope, Object javaObject, Class<?> staticType) {
        return new SafeNativeJavaObject(scope, javaObject, staticType);
    }
}