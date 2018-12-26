package math;

public class Number extends Node {

	private final double num;

	public Number(Node parent, double num) {
		super(parent, null);
		this.num = num;
	}

	@Override
	public double evaluate() {
		return num;
	}

	@Override
	public String toString() {
		return num + "";
	}

}
