package math;

public class Constant implements Calculable {
	public static final Constant _E = new Constant(Math.E) {
		@Override
		public String toString() {
			return "E";
		}
	};
	public static final Constant _PI = new Constant(Math.E) {
		@Override
		public String toString() {
			return "PI";
		}
	};

	// no need for reference to a variable map b/c this is a constant
	private final double value;

	public Constant(double value) {
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
		return String.valueOf(value);
	}

}
