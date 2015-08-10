package delight.rhinosandox.internal

import org.mozilla.javascript.NativeJavaObject
import org.mozilla.javascript.Scriptable

class SafeNativeJavaObject extends NativeJavaObject {
	 new(Scriptable scope, Object javaObject,
                                   Class<?> staticType) {
        super(scope, javaObject, staticType)
    }

   override Object get(String name, Scriptable start) {
        if ("forName".equals(name)) {
            return NOT_FOUND;
        }
         if ("getClassLoader".equals(name)) {
         	return NOT_FOUND;
        }
        return super.get(name, start);
    }
}