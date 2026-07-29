package delight.rhinosandox.tests;

import static org.junit.Assert.assertThrows;

import delight.rhinosandox.RhinoSandbox;
import delight.rhinosandox.RhinoSandboxes;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import org.mozilla.javascript.EcmaError;

@SuppressWarnings("all")
public class TestEvalWithGlobalScopeSecurity {

	@Test
	public void test_evalWithGlobalScope_does_not_allow_java_access() {
		final RhinoSandbox sandbox = RhinoSandboxes.create();
		sandbox.setInstructionLimit(100000);
		sandbox.setMaxDuration(5000);

		assertThrows(EcmaError.class, new ThrowingRunnable() {
			@Override
			public void run() throws Throwable {
				sandbox.evalWithGlobalScope("exploit",
					"java.lang.Runtime.getRuntime();");
			}
		});
	}
}
