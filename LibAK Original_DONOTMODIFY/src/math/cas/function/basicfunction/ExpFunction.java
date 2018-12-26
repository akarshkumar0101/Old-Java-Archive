package math.cas.function.basicfunction;

import java.util.Map;

import math.cas.Entity;
import math.cas.Variable;

public class ExpFunction extends BasicFunction {

	public ExpFunction(Entity... parameters) {
		super(parameters);
	}

	@Override
	public double evaluate(Map<Variable, Double> variableValues) {
		return Math.pow(a.evaluate(variableValues), b.evaluate(variableValues));
	}

	@Override
	public Entity partialWithRespectTo(Variable var) {
		return null;
	}
}
