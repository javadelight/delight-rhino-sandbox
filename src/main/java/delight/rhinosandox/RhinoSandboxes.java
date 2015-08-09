package delight.rhinosandox;

import delight.rhinosandox.RhinoSandbox;
import delight.rhinosandox.internal.RhinoSandboxImpl;

@SuppressWarnings("all")
public class RhinoSandboxes {
  public static RhinoSandbox create() {
    return new RhinoSandboxImpl();
  }
}
