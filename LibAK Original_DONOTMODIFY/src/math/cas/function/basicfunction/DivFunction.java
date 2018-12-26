package math.cas.function.basicfunction;

import java.util.Map;

import math.cas.Constant;
import math.cas.Entity;
import math.cas.Variable;

public class DivFunction extends BasicFunction {

	public DivFunction(Entity... parameters) {
		super(parameters);
	}

	@Override
	public double evaluate(Map<Variable, Double> variableValues) {
		return a.evaluate(variableValues) / b.evaluate(variableValues);
	}

	@Override
	public Entity partialWithRespectTo(Variable var) {
		Entity a = parameters[0], b = parameters[1];
		Entity da = a.partialWithRespectTo(var), db = b.partialWithRespectTo(var);

		Entity top = new SubFunction(new MulFunction(da, b), new MulFunction(a, db));
		Entity bot = new ExpFunction(b, new Constant(2));
		return new DivFunction(top, bot);
	}

}
