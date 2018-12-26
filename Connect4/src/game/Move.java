package game;

public class Move {

	private final int column;
	/**
	 * Used for ai purposes
	 */
	private Integer value;

	public Move(int column) {
		this.column = column;
	}

	public int getColumn() {
		return column;
	}

	public int getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
}
