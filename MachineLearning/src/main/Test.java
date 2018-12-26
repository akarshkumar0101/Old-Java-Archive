package main;

public class Test {

	public static void main(String[] args) {
		// double[][] points = { { 1, 1 }, { 2, 3 }, { 3, 2 }, { 4, 6 }, { 5, 7 }, { 6,
		// 7 } };
		// PolynomialRegression reg = new PolynomialRegression(points, 3);
		//
		// double[] param = reg.performRegression(points, 1000000, 0.01);
		// System.out.println(Arrays.toString(param));

		// Expression exp = new Expression("(9+(3-1)^3)/(4^2+2-1)");
		// System.out.println(exp.getRoot());
		// System.out.println(exp.evaluate(null));

		PaintingFrame frame = new PaintingFrame(20, 20, null);
		frame.setVisible(true);
	}

}
