package piece;

import game.*;
import util.*;

public class Bishop extends Piece {
	
	
	public Bishop(Coordinate c, Side s) {
		super(c, s);
	}

	public Bishop(Piece another) {
		super(another);
	}

	static boolean ivm(Board board, Move move) {
		Coordinate movingFrom = move.getMovingFrom();
		Coordinate movingTo = move.getMovingTo();

		boolean isInLineOfSight = false;

		if (movingTo.getX() > movingFrom.getX()
				&& movingTo.getY() > movingFrom.getY()) { // TOP RIGHT

			for (int i = 1; movingFrom.getX() + i < 8
					&& movingFrom.getY() + i < 8; i++) {
				if (movingTo.getX() == movingFrom.getX() + i
						&& movingTo.getY() == movingFrom.getY() + i)
					isInLineOfSight = true;
			}
			if (!isInLineOfSight)
				return false;

			for (int i = 1; i < movingTo.getX() - movingFrom.getX(); i++) {
				if (board.getPiece(
						Coordinate.getCoordinate(movingFrom.getX() + i,
								movingFrom.getY() + i)).getClass() != Space.class)
					return false;
			}

			return true;

		}

		else if (movingTo.getX() < movingFrom.getX()
				&& movingTo.getY() > movingFrom.getY()) { // TOP LEFT

			for (int i = 1; movingFrom.getX() - i > -1
					&& movingFrom.getY() + i < 8; i++) {
				if (movingTo.getX() == movingFrom.getX() - i
						&& movingTo.getY() == movingFrom.getY() + i)
					isInLineOfSight = true;
			}
			if (!isInLineOfSight)
				return false;

			for (int i = 1; i < movingFrom.getX() - movingTo.getX(); i++) {
				if (board.getPiece(
						Coordinate.getCoordinate(movingFrom.getX() - i,
								movingFrom.getY() + i)).getClass() != Space.class)
					return false;
			}

			return true;

		}

		else if (movingTo.getX() < movingFrom.getX()
				&& movingTo.getY() < movingFrom.getY()) { // BOTTOM LEFT

			for (int i = 1; movingFrom.getX() - i > -1
					&& movingFrom.getY() - i > -1; i++) {
				if (movingTo.getX() == movingFrom.getX() - i
						&& movingTo.getY() == movingFrom.getY() - i)
					isInLineOfSight = true;
			}
			if (!isInLineOfSight)
				return false;

			for (int i = 1; i < movingFrom.getX() - movingTo.getX(); i++) {
				if (board.getPiece(
						Coordinate.getCoordinate(movingFrom.getX() - i,
								movingFrom.getY() - i)).getClass() != Space.class)
					return false;
			}

			return true;

		}

		else if (movingTo.getX() > movingFrom.getX()
				&& movingTo.getY() < movingFrom.getY()) { // BOTTOM RIGHT

			for (int i = 1; movingFrom.getX() + i < 8
					&& movingFrom.getY() - i > -1; i++) {
				if (movingTo.getX() == movingFrom.getX() + i
						&& movingTo.getY() == movingFrom.getY() - i)
					isInLineOfSight = true;
			}
			if (!isInLineOfSight)
				return false;

			for (int i = 1; i < movingTo.getX() - movingFrom.getX(); i++) {
				if (board.getPiece(
						Coordinate.getCoordinate(movingFrom.getX() + i,
								movingFrom.getY() - i)).getClass() != Space.class)
					return false;
			}

			return true;

		}

		return false;

	}

}
