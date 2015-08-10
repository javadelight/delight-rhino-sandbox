package delight.rhinosandox.internal;

import org.mozilla.javascript.ClassShutter;

@SuppressWarnings("all")
public class SafeClassShutter implements ClassShutter {
  @Override
  public boolean visibleToScripts(final String fullClassName) {
    return false;
  }
}
