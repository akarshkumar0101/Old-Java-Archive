package math.cas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//entity is a constant, variable, function, or expression
public class Expression {

	private final String originalString;
	private String processedString;

	private Entity root;

	private final Variable[] variables;

	public Expression(String str, String... varStrings) {
		this(str, toVariableArray(varStrings));
	}

	public Expression(String str, Variable[] variables) {
		Function.registerKnownFunctions();
		originalString = str;
		processedString = processString(str);

		char[] charr = processedString.toCharArray();
		List<Object> parts = new ArrayList<Object>(charr.length);
		for (char c : charr) {
			parts.add(c);
		}

		root = MathParser.parse(parts, variables)[0];

		this.variables = root.variableDependancies();
	}

	private static Variable[] toVariableArray(String... varStrings) {
		Variable[] vars = new Variable[varStrings.length];
		for (int i = 0; i < vars.length; i++) {
			vars[i] = new Variable(varStrings[i]);
		}
		return vars;
	}

	private static String processString(String str) {
		return str.replace(" ", "");
	}

	public Entity getRoot() {
		return root;
	}

	public void consolidate() {
		root = root.consolidate();
	}

	public double evaluate(Map<Variable, Double> variableValues) {
		return root.evaluate(variableValues);
	}

	public String getOriginalString() {
		return originalString;
	}

	public Variable[] getVariableDependancies() {
		return variables;
	}

	@Override
	public String toString() {
		return root.toString();
	}
}
