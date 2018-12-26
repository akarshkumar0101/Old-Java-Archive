package util;

public abstract class Coordinate {
	
	private int x;
	private int y;
	
	private static final Coordinate[][] COORDINATES = new Coordinate[][]{
		{
			new Coordinate(0,0){}, new Coordinate(0,1){},new Coordinate(0,2){}, new Coordinate(0,3){},
			new Coordinate(0,4){}, new Coordinate(0,5){},new Coordinate(0,6){}, new Coordinate(0,7){}
		},
		{
			new Coordinate(1,0){}, new Coordinate(1,1){},new Coordinate(1,2){}, new Coordinate(1,3){},
			new Coordinate(1,4){}, new Coordinate(1,5){},new Coordinate(1,6){}, new Coordinate(1,7){}
		},
		{
			new Coordinate(2,0){}, new Coordinate(2,1){},new Coordinate(2,2){}, new Coordinate(2,3){},
			new Coordinate(2,4){}, new Coordinate(2,5){},new Coordinate(2,6){}, new Coordinate(2,7){}
		},
		{
			new Coordinate(3,0){}, new Coordinate(3,1){},new Coordinate(3,2){}, new Coordinate(3,3){},
			new Coordinate(3,4){}, new Coordinate(3,5){},new Coordinate(3,6){}, new Coordinate(3,7){}
		},
		{
			new Coordinate(4,0){}, new Coordinate(4,1){},new Coordinate(4,2){}, new Coordinate(4,3){},
			new Coordinate(4,4){}, new Coordinate(4,5){},new Coordinate(4,6){}, new Coordinate(4,7){}
		},
		{
			new Coordinate(5,0){}, new Coordinate(5,1){},new Coordinate(5,2){}, new Coordinate(5,3){},
			new Coordinate(5,4){}, new Coordinate(5,5){},new Coordinate(5,6){}, new Coordinate(5,7){}
		},
		{
			new Coordinate(6,0){}, new Coordinate(6,1){},new Coordinate(6,2){}, new Coordinate(6,3){},
			new Coordinate(6,4){}, new Coordinate(6,5){},new Coordinate(6,6){}, new Coordinate(6,7){}
		},
		{
			new Coordinate(7,0){}, new Coordinate(7,1){},new Coordinate(7,2){}, new Coordinate(7,3){},
			new Coordinate(7,4){}, new Coordinate(7,5){},new Coordinate(7,6){}, new Coordinate(7,7){}
		}
	};
	
	
	private Coordinate(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public static Coordinate getCoordinate(int x, int y){
		try{
			return COORDINATES[x][y];
		}catch(ArrayIndexOutOfBoundsException e){
			return null;
		}
	}
	
	public static boolean equals(Coordinate a, Coordinate b){
		if(a.getX() == b.getX() && a.getY() == b.getY()) return true;
		return false;
	}
	
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	@Override
	public String toString(){
		return "("+x+"," +y +")";
	}
	
	
	
}
