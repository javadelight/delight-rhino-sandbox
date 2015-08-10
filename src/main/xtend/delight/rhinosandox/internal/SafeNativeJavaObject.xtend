package delight.rhinosandox.internal

import org.mozilla.javascript.Scriptable

class SafeNativeJavaObject {
	 new(Scriptable scope, Object javaObject,
                                   Class<?> staticType) {
        super(scope, javaObject, staticType)
    }

    @Override
    public Object get(String name, Scriptable start) {
       // if ("getClass".equals(name)) {
       //     return NOT_FOUND;
      //  }
        return super.get(name, start);
    }
}