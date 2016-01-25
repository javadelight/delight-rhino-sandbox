package delight.rhinosandox.internal

import java.lang.reflect.Member
import org.mozilla.javascript.Context
import org.mozilla.javascript.FunctionObject
import org.mozilla.javascript.Scriptable

class RhinoEval extends FunctionObject {



	new(String name, Member methodOrConstructor, Scriptable scope) {
		super(name, methodOrConstructor, scope)
	}
	
	override Object call(Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
		
		val String script = args.get(0) as String
		
		val toFind = "//# sourceURL="
		
		val idx = script.lastIndexOf(toFind)
		
		var String scriptUrl = null
		if (idx != -1) {
			scriptUrl = script.substring(idx+toFind.length)
			scriptUrl = scriptUrl.replace("\n", "").replace(" ", "")
		}
		
		
		
		//println("XXXXXXX")
		//println(script)
		println(".....")
		println("scriptUrl "+scriptUrl)
		println(".....")
		
		
		return cx.evaluateString(scope, script, scriptUrl, 1, null)
		
		
		
		//return super.call(cx, scope, getParentScope(), args);
//      return super.call(cx, scope, thisObj, args);
	}
}
