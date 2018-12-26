package chesspieces;

import game.*;
import util.*;

public class Pawn extends ChessPiece {
	
	private final static String IMAGEPATH = "";

	public Pawn(Coordinate coordinate, Side side) {
		super(coordinate, side);
		VALUE = 1;
	}
	public Pawn(Pawn another){
		super(another);
		VALUE = 1;
	}
	
	
	public static boolean isValidMoveForPawn(Board board, Coordinate movingFrom, Coordinate movingTo){
		
		if(board.getPiece(movingFrom).getClass() != Pawn.class) return false;
		
		ChessPiece first = board.getPiece(movingFrom);
		ChessPiece second = board.getPiece(movingTo);
		
		if(first.getSide() == Side.WHITE && !(movingTo.getY() > movingFrom.getY() && movingTo.getY() <= movingFrom.getY()+2)) return false;
		if(first.getSide() == Side.BLACK && !(movingTo.getY() < movingFrom.getY() && movingTo.getY() >= movingFrom.getY()-2)) return false;
		
		int playerint = (first.getSide() == Side.WHITE) ? 1: -1;
		
		if(movingTo.getX() == movingFrom.getX() + 1 || movingTo.getX() == movingFrom.getX() - 1){
			if(movingTo.getY() != movingFrom.getY() + playerint) return false;
			else{
				if(second.getClass() == NoPiece.class) return false;
			}
		}
		else if(movingTo.getX() == movingFrom.getX()){
			if(second.getClass() != NoPiece.class) return false;
			
			if(movingTo.getY() == movingFrom.getY() + playerint && second.getClass() == NoPiece.class) return true;
			
			if(first.getHasMoved() && movingTo.getY() == movingFrom.getY() + 2 * playerint) return false;
			if(!first.getHasMoved() && movingTo.getY() == movingFrom.getY() + 2 * playerint && board.getPiece(Coordinate.getCoordinate(movingTo.getX(), movingTo.getY() - playerint)).getClass() == NoPiece.class) {
				return true;
			}
			else return false;
			
		}
		else return false;
		
		
		
		
		return true;
	}
	
}
