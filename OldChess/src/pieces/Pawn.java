package pieces;

import game.Board;
import util.Coordinate;

public class Pawn extends ChessPiece {

	public Pawn(Coordinate coordinate, boolean player) {
		super(coordinate, player);
	}

	public Pawn(Pawn another) {
		super(another);
	}

	public boolean equals(Pawn another) {
		return super.equals(another);
	}

	public static boolean isValidMove(Board board, Coordinate movingFrom, Coordinate movingTo) {

		if (board.getPiece(movingFrom).getClass() != Pawn.class)
			return false;

		ChessPiece first = board.getPiece(movingFrom);
		ChessPiece second = board.getPiece(movingTo);

		if (first.getPlayer() && !(movingTo.getY() > movingFrom.getY() && movingTo.getY() <= movingFrom.getY() + 2))
			return false;
		if (!first.getPlayer() && !(movingTo.getY() < movingFrom.getY() && movingTo.getY() >= movingFrom.getY() - 2))
			return false;

		int playerint = (first.getPlayer()) ? 1 : -1;

		if (movingTo.getX() == movingFrom.getX() + 1 || movingTo.getX() == movingFrom.getX() - 1) {
			if (movingTo.getY() != movingFrom.getY() + playerint)
				return false;
			else {
				if (second.getClass() == NoPiece.class)
					return false;
			}
		} else if (movingTo.getX() == movingFrom.getX()) {
			if (second.getClass() != NoPiece.class)
				return false;

			if (movingTo.getY() == movingFrom.getY() + playerint && second.getClass() == NoPiece.class)
				return true;

			if (first.getHasMoved() && movingTo.getY() == movingFrom.getY() + 2 * playerint)
				return false;
			if (!first.getHasMoved() && movingTo.getY() == movingFrom.getY() + 2 * playerint && board
					.getPiece(new Coordinate(movingTo.getX(), movingTo.getY() - playerint)).getClass() == NoPiece.class)
				return true;
			else
				return false;

		} else
			return false;
		return true;
	}

}
