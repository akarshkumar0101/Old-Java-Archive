package pieces;

import game.Board;
import util.Coordinate;

public class King extends ChessPiece {

	public King(Coordinate coordinate, boolean player) {
		super(coordinate, player);
	}
	public King(King another){
		super(another);
	}
	
	public boolean equals(King another){
		return super.equals(another);
	}
	
	public static boolean isValidMove(Board board, Coordinate movingFrom, Coordinate movingTo){
		for(int x = -1; x <2; x++){
			for(int y = -1; y <2; y++){
				if(x!=0 || y != 0){
					if(movingTo.getX() == movingFrom.getX() +x && movingTo.getY() == movingFrom.getY() +y) return true;
				}
			}
		}
		
		return false;
	}
	
}
