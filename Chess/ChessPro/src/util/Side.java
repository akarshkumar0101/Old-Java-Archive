package util;

public enum Side {
	WHITE, BLACK, NEUTRAL;

	public static Side myValueOf(String inp) {
		if (inp.contains("w") || inp.contains("W"))
			return WHITE;
		if (inp.contains("b") || inp.contains("B"))
			return BLACK;
		if (inp.contains("n") || inp.contains("N"))
			return NEUTRAL;

		try {
			return valueOf(inp.toUpperCase());
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	@Override
	public String toString() {
		if (this == WHITE)
			return "W";
		else if (this == BLACK)
			return "B";
		else
			return "N";
	}

	public static Side getOpposite(Side inp) {
		if (inp == WHITE)
			return BLACK;
		if (inp == BLACK)
			return WHITE;
		return NEUTRAL;
	}

}
