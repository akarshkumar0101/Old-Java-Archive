package chesspieces;

import javax.swing.*;
import game.*;
import util.*;

public abstract class ChessPiece {
	
	protected Coordinate coordinate;
	
	protected final Side side;
	
	protected boolean hasMoved;
	
	protected static int VALUE = 0;
	
	//CONST
	public ChessPiece(Coordinate coordinate, Side side){
		this.coordinate = coordinate;
		this.side = side;
		hasMoved = false;
	}
	//COPY CONST
	public ChessPiece(ChessPiece another){
		coordinate = another.getCoordinate();
		side = another.getSide();
		hasMoved = another.getHasMoved();
	}
	
	
	
	
	public Coordinate getCoordinate(){
		return coordinate;
	}

	public boolean getHasMoved(){
		return hasMoved;
	}
	
	public Side getSide(){
		return side;
	}
	
	@Override 
	public String toString(){
		
		return getSimpleName()+"@"+coordinate+ side + hasMoved;
		
	}

	public String getSimpleName(){
		return this.getClass().getSimpleName();
	}
	
	public void setCoordinate(Coordinate coordinate){
		this.coordinate = coordinate;
		if(!hasMoved) hasMoved = true;
	}
	
	public static boolean equals(ChessPiece a, ChessPiece b){
		if(Coordinate.equals(a.getCoordinate(),b.getCoordinate()) && 
				a.getSide() == b.getSide() && a.getHasMoved() == b.getHasMoved() && 
				a.getClass() == b.getClass()) return true;
		return false;
	}
	
	
	public static ImageIcon getImage(ChessPiece inp ){
		if(inp.getClass() == NoPiece.class)return null;
		return (new ImageIcon(inp.getClass().getResource("/images/"+inp.getSimpleName() +"" + inp.getSide() + ".png")));
	}
	
	
	

}
