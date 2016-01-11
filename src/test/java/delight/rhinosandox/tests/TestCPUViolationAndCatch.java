package delight.rhinosandox.tests;

import delight.rhinosandox.exceptions.ScriptCPUAbuseException;
import org.junit.Test;

@SuppressWarnings("all")
public class TestCPUViolationAndCatch {
  @Test(expected = ScriptCPUAbuseException.class)
  public void test_catch() {
    throw new Error("Unresolved compilation problems:"
      + "\nInvalid number of arguments. The method eval(String, String) is not applicable for the arguments (String)");
  }
}
