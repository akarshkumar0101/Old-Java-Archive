package pieces;

import game.Board;
import util.Coordinate;

public class Bishop extends ChessPiece {

	public Bishop(Coordinate coordinate, boolean player) {
		super(coordinate, player);
	}
	public Bishop(Bishop another){
		super(another);
	}
	
	public boolean equals(Bishop another){
		return super.equals(another);
	}
	
	public static boolean isValidMove(Board board, Coordinate movingFrom, Coordinate movingTo){
		
		boolean isInLineOfSight = false;
		
		if(movingTo.getX() > movingFrom.getX() && movingTo.getY() > movingFrom.getY()){ //TOP RIGHT
			
			for(int i = 1; movingFrom.getX() +i <8 && movingFrom.getY() +i <8; i ++){
				if(movingTo.getX() == movingFrom.getX() +i && movingTo.getY() == movingFrom.getY() +i) isInLineOfSight = true;
			}
			if(!isInLineOfSight) return false;
			
			
			for(int i = 1; i < movingTo.getX()- movingFrom.getX(); i ++){
				if(board.getPiece(new Coordinate(movingFrom.getX() +i, movingFrom.getY() +i)).getClass() != NoPiece.class) return false;
			}
			
			return true;
			
		}
		
		else if(movingTo.getX() < movingFrom.getX() && movingTo.getY() > movingFrom.getY()){ //TOP LEFT
			
			for(int i = 1; movingFrom.getX() -i >-1 && movingFrom.getY() +i <8; i ++){
				if(movingTo.getX() == movingFrom.getX() -i && movingTo.getY() == movingFrom.getY() +i) isInLineOfSight = true;
			}
			if(!isInLineOfSight) return false;
			
			
			
			for(int i = 1; i < movingFrom.getX()- movingTo.getX(); i ++){
				if(board.getPiece(new Coordinate(movingFrom.getX() - i, movingFrom.getY() +i)).getClass() != NoPiece.class) return false;
			}
			
			return true;
			
		}
		
		else if(movingTo.getX() < movingFrom.getX() && movingTo.getY() < movingFrom.getY()){ //BOTTOM LEFT
			
			for(int i = 1; movingFrom.getX() -i >-1 && movingFrom.getY() -i >-1; i ++){
				if(movingTo.getX() == movingFrom.getX() -i && movingTo.getY() == movingFrom.getY() -i) isInLineOfSight = true;
			}
			if(!isInLineOfSight) return false;
			
			
			
			for(int i = 1; i < movingFrom.getX()- movingTo.getX(); i ++){
				if(board.getPiece(new Coordinate(movingFrom.getX() - i, movingFrom.getY() -i)).getClass() != NoPiece.class) return false;
			}
			
			return true;
			
		}
		
		else if(movingTo.getX() > movingFrom.getX() && movingTo.getY() < movingFrom.getY()){ //BOTTOM RIGHT
			
			for(int i = 1; movingFrom.getX() +i <8 && movingFrom.getY() -i > -1; i ++){
				if(movingTo.getX() == movingFrom.getX() +i && movingTo.getY() == movingFrom.getY() -i) isInLineOfSight = true;
			}
			if(!isInLineOfSight) return false;
			
			
			for(int i = 1; i < movingTo.getX()- movingFrom.getX(); i ++){
				if(board.getPiece(new Coordinate(movingFrom.getX() +i, movingFrom.getY() -i)).getClass() != NoPiece.class) return false;
			}
			
			return true;
			
		}
		
		
		return false;
		
	}

}
