package chesspieces;

import game.*;
import util.*;

public class Rook extends ChessPiece {
	
	private final static String IMAGEPATH = "";

	public Rook(Coordinate coordinate, Side side) {
		super(coordinate, side);
		VALUE = 5;
	}
	public Rook(Rook another){
		super(another);
		VALUE = 5;
	}
	
	
	public static boolean isValidMoveForRook(Board board, Coordinate movingFrom, Coordinate movingTo){
		
		if(movingTo.getX() != movingFrom.getX() && movingTo.getY() != movingFrom.getY())return false;
		
		
		
		if(movingTo.getX() > movingFrom.getX()){ //RIGHT
			
			for(int i = movingFrom.getX()+1; i < movingTo.getX(); i ++){
				if(board.getPiece(Coordinate.getCoordinate(i, movingFrom.getY())).getClass() != NoPiece.class) return false;
				
			}
			
			
		}
		else if(movingTo.getX() < movingFrom.getX()){ //LEFT
			
			for(int i = movingFrom.getX()-1; i > movingTo.getX(); i --){
				if(board.getPiece(Coordinate.getCoordinate(i, movingFrom.getY())).getClass() != NoPiece.class) return false;
				
			}
			
		}
		else if(movingTo.getY() > movingFrom.getY()){ //UP

			for(int i = movingFrom.getY()+1; i < movingTo.getY(); i ++){
				if(board.getPiece(Coordinate.getCoordinate(movingFrom.getX(), i)).getClass() != NoPiece.class) return false;
				
			}
			
		}
		else if(movingTo.getY() < movingFrom.getY()){ //DOWN

			for(int i = movingFrom.getY()-1; i > movingTo.getY(); i --){
				if(board.getPiece(Coordinate.getCoordinate(movingFrom.getX(), i)).getClass() != NoPiece.class) return false;
				
			}
			
		}
		
		return true;
	}
	
}
