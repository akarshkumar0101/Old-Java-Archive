package math;

import java.util.HashMap;
import java.util.Map;

import math.functions.AddFunction;
import math.functions.ExpFunction;
import math.functions.MulFunction;

public abstract class Function extends Node {

	public static final char ADD = '+', MUL = '*', EXP = '^';

	public static final Map<String, Class<? extends Function>> funcClasses;

	static {
		funcClasses = new HashMap<>();
	}

	public static boolean registerFunction(String funcName, Class<? extends Function> FuncClass) {
		if (funcClasses.containsKey(funcName))
			return false;
		else {
			funcClasses.put(funcName, FuncClass);
			return true;
		}
	}

	public Function(Node parent, Node... children) {
		super(parent, children);
	}

	public static boolean isBasicFunction(char c) {
		return c == ADD || c == MUL || c == EXP;
	}

	public static boolean isFunction(String str) {
		return funcClasses.containsKey(str);
	}

	public static Function createBasicFunction(char c, Node a, Node b) {
		if (c == ADD)
			return new AddFunction(null, a, b);
		else if (c == MUL)
			return new MulFunction(null, a, b);
		else if (c == EXP)
			return new ExpFunction(null, a, b);
		return null;
	}

	public static Function createFunction(String str, Node argument) {
		
		return null;
	}

}
