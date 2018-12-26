package piece;

import game.*;

import java.util.*;

import util.*;
import javax.swing.*;

public abstract class Piece {

	protected Coordinate coordinate;
	protected Side side;
	protected ArrayList<Coordinate> locationHistory;
	

	public Piece(Coordinate c, Side s) {
		coordinate = c;
		side = s;
		locationHistory = new ArrayList<Coordinate>();
		locationHistory.add(coordinate);
	}

	public Piece(Coordinate c) {
		this(c, Side.NEUTRAL);
	}

	// copy constructor
	public Piece(Piece another) { // COPY ALL PROPERTIES
		coordinate = another.getCoordinate();
		side = another.getSide();
		locationHistory = new ArrayList<Coordinate>(
				another.getLocationHistory());
	}
	
	public int getValue(){
		if(this.getClass() == Space.class) return 0;
		if(this.getClass() == Pawn.class) return 1;
		if(this.getClass() == Rook.class) return 5;
		if(this.getClass() == Knight.class) return 3;
		if(this.getClass() == Bishop.class) return 3;
		if(this.getClass() == Queen.class) return 9;
		if(this.getClass() == King.class) return -1;
		return 0;
	}

	public boolean equals(Piece another) { // DOES NOT COMPARE LOCATION HISTORY
		if (coordinate == another.getCoordinate() && side == another.getSide()
				&& this.getClass() == another.getClass())
			return true;
		return false;
	}

	public void moveTo(Coordinate inp) {
		coordinate = inp;
		locationHistory.add(coordinate);
	}

	public void setCoordinate(Coordinate inp) {
		coordinate = inp;
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}

	public Side getSide() {
		return side;
	}

	public ArrayList<Coordinate> getLocationHistory() {
		return locationHistory;
	}

	public boolean hasMoved() {
		return (locationHistory.size() > 1);
	}

	public static boolean isValidMove(Board board, Move move) { //JUST CHECKS PIECE AND SURROUNDINGS TO SEE IF PIECE IS ALLOWED TO MOVE THERE BY RULES
		Piece first = board.getPiece(move.getMovingFrom());
		Piece second = board.getPiece(move.getMovingTo());

		if (first.getSide() == second.getSide())
			return false;

		if (first.getClass() == Space.class)
			return false;
		if (first.getClass() == Pawn.class)
			return Pawn.ivm(board, move);
		if (first.getClass() == Rook.class)
			return Rook.ivm(board, move);
		if (first.getClass() == Knight.class)
			return Knight.ivm(board, move);
		if (first.getClass() == Bishop.class)
			return Bishop.ivm(board, move);
		if (first.getClass() == King.class)
			return King.ivm(board, move);
		if (first.getClass() == Queen.class)
			return Queen.ivm(board, move);

		return false;
	}

	@Override
	public String toString() {
		return side + this.getClass().getSimpleName() + "@" + coordinate;

	}
	
	  public ImageIcon getImage(){ //NEED TO GET FROM A DIMENSION INPUT//////////////////////////////////////
		  if(this.getClass() == Space.class)return null;
		  String s = this.toString();
		  return (new ImageIcon(getClass().getResource("/images/"+side + getClass().getSimpleName() +".png")));
	  } 
	  public static ImageIcon getImage(Piece inp){
			  return inp.getImage(); 
	  }
	 

}
