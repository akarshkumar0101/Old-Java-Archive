package math.cas;

import math.cas.function.basicfunction.MulFunction;

public abstract class OneArgumentFunction extends Function {

	protected final Entity a;

	protected OneArgumentFunction(Entity... parameters) {
		super(parameters);
		a = parameters[0];
	}

	@Override
	public Entity partialWithRespectTo(Variable var) {
		return new MulFunction(derivativeWithRespectToArgument(), a.partialWithRespectTo(var));
	}

	public abstract Entity derivativeWithRespectToArgument();

}
