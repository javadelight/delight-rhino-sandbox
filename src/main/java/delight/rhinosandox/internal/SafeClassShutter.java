package delight.rhinosandox.internal;

import java.util.HashSet;
import java.util.Set;
import org.mozilla.javascript.ClassShutter;
import org.mozilla.javascript.EcmaError;

@SuppressWarnings("all")
public class SafeClassShutter implements ClassShutter {
  public final Set<String> allowedClasses;
  
  @Override
  public boolean visibleToScripts(final String fullClassName) {
    return this.allowedClasses.contains(fullClassName);
  }
  
  public SafeClassShutter() {
    HashSet<String> _hashSet = new HashSet<String>();
    this.allowedClasses = _hashSet;
    this.allowedClasses.add("adapter1");
    this.allowedClasses.add("adapter2");
    this.allowedClasses.add("adapter3");
    this.allowedClasses.add("adapter4");
    String _name = EcmaError.class.getName();
    this.allowedClasses.add(_name);
  }
}
