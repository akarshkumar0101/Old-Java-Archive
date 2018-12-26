package game.util;

public enum Direction {
	UP, RIGHT, DOWN, LEFT;

	public Direction getOpposite() {
		if (this == UP)
			return DOWN;
		if (this == DOWN)
			return UP;
		if (this == RIGHT)
			return LEFT;
		if (this == LEFT)
			return RIGHT;
		return null;
	}

	public int toInt() {
		if (this == UP || this == RIGHT)
			return 1;
		if (this == DOWN || this == LEFT)
			return -1;
		return 0;
	}
}
