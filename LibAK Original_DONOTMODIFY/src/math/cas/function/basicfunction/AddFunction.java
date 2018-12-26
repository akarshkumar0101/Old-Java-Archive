package math.cas.function.basicfunction;

import java.util.Map;

import array.Arrays;
import data.function.Function1D;
import math.cas.Constant;
import math.cas.Entity;
import math.cas.Variable;

public class AddFunction extends BasicFunction {

	public AddFunction(Entity... parameters) {
		super(parameters);
	}

	@Override
	public double evaluate(Map<Variable, Double> variableValues) {
		return a.evaluate(variableValues) + b.evaluate(variableValues);
	}

	@Override
	public Entity partialWithRespectTo(Variable var) {
		return new AddFunction(Arrays.performFunction(parameters, new Function1D<Entity, Entity>() {
			@Override
			public Entity evaluate(Entity a) {
				return a.partialWithRespectTo(var);
			}
		}));
	}

	@Override
	public Entity consolidate() {
		Entity ans = super.consolidate();

		if (ans instanceof AddFunction) {
			AddFunction func = (AddFunction) ans;
			Entity check0 = func.consolidateCheck0s();
			if (check0 != null)
				return check0;
		}

		return ans;
	}

	private Entity consolidateCheck0s() {
		if (a.equals(Constant.ZERO))
			return b;
		else if (b.equals(Constant.ZERO))
			return a;
		return null;
	}

}
