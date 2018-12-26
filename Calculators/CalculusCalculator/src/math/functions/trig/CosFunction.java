package math.functions.trig;

import math.Function;
import math.Node;

public class CosFunction extends Function {

	static {
		Function.registerFunction("cos", CosFunction.class);
	}

	public CosFunction(Node parent, Node child) {
		super(parent, new Node[] { child });
	}

	@Override
	public double evaluate() {
		return Math.cos(getChildren()[0].evaluate());
	}

}
