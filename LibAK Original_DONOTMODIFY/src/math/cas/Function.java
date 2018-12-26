package math.cas;

import java.lang.reflect.Constructor;
import java.util.HashMap;

import math.cas.function.basicfunction.BasicFunction;
import math.cas.function.trigfunction.TrigonometricFunction;

public abstract class Function extends Entity {

	public static final HashMap<String, Class<? extends Function>> functionNames = new HashMap<>();

	protected final Entity[] parameters;

	public static void registerFunction(String funcName, Class<? extends Function> funcClass) {
		functionNames.put(funcName, funcClass);
	}

	// protected Function(Parameter parameter) {
	// this.parameter = parameter;
	// }

	public Function(Entity... parameters) {
		super(parameters);
		this.parameters = parameters;
	}

	public static boolean isFunction(String funcName) {
		return functionNames.keySet().contains(funcName);
	}

	public static Function createFunction(String funcName, Entity... parameters) {
		try {
			Constructor<? extends Function> cons = functionNames.get(funcName).getConstructor(Entity[].class);
			Object param = parameters;
			Function function = cons.newInstance(param);
			return function;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static boolean alreadyRegistered = false;

	public static void registerKnownFunctions() {
		if (!alreadyRegistered) {
			BasicFunction.registerAllFunctions();
			TrigonometricFunction.registerAllFunctions();
			alreadyRegistered = true;
		}
	}

	@Override
	public boolean equals(Object another) {

		if (getClass() != another.getClass())
			return false;
		Function func = (Function) another;
		if (parameters.length != func.parameters.length)
			return false;
		for (int i = 0; i < parameters.length; i++) {
			if (!parameters[i].equals(func.parameters[i]))
				return false;
		}
		return true;
	}

	/*
	 * Consolidates the parameters of the function and returns the same function
	 * 
	 */
	@Override
	public Entity consolidate() {
		String funcString = getFunctionString(this.getClass());
		Entity[] newparams = new Entity[parameters.length];
		boolean allConstants = true;
		for (int i = 0; i < newparams.length; i++) {
			newparams[i] = parameters[i].consolidate();
			if (!newparams[i].isConstant()) {
				allConstants = false;
			}
		}
		if (allConstants) {
			// TODO DO SOMETHING
		}

		return createFunction(funcString, newparams);
	}

	public static String getFunctionString(Class<? extends Function> funcClass) {
		for (String s : functionNames.keySet()) {
			if (funcClass.equals(functionNames.get(s)))
				return s;
		}
		return null;
	}

	@Override
	public String toString() {
		String str = "";
		str += getFunctionString(this.getClass());
		str += "(";
		for (int i = 0; i < parameters.length; i++) {
			str += parameters[i];
			if (i != parameters.length - 1) {
				str += ", ";
			}
		}
		str += ")";
		return str;
	}
}
