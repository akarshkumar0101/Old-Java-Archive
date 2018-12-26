package math;

import java.util.HashMap;

class Variable extends Calculable {
	final char variableName;

	Variable(char variableName, HashMap<Character, Double> parentVariableMap) {
		super(parentVariableMap);
		this.variableName = variableName;
	}

	@Override
	public boolean isConstant() {
		return false;
	}

	@Override
	public double getValue() {
		return variableMap.get(variableName);
	}
}
