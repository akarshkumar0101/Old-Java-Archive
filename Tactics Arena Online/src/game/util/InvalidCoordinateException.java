package game.util;

public class InvalidCoordinateException extends RuntimeException {

	private static final long serialVersionUID = -6037382486174350695L;

	private final byte[] coor;

	public InvalidCoordinateException(byte... coor) {
		super("Invalid coordinate: {" + coor[0] + ", " + coor[1] + "}");
		this.coor = coor;
	}

	public byte[] getCoordinate() {
		return coor;
	}
}
