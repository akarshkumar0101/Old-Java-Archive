package math.cas.function.trigfunction;

import java.util.Map;

import math.cas.Constant;
import math.cas.Entity;
import math.cas.Variable;
import math.cas.function.basicfunction.MulFunction;

public class CscFunction extends TrigonometricFunction {

	public CscFunction(Entity... parameters) {
		super(parameters);
	}

	@Override
	public double evaluate(Map<Variable, Double> variableValues) {
		return 1 / Math.sin(parameters[0].evaluate(variableValues));
	}

	@Override
	public Entity derivativeWithRespectToArgument() {
		return new MulFunction(Constant.NEGATIVE_ONE, new MulFunction(new CotFunction(a), new CscFunction(a)));
	}
}
