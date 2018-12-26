package pieces;

import util.Coordinate;

public class NoPiece extends ChessPiece {

	public NoPiece(Coordinate coordinate) {
		super(coordinate);
		
	}
	public NoPiece(NoPiece another){
		super(another.getCoordinate());
	}
	
	public boolean equals(NoPiece another){
		return super.equals(another);
	}
	
}
