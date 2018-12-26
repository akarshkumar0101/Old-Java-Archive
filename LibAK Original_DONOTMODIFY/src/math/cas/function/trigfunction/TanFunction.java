package math.cas.function.trigfunction;

import java.util.Map;

import math.cas.Constant;
import math.cas.Entity;
import math.cas.Variable;
import math.cas.function.basicfunction.ExpFunction;

public class TanFunction extends TrigonometricFunction {

	public TanFunction(Entity... parameters) {
		super(parameters);
	}

	@Override
	public double evaluate(Map<Variable, Double> variableValues) {
		return Math.tan(a.evaluate(variableValues));
	}

	@Override
	public Entity derivativeWithRespectToArgument() {
		return new ExpFunction(new SecFunction(a), new Constant(2));
	}

}
