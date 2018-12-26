package game.util;

import game.board.Board;
import game.unit.Unit;

public class PathFinder {
	public static Path getPath(Unit unit, byte... moveToCoor) {
		Board board = unit.getBoard();
		int moveRange = unit.getMoveRange();
		Path path = getPath(board, unit, moveRange, unit.getCoor(), moveToCoor, null, null);
		System.gc();
		return path;
	}

	private static Path getPath(Board board, Unit unit, int remainingMoveRange, byte[] from, byte[] to,
			Direction prevDir, Path pathConstructed) {
		if (remainingMoveRange == 0)
			return null;
		if (pathConstructed == null) {
			pathConstructed = new Path(from);
		}
		for (Direction dir : Direction.values()) {
			if (prevDir != null && dir != prevDir.getOpposite()) {
				Path path = new Path(pathConstructed);
				byte[] newcoor = CoordinateUtil.shiftCoordinate(dir, from);

				// TODO edit this to make it go through side step but not
				// enemies
				if (false) {
					continue;
				}

				path.addCoordinate(newcoor);
				if (CoordinateUtil.isSame(newcoor, to))
					return path;

				Path continuedPath = getPath(board, unit, remainingMoveRange - 1, newcoor, to, dir, path);
				if (continuedPath != null)
					return continuedPath;
			}
		}
		return null;
	}
}
