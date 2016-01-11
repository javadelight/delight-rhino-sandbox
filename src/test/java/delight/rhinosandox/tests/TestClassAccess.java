package delight.rhinosandox.tests;

import org.junit.Test;

@SuppressWarnings("all")
public class TestClassAccess {
  public static class TestEmbed {
    public String value;
    
    public void setValue(final String s) {
      this.value = s;
    }
  }
  
  @Test
  public void test_access_allowed() {
    throw new Error("Unresolved compilation problems:"
      + "\nInvalid number of arguments. The method eval(String, String) is not applicable for the arguments (String)");
  }
  
  @Test
  public void test_java_variable() {
    throw new Error("Unresolved compilation problems:"
      + "\nInvalid number of arguments. The method eval(String, String) is not applicable for the arguments (String)");
  }
  
  @Test(expected = Exception.class)
  public void test_system_out_forbidden() {
    throw new Error("Unresolved compilation problems:"
      + "\nInvalid number of arguments. The method eval(String, String) is not applicable for the arguments (String)");
  }
}
