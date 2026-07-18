package delight.rhinosandox.tests;

import delight.rhinosandox.RhinoSandbox;
import delight.rhinosandox.RhinoSandboxes;
import delight.rhinosandox.exceptions.ScriptCPUAbuseException;
import delight.rhinosandox.exceptions.ScriptDurationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

@SuppressWarnings("all")
public class TestNativeCallBypass {

    @Test(expected = ScriptCPUAbuseException.class)
    public void testConcatNativeCallBypassesInstructionLimit() {
        final RhinoSandbox sandbox = RhinoSandboxes.create();
        sandbox.setInstructionLimit(10000);
        sandbox.setMaxDuration(2000);

        // PoC: 28 iterations of Array.concat, each doubling the array size.
        // Produces only ~140 bytecodes total — well below the 10000 observer threshold.
        // All massive allocation work happens inside native NativeArray.js_concat,
        // so observeInstructionCount() is never called and limits never fire.
        sandbox.eval("poc",
            "var a = [0];\n" +
            "for (var i = 0; i < 28; i++) {\n" +
            " a = a.concat(a);\n" +
            "}\n"
        );

        // If we reach here, the instruction limit was bypassed.
        // The test will fail with: Expected ScriptCPUAbuseException but nothing was thrown
    }

    @Test
    public void testConcatNativeCallBypassesDurationLimit() {
        final RhinoSandbox sandbox = RhinoSandboxes.create();
        sandbox.setMaxDuration(100);
        sandbox.setInstructionLimit(0);

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

        // If we reach here, the duration limit was bypassed.
        // The test will fail with: Expected ScriptDurationException to be thrown, but nothing was thrown
    }
}
