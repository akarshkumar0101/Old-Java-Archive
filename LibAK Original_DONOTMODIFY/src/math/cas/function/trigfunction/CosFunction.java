package math.cas.function.trigfunction;

import java.util.Map;

import math.cas.Constant;
import math.cas.Entity;
import math.cas.Variable;
import math.cas.function.basicfunction.MulFunction;

public class CosFunction extends TrigonometricFunction {

	public CosFunction(Entity... parameters) {
		super(parameters);
	}

	@Override
	public double evaluate(Map<Variable, Double> variableValues) {
		return Math.cos(a.evaluate(variableValues));
	}

	@Override
	public Entity derivativeWithRespectToArgument() {
		return new MulFunction(Constant.NEGATIVE_ONE, new SinFunction(a));
	}

}
