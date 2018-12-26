package piece;

import game.*;
import util.*;

public class Knight extends Piece {
	
	public Knight(Coordinate c, Side s) {
		super(c, s);
		
	}

	public Knight(Piece another) {
		super(another);
	}
	static boolean ivm(Board board, Move move) {
		Coordinate movingFrom = move.getMovingFrom();
		Coordinate movingTo = move.getMovingTo();

		for (int x = -2; x <= 2; x++) {
			for (int y = -2; y <= 2; y++) {
				if (Math.abs(x) != Math.abs(y) && x != 0 && y != 0) {
					if (movingTo.getX() == movingFrom.getX() + x
							&& movingTo.getY() == movingFrom.getY() + y)
						return true;
				}
			}
		}

		return false;
	}
}
