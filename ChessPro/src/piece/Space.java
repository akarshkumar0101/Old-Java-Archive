package piece;

import util.*;

public class Space extends Piece {
	
	
	public Space(Coordinate c) {
		super(c);
	}

	public Space(Piece another) {
		this(another.getCoordinate());
	}
}
