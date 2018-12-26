package math;

import java.util.ArrayList;
import java.util.HashMap;

public class Expression extends Calculable {

	private String originalSource;

	/**
	 * Temporary variable for parsing source String
	 */
	private String parsingString;

	/**
	 * Will contain a math expression consisting ONLY of instances of
	 * MEParts(basic arithmetic), and instances of Calculable(Expression,
	 * Function, Variable, Constant)
	 */
	ArrayList<Object> matheq;

	public Expression(String source) {
		// creating new variable map
		this(source, new HashMap<Character, Double>());
	}

	public Expression(String source, HashMap<Character, Double> parentVariableMap) {
		super(parentVariableMap);
		originalSource = parsingString = source;
	}

	@Override
	public boolean isConstant() {
		return false;
	}

	@Override
	public double getValue() {
		return 0;
	}

	// Following methods are parsing methods used at the creation of an
	// Expression

	private static final String[] ALLIDENTIFIERS;
	static {
		// TODO add all functions to this identifier list
		ALLIDENTIFIERS = new String[] { "+", "-", "*", "/", "%", "^" };

	}

	private static Object getIdentifier(String inp) {
		return null;
	}

	private static Object nextIdentifier(Expression p) {
		return null;
	}

	private static ArrayList<Object> getPossibleIdentifiers(String inp) {
		return null;
	}

	/**
	 * Handles converting the source String to an Math Equation
	 * Object(identified as an ArrayList<Object>). Also handles adding unadded
	 * multiplication signs.
	 * 
	 * @param source
	 *            the source String to be parsed
	 * @return the ME object made
	 */
	private static ArrayList<Object> toME(String source) {
		return null;
	}

	/**
	 * Handles replacing constant Expression/functions with their numerical
	 * values, and combining additive and multiplicative constants, etc
	 * 
	 * @param me
	 *            the input math equation to be simplified
	 * @return the simplified version of the math equation
	 */
	private static ArrayList<Object> simplifyME(ArrayList<Object> me) {
		return null;
	}
}
