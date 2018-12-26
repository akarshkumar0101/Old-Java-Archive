package math.functions.trig;

import math.Function;
import math.Node;

public class TanFunction extends Function {

	static {
		Function.registerFunction("tan", TanFunction.class);
	}

	public TanFunction(Node parent, Node child) {
		super(parent, new Node[] { child });
	}

	@Override
	public double evaluate() {
		return Math.tan(getChildren()[0].evaluate());
	}

}
