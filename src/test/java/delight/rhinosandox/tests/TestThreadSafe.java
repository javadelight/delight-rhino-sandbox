package delight.rhinosandox.tests;

import delight.rhinosandox.RhinoSandbox;
import delight.rhinosandox.RhinoSandboxes;
import delight.rhinosandox.exceptions.ScriptDurationException;
import java.util.concurrent.CountDownLatch;
import org.junit.Assert;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

public class TestThreadSafe {
    // In case of ThreadSafe failure, the test will run forever. So, limiting the test to 5 secs
    @Test(timeout = 5000)
    public void multiThreadingFail() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(2);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                RhinoSandbox rhinoSandbox = RhinoSandboxes.create();
                String jsCode = "(function a() {return \"test\";})()";
                rhinoSandbox.setMaxDuration(0);
                rhinoSandbox.setInstructionLimit(0);
                rhinoSandbox.eval(null, jsCode);
                latch.countDown();
            }
        });
        t1.setName("Thread 1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                final RhinoSandbox rhinoSandbox = RhinoSandboxes.create();
                final String jsCode = "(function a() {while(true) { }})()";
                rhinoSandbox.setMaxDuration(500);
                rhinoSandbox.setInstructionLimit(0);
                Assert.assertThrows(ScriptDurationException.class, new ThrowingRunnable() {
                    @Override
                    public void run() throws Throwable {
                        rhinoSandbox.eval(null, jsCode);
                    }
                });
                latch.countDown();
            }
        });
        t2.setName("Thread 2");

        t1.start();
        Thread.sleep(1000L);
        t2.start();

        latch.await();
    }
}
