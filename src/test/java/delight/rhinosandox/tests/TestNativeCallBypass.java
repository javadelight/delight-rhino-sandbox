package delight.rhinosandox.tests;

import delight.rhinosandox.RhinoSandbox;
import delight.rhinosandox.RhinoSandboxes;
import delight.rhinosandox.exceptions.ScriptDurationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

@SuppressWarnings("all")
public class TestNativeCallBypass {

    @Test
    public void testDurationLimitWithWatchdog() {
        final RhinoSandbox sandbox = RhinoSandboxes.create();
        sandbox.setMaxDuration(2000);
        sandbox.setInstructionLimit(10000);
        sandbox.allow(String.class);
        sandbox.allow(Integer.class);

        Assert.assertThrows(ScriptDurationException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                sandbox.eval("poc",
                    "var a = [0];\n" +
                    "for (var i = 0; i < 28; i++) {\n" +
                    " a = a.concat(a);\n" +
                    "}\n"
                );
            }
        });
    }
}
