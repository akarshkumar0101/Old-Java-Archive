package math.cas.function.basicfunction;

import java.util.Map;

import math.cas.Constant;
import math.cas.Entity;
import math.cas.Variable;

public class MulFunction extends BasicFunction {

	public MulFunction(Entity... parameters) {
		super(parameters);
	}

	@Override
	public double evaluate(Map<Variable, Double> variableValues) {
		return a.evaluate(variableValues) * b.evaluate(variableValues);
	}

	@Override
	public Entity partialWithRespectTo(Variable var) {
		Entity a = parameters[0], b = parameters[1];
		Entity da = a.partialWithRespectTo(var), db = b.partialWithRespectTo(var);

		return new AddFunction(new MulFunction(da, b), new MulFunction(a, db));
	}

	@Override
	public Entity consolidate() {
		Entity ans = super.consolidate();

		if (ans instanceof MulFunction) {
			MulFunction func = (MulFunction) ans;

			Entity check0 = func.consolidateCheck0s();
			if (check0 != null)
				return check0;

			Entity check1 = func.consolidateCheck1s();
			if (check1 != null)
				return check1;
		}

		return ans;
	}

	private Entity consolidateCheck0s() {
		if (a.equals(Constant.ZERO) || b.equals(Constant.ZERO))
			return Constant.ZERO;
		return null;
	}

	private Entity consolidateCheck1s() {
		if (a.equals(Constant.ONE))
			return b;
		else if (b.equals(Constant.ONE))
			return a;
		return null;
	}

}
