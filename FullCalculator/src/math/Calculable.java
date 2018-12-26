package math;

import java.util.HashMap;

abstract class Calculable {
	protected final HashMap<Character, Double> variableMap;

	Calculable(HashMap<Character, Double> variableMap) {
		this.variableMap = variableMap;
	}

	protected abstract boolean isConstant();

	protected abstract double getValue();

}
