package piece;

import game.*;
import util.*;

public class King extends Piece {
	
	
	public King(Coordinate c, Side s) {
		super(c, s);
	}

	public King(Piece another) {
		super(another);
	}
	
	static boolean ivm(Board board, Move move) {
		Coordinate movingFrom = move.getMovingFrom();
		Coordinate movingTo = move.getMovingTo();
		for (int x = -1; x < 2; x++) {
			for (int y = -1; y < 2; y++) {
				if (x != 0 || y != 0) {
					if (movingTo.getX() == movingFrom.getX() + x
							&& movingTo.getY() == movingFrom.getY() + y)
						return true;
				}
			}
		}

		return false;
	}
}
