package delight.rhinosandox.internal;

import java.util.HashSet;
import java.util.Set;
import org.mozilla.javascript.ClassShutter;

@SuppressWarnings("all")
public class SandboxClassShutter implements ClassShutter {
  public final Set<String> allowedClasses;
  
  @Override
  public boolean visibleToScripts(final String className) {
    boolean _contains = this.allowedClasses.contains(className);
    if (_contains) {
      return true;
    } else {
      return false;
    }
  }
  
  public SandboxClassShutter() {
    HashSet<String> _hashSet = new HashSet<String>();
    this.allowedClasses = _hashSet;
  }
}
