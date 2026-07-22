package delight.rhinosandox.tests;

import static org.junit.Assert.assertThrows;

import delight.rhinosandox.RhinoSandbox;
import delight.rhinosandox.RhinoSandboxes;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import org.mozilla.javascript.EcmaError;

@SuppressWarnings("all")
public class TestUseSafeStandardObjectsBypass {

    @Test
    public void test_classShutter_not_applied_with_safeStandardObjects() {
        final RhinoSandbox sandbox = RhinoSandboxes.create();
        sandbox.setUseSafeStandardObjects(true);

        assertThrows(EcmaError.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                sandbox.eval(null,
                    "(function b() {java.lang.System.out.println('hello');})()");
            }
        });
    }

    @Test
    public void test_safeWrapFactory_not_applied_with_safeStandardObjects() {
        final RhinoSandbox sandbox = RhinoSandboxes.create();
        sandbox.setUseSafeStandardObjects(true);
        sandbox.inject("myObj", new Object());

        assertThrows(EcmaError.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                sandbox.eval(null,
                    "(function() { return myObj.getClass(); })()");
            }
        });
    }
}
