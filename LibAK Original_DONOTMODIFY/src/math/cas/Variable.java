package math.cas;

import java.util.Map;

import array.Arrays;

public class Variable extends Entity {
	private final String ID;

	public Variable(String ID) {
		super();
		this.ID = ID;

		variables = Arrays.addElement(variables, this, new Variable[variables.length + 1]);
	}

	public String getID() {
		return ID;
	}

	@Override
	public double evaluate(Map<Variable, Double> variableValues) {
		return variableValues.get(this);
	}

	@Override
	public Entity partialWithRespectTo(Variable var) {
		return equals(var) ? Constant.ONE : Constant.ZERO;
	}

	@Override
	public Variable consolidate() {
		return this;
	}

	@Override
	public boolean equals(Object another) {
		return another instanceof Variable && ID.equals(((Variable) another).getID());
	}

	@Override
	public int hashCode() {
		return ID.hashCode();
	}

	@Override
	public String toString() {
		return ID;
	}

}
