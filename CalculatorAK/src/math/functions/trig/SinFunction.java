package math.functions.trig;

import math.Function;
import math.Node;

public class SinFunction extends Function {

	static {
		Function.registerFunction("sin", SinFunction.class);
	}

	public SinFunction(Node parent, Node child) {
		super(parent, new Node[] { child });
	}

	@Override
	public double evaluate() {
		return Math.sin(getChildren()[0].evaluate());
	}

}
