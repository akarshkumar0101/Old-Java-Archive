package math.functions;

import math.Function;
import math.Node;

public class AddFunction extends Function {
	static {
		Function.registerFunction("add", AddFunction.class);
	}

	public AddFunction(Node parent, Node... children) {
		super(parent, children);
	}

	@Override
	public double evaluate() {
		double sum = 0;
		for (int i = 0; i < getChildren().length; i++) {
			sum += getChildren()[i].evaluate();
		}
		return sum;
	}

	@Override
	public String toString() {
		String str = "(";
		for (int i = 0; i < getChildren().length; i++) {
			str += getChildren()[i];
			if (i != getChildren().length - 1) {
				str += "+";
			}
		}
		str += ")";
		return str;
	}

}
