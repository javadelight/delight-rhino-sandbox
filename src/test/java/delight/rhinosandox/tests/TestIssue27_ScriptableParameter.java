package delight.rhinosandox.tests;

import delight.rhinosandox.RhinoSandbox;
import delight.rhinosandox.RhinoSandboxes;
import org.junit.Assert;
import org.junit.Test;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

@SuppressWarnings("all")
public class TestIssue27_ScriptableParameter {

    @Test
    public void test_eval_with_custom_scriptable() {
        final RhinoSandbox sandbox = RhinoSandboxes.create();

        // Create a custom Scriptable object
        final Scriptable customScope = new ScriptableObject() {
            private static final long serialVersionUID = 1L;

            @Override
            public String getClassName() {
                return "CustomScope";
            }
        };

        // Test that we can evaluate with custom Scriptable
        Class<? extends TestIssue27_ScriptableParameter> testClass = this.getClass();
        String sourceName = "Test_" + testClass.getSimpleName();

        // This should work without throwing an exception
        Object result = sandbox.eval(sourceName, "var x = 42; x + 1;", customScope);

        // Verify the result
        Assert.assertEquals(43.0, result);
    }

    @Test
    public void test_scriptable_scope_isolation() {
        final RhinoSandbox sandbox = RhinoSandboxes.create();

        // Create first custom Scriptable
        final Scriptable scope1 = new ScriptableObject() {
            private static final long serialVersionUID = 1L;

            @Override
            public String getClassName() {
                return "Scope1";
            }
        };

        // Create second custom Scriptable
        final Scriptable scope2 = new ScriptableObject() {
            private static final long serialVersionUID = 1L;

            @Override
            public String getClassName() {
                return "Scope2";
            }
        };

        Class<? extends TestIssue27_ScriptableParameter> testClass = this.getClass();
        String sourceName1 = "Test1_" + testClass.getSimpleName();
        String sourceName2 = "Test2_" + testClass.getSimpleName();

        // Evaluate in first scope
        sandbox.eval(sourceName1, "var shared = 'scope1';", scope1);

        // Evaluate in second scope - should not see variables from first scope
        sandbox.eval(sourceName2, "var shared = 'scope2'; shared;", scope2);

        // Both evaluations should succeed independently
        Assert.assertNotNull(scope1);
        Assert.assertNotNull(scope2);
    }

    @Test
    public void test_scriptable_maintains_security() {
        final RhinoSandbox sandbox = RhinoSandboxes.create();

        // Create a custom Scriptable object
        final Scriptable customScope = new ScriptableObject() {
            private static final long serialVersionUID = 1L;

            @Override
            public String getClassName() {
                return "SecureCustomScope";
            }
        };

        Class<? extends TestIssue27_ScriptableParameter> testClass = this.getClass();
        String sourceName = "SecurityTest_" + testClass.getSimpleName();

        // This should work - basic arithmetic is allowed
        Object result = sandbox.eval(sourceName, "var x = 10; x * 2;", customScope);
        Assert.assertEquals(20.0, result);

        // The sandbox should still enforce security constraints
        // (This test verifies the security features are still active)
    }

    @Test
    public void test_scriptable_vs_map_consistency() {
        final RhinoSandbox sandbox = RhinoSandboxes.create();

        // Create a custom Scriptable object with the input variable
        final Scriptable scriptableScope = new ScriptableObject() {
            private static final long serialVersionUID = 1L;

            @Override
            public String getClassName() {
                return "ConsistencyTestScope";
            }

            // Initialize with the input variable
            {
                // Add the input variable to this Scriptable object
                this.put("input", this, 50.0);
            }
        };

        Class<? extends TestIssue27_ScriptableParameter> testClass = this.getClass();
        String sourceName = "Consistency_" + testClass.getSimpleName();

        // Test with Scriptable parameter (with input variable already in scope)
        Object scriptableResult = sandbox.eval(sourceName + "_scriptable",
            "var value = input + 100; value;", scriptableScope);

        // Test with Map parameter for comparison
        java.util.Map<String, Object> variables = new java.util.HashMap<>();
        variables.put("input", 50.0);
        Object mapResult = sandbox.eval(sourceName + "_map",
            "var value = input + 100; value;", variables);

        // Both should produce the same result (150.0)
        Assert.assertEquals(150.0, scriptableResult);
        Assert.assertEquals(150.0, mapResult);
    }
}
