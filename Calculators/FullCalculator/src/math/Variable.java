package math;

import java.util.HashMap;

public class Variable implements Calculable {
	private final HashMap<Variable, Double> variableMap;
	private String name;

	public Variable(String name, HashMap<Variable, Double> variableMap) {
		this.name = name;
		this.variableMap = variableMap;
	}

	@Override
	public boolean isConstant() {
		return false;
	}

	@Override
	public double getValue() {
		return variableMap.get(this);
	}

	@Override
	public String toString() {
		return name;
	}

}
