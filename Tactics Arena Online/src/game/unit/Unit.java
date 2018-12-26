package game.unit;

import game.board.Board;
import game.util.Direction;
import game.util.Path;

public abstract class Unit {
	protected final Board board;

	protected byte[] coor;
	protected Direction directionFacing;

	public Unit(Board board, Direction directionFacing, byte... coor) {
		this.board = board;
		this.coor = coor;
		this.directionFacing = directionFacing;
	}

	public Board getBoard() {
		return board;
	}

	public byte[] getCoor() {
		return coor;
	}

	public abstract int getMoveRange();

	public Path getPathTo(byte... moveToCoor) {
		if (!(canMove() && isInRangeOfWalking(moveToCoor)))
			return null;
		return null;
	}

	public final boolean isInRangeOfWalking(byte... moveToCoor) {
		int walkingdistance = Math.abs(moveToCoor[0] - coor[0]) + Math.abs(moveToCoor[1] - coor[1]);
		return walkingdistance <= getMoveRange();
	}

	// TODO make sure all units should consider overriding these methods
	public boolean canMove() {
		return true;
	}

	// side stepping is when it can move out of way to let friendly piece pass
	public boolean canSideStep() {
		return true;
	}

}
