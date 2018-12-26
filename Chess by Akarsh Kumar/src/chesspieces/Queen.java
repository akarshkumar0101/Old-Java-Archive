package chesspieces;

import game.*;
import util.*;

public class Queen extends ChessPiece {
	
	private final static String IMAGEPATH = "";

	public Queen(Coordinate coordinate, Side side) {
		super(coordinate, side);
		VALUE = 9;
	}
	public Queen(Queen another){
		super(another);
		VALUE = 9;
	}
	
	
	public static boolean isValidMoveForQueen(Board board, Coordinate movingFrom, Coordinate movingTo){
		if(Rook.isValidMoveForRook(board, movingFrom, movingTo) || Bishop.isValidMoveForBishop(board, movingFrom, movingTo)) return true;
		return false;
	}

}
