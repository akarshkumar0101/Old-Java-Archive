package util;

public class Coordinate{
	private int x;
	private int y;
	
	public Coordinate(int x, int y){
		this.x = x;
		this.y = y;
	}
	public Coordinate(Coordinate another){
		x = another.getX();
		y = another.getY();
	}
	
	public boolean equals(Coordinate another){
		if(x == another.getX() && y == another.getY()) return true;
		return false;
	}
	
	public boolean isValid(){
		if(x>=0 && x <8 && y>=0 && y <8) return true;
		return false;
	}
	
	public void set(int x,int y){
		this.x = x;
		this.y = y;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	
	public static boolean isInRange(Coordinate coordinate){
		return isInRange(coordinate.getX(), coordinate.getY());
	}
	public static boolean isInRange(int x, int y){
		if(x >-1 && x <8 && y >-1 && y <8) return true;
		return false;
	}
	
}
