package piece;

import game.*;
import util.*;

public class Pawn extends Piece {
	
	public Pawn(Coordinate c, Side s) {
		super(c, s);
		
	}

	public Pawn(Piece another) {
		super(another);
	}
	
	@Override
	public void moveTo(Coordinate inp) {
		super.moveTo(inp);
		if(inp.getY() ==  0 || inp.getY() == 7){
			System.out.println("heyyyyyyyyy");
			//////////////////////////NOTIFTY UPDATER FOR PAWNS
		}
	}
	
	static boolean ivm(Board board, Move move) {
		Coordinate movingFrom = move.getMovingFrom();
		Coordinate movingTo = move.getMovingTo();

		Piece first = board.getPiece(movingFrom);
		Piece second = board.getPiece(movingTo);

		if (first.getSide() == Side.WHITE
				&& !(movingTo.getY() > movingFrom.getY() && movingTo.getY() <= movingFrom
						.getY() + 2))
			return false;
		if (first.getSide() == Side.BLACK
				&& !(movingTo.getY() < movingFrom.getY() && movingTo.getY() >= movingFrom
						.getY() - 2))
			return false;

		int playerint = (first.getSide() == Side.WHITE) ? 1 : -1;

		if (movingTo.getX() == movingFrom.getX() + 1
				|| movingTo.getX() == movingFrom.getX() - 1) {
			if (movingTo.getY() != movingFrom.getY() + playerint)
				return false;
			else {
				if (second.getClass() == Space.class)
					return false;
			}
		} else if (movingTo.getX() == movingFrom.getX()) {
			if (second.getClass() != Space.class)
				return false;

			if (movingTo.getY() == movingFrom.getY() + playerint
					&& second.getClass() == Space.class)
				return true;

			if (first.hasMoved()
					&& movingTo.getY() == movingFrom.getY() + 2 * playerint)
				return false;
			if (!first.hasMoved()
					&& movingTo.getY() == movingFrom.getY() + 2 * playerint
					&& board.getPiece(
							Coordinate.getCoordinate(movingTo.getX(),
									movingTo.getY() - playerint)).getClass() == Space.class) {
				return true;
			} else
				return false;

		} else
			return false;

		return true;

	}
}
