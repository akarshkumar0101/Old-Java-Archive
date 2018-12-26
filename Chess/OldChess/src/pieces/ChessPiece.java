package pieces;

import util.*;

import javax.swing.ImageIcon;

public abstract class ChessPiece {
	
	protected Coordinate coordinate;
	
	protected final boolean player;
	
	protected boolean hasMoved;
	
	
	
	public ChessPiece(Coordinate coordinate, boolean player){
		this.coordinate = new Coordinate(coordinate);
		this.player = player;
	}
	public ChessPiece(Coordinate coordinate){
		this.coordinate = new Coordinate(coordinate);
		player = false;
	}
	
	public ChessPiece(ChessPiece another){
		coordinate = new Coordinate(another.getCoordinate());
		player = another.getPlayer();
		hasMoved = another.getHasMoved();
	}
	
	public boolean equals(ChessPiece another){
		if(coordinate.equals(another.getCoordinate()) && player == another.getPlayer() && this.getClass() == another.getClass()) return true;
		return false;
	}
	
	
	public void setCoordinate(Coordinate coordinate){
		this.coordinate = coordinate;
	}
	
	
	public boolean getPlayer(){
		return player;
	}
	public Coordinate getCoordinate(){
		return coordinate;
	}
	
	public String getSimpleName(){
		return this.getClass().getSimpleName();
	}
	
	public boolean getHasMoved(){
		return hasMoved;
	}
	public void setHasMovedTrue(){
		hasMoved = true;
	}
	
	@Override 
	public String toString(){
		
		return getSimpleName()+"@"+coordinate.getX()+","+ coordinate.getY()+ player;
		
	}
	
	public ImageIcon getImage(){
		if(this.getClass() == NoPiece.class)return null;
		return (new ImageIcon(getClass().getResource("/images/"+getSimpleName() +"" + player + ".png")));
	}
	public static ImageIcon getImage(ChessPiece inp){
		return inp.getImage();
	}
	
}
