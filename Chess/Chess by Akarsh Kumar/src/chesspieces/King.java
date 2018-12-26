package chesspieces;

import game.*;
import util.*;

public class King extends ChessPiece {
	
	private final static String IMAGEPATH = "";

	public King(Coordinate coordinate, Side side) {
		super(coordinate, side);
		VALUE = 1000;
	}
	public King(King another){
		super(another);
		VALUE = 1000;
	}
	
	
	public static boolean isValidMoveForKing(Board board, Coordinate movingFrom, Coordinate movingTo){
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
