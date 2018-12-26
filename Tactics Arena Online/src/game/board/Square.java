package game.board;

import game.unit.Unit;

public class Square {

	private final byte[] coor;

	private Unit unit;

	public Square(Unit unit, byte... coor) {
		this.coor = coor;
		this.unit = unit;
	}

	public Square(byte... coor) {
		this(null, coor);
	}

	public boolean isEmpty() {
		return (unit == null);
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public byte[] getCoordinate() {
		return coor;
	}

}
