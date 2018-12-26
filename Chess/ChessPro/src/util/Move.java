package util;

public class Move {
	private final Coordinate movingFrom;
	private final Coordinate movingTo;
	
	public Move(Coordinate inp1, Coordinate inp2){
		movingFrom = inp1;
		movingTo = inp2;
	}
	//copy constructor
	public Move(Move inp){
		movingFrom = inp.getMovingFrom();
		movingTo = inp.getMovingTo();
	}
	
	public Coordinate getMovingFrom(){
		return movingFrom;
	}
	public Coordinate getMovingTo(){
		return movingTo;
	}
	

}
