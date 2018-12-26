package math;

import java.util.ArrayList;
import java.util.HashMap;

//TODO go through the visibility of all methods
//TODO make sure originalString gets spaces removed, toLowerCased(to identify variable names), and function Strings get toUpperCased to make them identified
public class Expression implements Calculable {
	private final ArrayList<Object> parts;
	private final HashMap<Variable, Double> variableMap;
	// private final ArrayList<Class<? extends Function>> functionsUsed;

	private final String rawSource;

	public Expression(String source) {
		this(source, new HashMap<Variable, Double>());
	}

	public Expression(String source, HashMap<Variable, Double> variableMap) {
		rawSource = source;
		parts = new ArrayList<Object>();
		this.variableMap = variableMap;

	}

	@Override
	public boolean isConstant() {
		return false;
	}

	@Override
	public double getValue() {
		return 0;
	}

	private static String formatRawInput(String inp) {
		return null;
	}

}
