package math;

public class Variable {
	private final String name;
	private double currentVal;

	public Variable(String name) {
		this.name = name;
		currentVal = 0;
	}

	public String getName() {
		return name;
	}

	public void setValue(double val) {
		currentVal = val;
	}

	public double getValue() {
		return currentVal;
	}

}
