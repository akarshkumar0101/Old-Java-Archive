package math.cas.function.basicfunction;

import java.util.Map;

import array.Arrays;
import data.function.Function1D;
import math.cas.Entity;
import math.cas.Variable;

public class SubFunction extends BasicFunction {

	public SubFunction(Entity... parameters) {
		super(parameters);
	}

	@Override
	public double evaluate(Map<Variable, Double> variableValues) {
		return a.evaluate(variableValues) - b.evaluate(variableValues);
	}

	@Override
	public Entity partialWithRespectTo(Variable var) {
		return new SubFunction(Arrays.performFunction(parameters, new Function1D<Entity, Entity>() {
			@Override
			public Entity evaluate(Entity a) {
				return a.partialWithRespectTo(var);
			}
		}));
	}

}
