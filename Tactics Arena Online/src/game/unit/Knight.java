package game.unit;

import game.board.Board;
import game.util.Direction;

public class Knight extends Unit {

	public static final int MOVERANGE = 3;

	public Knight(Board board, Direction directionFacing, byte... coor) {
		super(board, directionFacing, coor);
	}

	@Override
	public int getMoveRange() {
		return MOVERANGE;
	}

}
