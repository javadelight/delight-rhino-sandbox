package delight.rhinosandox.tests;

import org.junit.Test;

@SuppressWarnings("all")
public class TestInstanceScope {
  @Test(expected = Exception.class)
  public void test_isloated_instance_scopes() {
    throw new Error("Unresolved compilation problems:"
      + "\nInvalid number of arguments. The method eval(String, String) is not applicable for the arguments (String)"
      + "\nInvalid number of arguments. The method eval(String, String) is not applicable for the arguments (String)");
  }
  
  @Test
  public void test_global_scope() {
    throw new Error("Unresolved compilation problems:"
      + "\nInvalid number of arguments. The method evalWithGlobalScope(String, String) is not applicable for the arguments (String)"
      + "\nInvalid number of arguments. The method eval(String, String) is not applicable for the arguments (String)"
      + "\nInvalid number of arguments. The method eval(String, String) is not applicable for the arguments (String)"
      + "\nInvalid number of arguments. The method eval(String, String) is not applicable for the arguments (String)");
  }
}
