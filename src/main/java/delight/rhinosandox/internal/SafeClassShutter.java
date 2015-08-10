package delight.rhinosandox.internal;

import java.util.HashSet;
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
    HashSet<String> _hashSet = new HashSet<String>();
    this.allowedClasses = _hashSet;
  }
}
