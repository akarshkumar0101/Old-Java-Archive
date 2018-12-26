package pieces;

import game.Board;
import util.*;

public class Knight extends ChessPiece {

	public Knight(Coordinate coordinate, boolean player) {
		super(coordinate, player);
	}
	public Knight(Knight another){
		super(another);
	}
	
	public boolean equals(Knight another){
		return super.equals(another);
	}
	
	public static boolean isValidMove(Board board, Coordinate movingFrom, Coordinate movingTo){
		for(int x = -2; x <=2 ; x++){
			for(int y = -2; y <=2 ; y++){
				if(Math.abs(x) != Math.abs(y) && x!=0 && y!= 0){
					if(movingTo.getX() ==movingFrom.getX() +x && movingTo.getY() == movingFrom.getY() +y) return true;
				}
			}
		}
		
		
		
		return false;
	}
	
}
