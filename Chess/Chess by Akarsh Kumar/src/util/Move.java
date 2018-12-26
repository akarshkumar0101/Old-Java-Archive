package util;

public class Move {
	
	private Coordinate movingFrom;
	private Coordinate movingTo;
	
	public Move(Coordinate movingFrom, Coordinate movingTo){
		this.movingFrom = movingFrom;
		this.movingTo = movingTo;
	}
	public Move(Coordinate inp){
		movingFrom = inp;
	}
	
	public void setMovingFrom(Coordinate inp){
		movingFrom = inp;
	}
	public void setMovingTo(Coordinate inp){
		movingTo = inp;
	}
	
	public Coordinate getMovingFrom(){
		return movingFrom;
	}
	public Coordinate getMovingTo(){
		return movingTo;
	}
	

}
