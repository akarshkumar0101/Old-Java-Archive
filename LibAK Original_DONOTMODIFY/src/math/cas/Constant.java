package math.cas;

import java.util.Map;

public class Constant extends Entity {
	public static final Constant ZERO = new Constant(0);
	public static final Constant ONE = new Constant(1);
	public static final Constant NEGATIVE_ONE = new Constant(-1);

	public static final String PI_STRING = "PI";
	public static final Constant PI = new Constant(Math.PI);

	public static final String E_STRING = "E";
	public static final Constant E = new Constant(Math.E);

	private double value;

	public Constant(double value) {
		super();
		this.value = value;
	}

	public static Constant createConstant(String str) {
		if (PI_STRING.equals(str))
			return PI;
		if (E_STRING.equals(str))
			return E;
		try {
			return new Constant(Double.parseDouble(str));
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public double evaluate(Map<Variable, Double> variableValues) {
		return value;
	}

	@Override
	public Entity partialWithRespectTo(Variable var) {
		return Constant.ZERO;
	}

	@Override
	public Constant consolidate() {
		return this;
	}

	@Override
	public boolean equals(Object another) {
		return another instanceof Constant && value == ((Constant) another).value;
	}

	@Override
	public String toString() {
		if (this == PI)
			return PI_STRING;
		else if (this == E)
			return E_STRING;
		return String.valueOf(value);
	}

}
