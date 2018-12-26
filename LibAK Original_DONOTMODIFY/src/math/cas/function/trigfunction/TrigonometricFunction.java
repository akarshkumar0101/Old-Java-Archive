package math.cas.function.trigfunction;

import math.cas.Entity;
import math.cas.Function;
import math.cas.OneArgumentFunction;

public abstract class TrigonometricFunction extends OneArgumentFunction {

	public static void registerAllFunctions() {
		Function.registerFunction("sin", SinFunction.class);
		Function.registerFunction("cos", CosFunction.class);
		Function.registerFunction("tan", TanFunction.class);
		Function.registerFunction("csc", CscFunction.class);
		Function.registerFunction("sec", SecFunction.class);
		Function.registerFunction("cot", CotFunction.class);
	}

	public TrigonometricFunction(Entity... parameters) {
		super(parameters);
	}
}
