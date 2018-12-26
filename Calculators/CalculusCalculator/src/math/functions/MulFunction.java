package math.functions;

import math.Function;
import math.Node;

public class MulFunction extends Function {
	static {
		Function.registerFunction("*", MulFunction.class);
	}

	public MulFunction(Node parent, Node a, Node b) {
		super(parent, new Node[] { a, b });
	}

	@Override
	public double evaluate() {
		return getChildren()[0].evaluate() * getChildren()[1].evaluate();
	}

	@Override
	public String toString() {
		return "(" + getChildren()[0] + "*" + getChildren()[1] + ")";
	}
}
