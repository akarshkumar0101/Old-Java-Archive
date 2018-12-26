package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// this saves blanks as -1 and id of 0 as 0
public class Board {

	// room id stored in this
	private final int[][] grid;
	private final Map<Integer, int[]> roomLocations;

	private final Map<Player, int[]> playerLocations;

	public Board(int[][] board) {
		grid = board;
		roomLocations = new HashMap<>();

		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board.length; x++) {
				if (grid[x][y] != -1)
					roomLocations.put(grid[x][y], new int[] { x, y });
			}
		}

		playerLocations = new HashMap<>();
	}

	public int[][] getGrid() {
		return grid;
	}

	public void putPlayerAt(Player player, int... coordinate) {
		playerLocations.put(player, coordinate);
	}

	public int[] locationOf(Player player) {
		return playerLocations.get(player);
	}

	public int[] locationOfRoom(int roomID) {
		return roomLocations.get(roomID);
	}

	public int getRoomID(int... coor) {
		return grid[coor[0]][coor[1]];
	}

	public Unit getRoom(int... coor) {
		int id = getRoomID(coor);
		if (id == -1)
			throw new RuntimeException();
		Unit room = Unit.getUnit(Type.ROOM, id);
		return room;
	}

	public void permanentlyRemovePlayer(Player player) {
		playerLocations.put(player, null);
		playerLocations.remove(player);
	}

	public List<int[]> roomsInRangeOf(int range, int... coor) {
		List<int[]> result = new ArrayList<>(4);

		for (int x = coor[0] - range; x <= coor[0] + range; x++) {
			for (int y = coor[1] - range; y <= coor[1] + range; y++) {
				if (dist(x, y, coor[0], coor[1]) > range)
					continue;
				if (!isInBoard(coor))
					continue;
				if (grid[x][y] != -1) {
					result.add(new int[] { x, y });
				}
			}
		}

		return result;
	}

	public boolean isInBoard(int... coor) {
		if (coor[0] >= 0 && coor[0] < grid.length && coor[1] >= 0 && coor[1] < grid[0].length) {
			return true;
		} else
			return false;
	}

	public static int dist(int x, int y, int newx, int newy) {
		return Math.abs(newx - x) + Math.abs(newy - y);
	}

	public static double absDist(int x, int y, int newx, int newy) {
		return Math.sqrt(Math.pow(newx - x, 2) + Math.pow(newy - y, 2));
	}
}
