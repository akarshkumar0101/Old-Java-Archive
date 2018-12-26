package chesspieces;

import game.*;
import util.*;

public class Knight extends ChessPiece {
	
	private final static String IMAGEPATH = "";

	public Knight(Coordinate coordinate, Side side) {
		super(coordinate, side);
		VALUE = 3;
	}
	public Knight(Knight another){
		super(another);
		VALUE = 3;
	}
	
	
	public static boolean isValidMoveForKnight(Board board, Coordinate movingFrom, Coordinate movingTo){
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
