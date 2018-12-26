package game.board;

import game.unit.Unit;
import game.util.InvalidCoordinateException;

//simply to hold information about the board and the pieces on it
public abstract class Board {
	// always x, y to access elements
	// use byte[]{x,y} to represent coordinates
	protected final Square[][] grid;

	public Board() {
		grid = new Square[getWidth()][getHeight()];

		for (byte x = 0; x < getWidth(); x++) {
			for (byte y = 0; y < getHeight(); y++) {
				try {
					checkCoordinateRange(x, y);
					grid[x][y] = new Square(x, y);
				} catch (InvalidCoordinateException e) {
					grid[x][y] = null;
				}
			}
		}
	}

	protected abstract int getWidth();

	protected abstract int getHeight();

	public abstract boolean isInBoard(byte... coor);

	// TODO public? private?
	// TODO go through everything in game package to see visibility
	protected void checkCoordinateRange(byte... coor) {
		if (!isInBoard(coor))
			throw new InvalidCoordinateException(coor);
	}

	public Square getSquare(byte... coor) {
		checkCoordinateRange(coor);
		return grid[coor[0]][coor[1]];
	}

	public Unit getUnitAt(byte... coor) {
		checkCoordinateRange(coor);
		return grid[coor[0]][coor[1]].getUnit();
	}

}
