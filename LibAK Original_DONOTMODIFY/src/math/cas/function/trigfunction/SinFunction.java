package math.cas.function.trigfunction;

import java.util.Map;

import math.cas.Entity;
import math.cas.Variable;

public class SinFunction extends TrigonometricFunction {

	public SinFunction(Entity... parameters) {
		super(parameters);
	}

	@Override
	public double evaluate(Map<Variable, Double> variableValues) {
		return Math.sin(parameters[0].evaluate(variableValues));
	}

	@Override
	public Entity derivativeWithRespectToArgument() {
		return new CosFunction(a);
	}
}
