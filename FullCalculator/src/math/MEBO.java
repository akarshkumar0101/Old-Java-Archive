package math;

enum MEBO {
	ADD, SUB, MUL, DIV, MOD, POW;

	public boolean isAdditive() {
		return this == ADD || this == SUB;
	}

	public boolean isMultiplicative() {
		return this == MUL || this == DIV || this == MOD;
	}

	public boolean isExponential() {
		return this == POW;
	}
}
