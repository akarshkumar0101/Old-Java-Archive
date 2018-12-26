package piece;

import game.*;
import util.*;

public class Queen extends Piece {
	
	public Queen(Coordinate c, Side s) {
		super(c, s);
	}

	public Queen(Piece another) {
		super(another);
	}
	static boolean ivm(Board board, Move move) {

		if (Rook.ivm(board, move) || Bishop.ivm(board, move))
			return true;
		return false;
	}
}
