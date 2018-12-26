package game.util;

public class CoordinateUtil {
	public static boolean isSame(byte[] coor1, byte... coor2) {
		if (coor1[0] == coor2[0] && coor1[1] == coor2[1])
			return true;
		return false;
	}

	public static byte[] shiftCoordinate(Direction dir, byte... coor) {
		byte[] newcoor = coor.clone();
		if (dir == Direction.RIGHT || dir == Direction.LEFT) {
			newcoor[0] += dir.toInt();
		} else if (dir == Direction.UP || dir == Direction.DOWN) {
			newcoor[1] += dir.toInt();
		}
		return newcoor;
	}

}
