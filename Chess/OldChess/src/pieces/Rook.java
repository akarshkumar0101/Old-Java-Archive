package pieces;

import game.*;
import util.Coordinate;

public class Rook extends ChessPiece {

	public Rook(Coordinate coordinate, boolean player) {
		super(coordinate, player);
	}
	public Rook(Rook another){
		super(another);
	}
	
	public boolean equals(Rook another){
		return super.equals(another);
	}
	
	public static boolean isValidMove(Board board, Coordinate movingFrom, Coordinate movingTo){
		
		if(movingTo.getX() != movingFrom.getX() && movingTo.getY() != movingFrom.getY())return false;
		
		
		
		if(movingTo.getX() > movingFrom.getX()){ //RIGHT
			
			for(int i = movingFrom.getX()+1; i < movingTo.getX(); i ++){
				if(board.getPiece(new Coordinate(i, movingFrom.getY())).getClass() != NoPiece.class) return false;
				
			}
			
			
		}
		else if(movingTo.getX() < movingFrom.getX()){ //LEFT
			
			for(int i = movingFrom.getX()-1; i > movingTo.getX(); i --){
				if(board.getPiece(new Coordinate(i, movingFrom.getY())).getClass() != NoPiece.class) return false;
				
			}
			
		}
		else if(movingTo.getY() > movingFrom.getY()){ //UP

			for(int i = movingFrom.getY()+1; i < movingTo.getY(); i ++){
				if(board.getPiece(new Coordinate(movingFrom.getX(), i)).getClass() != NoPiece.class) return false;
				
			}
			
		}
		else if(movingTo.getY() < movingFrom.getY()){ //DOWN

			for(int i = movingFrom.getY()-1; i > movingTo.getY(); i --){
				if(board.getPiece(new Coordinate(movingFrom.getX(), i)).getClass() != NoPiece.class) return false;
				
			}
			
		}
		
		return true;
	}
	
}
