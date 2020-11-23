package delight.rhinosandox.tests;

import static org.junit.Assert.assertThrows;

import delight.rhinosandox.RhinoSandbox;
import delight.rhinosandox.RhinoSandboxes;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import org.mozilla.javascript.EcmaError;

public class TestClassShutter {
    @Test
    public void javaTestFails() {
        final RhinoSandbox rhinoSandbox = RhinoSandboxes.create();
        String jsCode1 = "(function a() {var obj = {x:34,y:100}; return obj;})()";
        rhinoSandbox.eval(null, jsCode1);

        assertThrows(EcmaError.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                String jsCode2 = "(function b() {java.lang.System.out.println('hello');})()";
                rhinoSandbox.eval(null, jsCode2);
            }
        });
    }

    @Test
    public void javaTestPasses() {
        final RhinoSandbox rhinoSandbox = RhinoSandboxes.create();
        assertThrows(EcmaError.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                String jsCode2 = "(function b() {java.lang.System.out.println('hello');})()";
                System.out.println(rhinoSandbox.eval(null, jsCode2));
            }
        });
    }
}
