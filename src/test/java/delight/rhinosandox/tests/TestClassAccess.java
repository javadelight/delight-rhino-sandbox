package delight.rhinosandox.tests;

import org.junit.Test;

@SuppressWarnings("all")
public class TestClassAccess {
  public static class TestEmbed {
    public void printThis(final String s) {
      TestClassAccess.value = s;
    }
  }
  
  private static String value;
  
  @Test
  public void test() {
    throw new Error("Unresolved compilation problems:"
      + "\nInvalid number of arguments. The method inject(String, Object) is not applicable for the arguments (TestEmbed)"
      + "\nType mismatch: cannot convert from TestEmbed to String");
  }
}
