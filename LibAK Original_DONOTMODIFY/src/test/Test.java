package test;

import math.cas.Expression;
import math.cas.Variable;

public class Test {

	public static void main(String[] args) {
		System.out.println(Math.PI * Math.sin(Math.pow(2.3, Math.tan(2.3))));
		Expression exp = new Expression("x*x*y", "x", "y");

		System.out.println(exp.getRoot().partialWithRespectTo(new Variable("y")));

	}
}