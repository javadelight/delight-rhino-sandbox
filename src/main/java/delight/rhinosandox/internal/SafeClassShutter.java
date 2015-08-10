package delight.rhinosandox.internal;

import java.util.Set;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.mozilla.javascript.ClassShutter;

@SuppressWarnings("all")
public class SafeClassShutter implements ClassShutter {
  public final Set<String> allowedClasses;
  
  @Override
  public boolean visibleToScripts(final String fullClassName) {
    InputOutput.<String>println(((("test " + fullClassName) + " has ") + this.allowedClasses));
    return this.allowedClasses.contains(fullClassName);
  }
  
  public SafeClassShutter() {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method allowedClassed is undefined for the type SafeClassShutter"
      + "\nThe syntax for type literals is typeof(EcmaError) or EcmaError."
      + "\nadd cannot be resolved");
  }
}
