package util;

public enum Direction {
	UP, DOWN, LEFT, RIGHT;
	
	public Direction getOppositeDirection(){
		if(this == UP) return DOWN;
		if(this == DOWN) return UP;
		if(this == LEFT) return RIGHT;
		return LEFT;
	}
	public int toInt(){
		if(this == UP || this == RIGHT) return 1;
		return -1;
	}
	
}
