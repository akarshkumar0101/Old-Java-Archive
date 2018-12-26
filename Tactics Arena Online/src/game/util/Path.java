package game.util;

import java.util.ArrayList;
import java.util.List;

public class Path {
	private final byte[] startCoor;
	private byte[] endCoor;
	private final List<byte[]> pathCoordinates;

	public Path(byte... startCoor) {
		this(5, startCoor);
	}

	public Path(int len, byte... startCoor) {
		pathCoordinates = new ArrayList<byte[]>(len);
		this.startCoor = startCoor;
		endCoor = null;
	}

	public Path(Path another) {
		pathCoordinates = new ArrayList<byte[]>(another.pathCoordinates);
		// TODO careful when referring to same coordinate, a x coordinate change
		// will affect the other one
		startCoor = another.startCoor;
		endCoor = another.endCoor;
	}

	public void addCoordinate(byte... coor) {
		endCoor = coor;
		pathCoordinates.add(coor);
	}

	public void addPath(Path another) {
		for (byte[] coor : another.pathCoordinates) {
			pathCoordinates.add(coor);
		}
		endCoor = pathCoordinates.get(pathCoordinates.size() - 1);
	}

	public byte[] getStartCoor() {
		return startCoor;
	}

	public byte[] getEndCoor() {
		return endCoor;
	}

	public List<byte[]> getPathCoordinates() {
		return pathCoordinates;
	}
}
