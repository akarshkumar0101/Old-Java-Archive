package pieces;

import game.Board;
import util.Coordinate;

public class Queen extends ChessPiece {

	public Queen(Coordinate coordinate, boolean player) {
		super(coordinate, player);
	}
	public Queen(Queen another){
		super(another);
	}
	
	public boolean equals(Queen another){
		return super.equals(another);
	}
	
	public static boolean isValidMove(Board board, Coordinate movingFrom, Coordinate movingTo){
		if(Rook.isValidMove(board, movingFrom, movingTo) || Bishop.isValidMove(board, movingFrom, movingTo)) return true;
		return false;
	}

}
