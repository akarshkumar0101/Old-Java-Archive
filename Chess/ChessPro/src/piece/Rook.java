package piece;

import game.*;
import util.*;

public class Rook extends Piece {
	
	public Rook(Coordinate c, Side s) {
		super(c, s);
	}

	public Rook(Piece another) {
		super(another);
	}
	static boolean ivm(Board board, Move move) {
		Coordinate movingFrom = move.getMovingFrom();
		Coordinate movingTo = move.getMovingTo();

		if (movingTo.getX() != movingFrom.getX()
				&& movingTo.getY() != movingFrom.getY())
			return false;

		if (movingTo.getX() > movingFrom.getX()) { // RIGHT

			for (int i = movingFrom.getX() + 1; i < movingTo.getX(); i++) {
				if (board.getPiece(
						Coordinate.getCoordinate(i, movingFrom.getY()))
						.getClass() != Space.class)
					return false;

			}

		} else if (movingTo.getX() < movingFrom.getX()) { // LEFT

			for (int i = movingFrom.getX() - 1; i > movingTo.getX(); i--) {
				if (board.getPiece(
						Coordinate.getCoordinate(i, movingFrom.getY()))
						.getClass() != Space.class)
					return false;

			}

		} else if (movingTo.getY() > movingFrom.getY()) { // UP

			for (int i = movingFrom.getY() + 1; i < movingTo.getY(); i++) {
				if (board.getPiece(
						Coordinate.getCoordinate(movingFrom.getX(), i))
						.getClass() != Space.class)
					return false;

			}

		} else if (movingTo.getY() < movingFrom.getY()) { // DOWN

			for (int i = movingFrom.getY() - 1; i > movingTo.getY(); i--) {
				if (board.getPiece(
						Coordinate.getCoordinate(movingFrom.getX(), i))
						.getClass() != Space.class)
					return false;

			}

		}

		return true;
	}
}
