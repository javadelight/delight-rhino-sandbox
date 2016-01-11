package delight.rhinosandox.tests;

import delight.rhinosandox.exceptions.ScriptCPUAbuseException;
import org.junit.Test;

@SuppressWarnings("all")
public class TestCPUViolation {
  @Test(expected = ScriptCPUAbuseException.class)
  public void test() {
    throw new Error("Unresolved compilation problems:"
      + "\nInvalid number of arguments. The method eval(String, String) is not applicable for the arguments (String)");
  }
  
  @Test
  public void test_all_okay() {
    throw new Error("Unresolved compilation problems:"
      + "\nInvalid number of arguments. The method eval(String, String) is not applicable for the arguments (String)");
  }
}
