package math;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;

public abstract class Function implements Calculable {

	/**
	 * The Name-Function Map for creating functions from keywords
	 */
	private static final HashMap<String, Constructor<? extends Function>> NFM = new HashMap<String, Constructor<? extends Function>>();

	// static class FunctionSubClassInfo

	public static <C extends Function> void registerFunctionClass(Class<C> functionClass) {
		try {
			Field f = functionClass.getField("FUNCNAME");
			String functName = (String) f.get(null);
			Constructor<C> cnstr = functionClass.getConstructor(Expression.class);
			NFM.put(functName, cnstr);
			System.out.println("Successfully loaded function class");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Cant load function class");
		}
	}

	@Override
	public String toString() {
		return "hello";
	}
}