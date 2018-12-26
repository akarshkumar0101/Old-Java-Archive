package math;

public class VariableInstance extends Node {

	private final Variable variable;

	public VariableInstance(Node parent, Variable variable) {
		super(parent, null);
		this.variable = variable;
	}

	@Override
	public double evaluate() {
		return variable.getValue();
	}

	@Override
	public String toString() {
		return variable.getName();
	}
}
