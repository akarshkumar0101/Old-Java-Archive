package math;

class Constant extends Calculable {
	static final Constant _E = new Constant(Math.E);
	static final Constant _PI = new Constant(Math.PI);

	private final double value;

	private Constant(double value) {
		super(null);
		this.value = value;
	}

	@Override
	public boolean isConstant() {
		return true;
	}

	@Override
	public double getValue() {
		return value;
	}

	@Override
	public String toString() {
		if (this == _E)
			return "E";
		else if (this == _PI)
			return "PI";
		else
			return String.valueOf(value);
	}

}
