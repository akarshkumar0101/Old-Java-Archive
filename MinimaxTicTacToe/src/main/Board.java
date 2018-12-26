package main;

public class Board {

	private final int[][] grid = new int[3][3];
	private int currentplay;

	public Board() {
		reset();
	}

	public Board(Board another) {
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				grid[x][y] = another.grid[x][y];
			}
		}
		currentplay = another.currentplay;
	}

	public int[][] getGrid() {
		return grid;
	}

	public boolean canPlace(int x, int y) {
		if (grid[x][y] == TicTacToe.NONE)
			return true;
		return false;
	}

	public void set(int x, int y, int play) {
		grid[x][y] = play;
	}

	public void put(int x, int y) {
		grid[x][y] = currentplay;
		currentplay *= -1;
	}

	public void remove(int x, int y) {
		grid[x][y] = TicTacToe.NONE;
	}

	public int get(int x, int y) {
		return grid[x][y];
	}

	public int[][] getPossibleMoves() {
		int num = 0;
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				if (grid[x][y] == TicTacToe.NONE) {
					num++;
				}
			}
		}
		int[][] moves = new int[num][2];
		num = 0;
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				if (grid[x][y] == TicTacToe.NONE) {
					moves[num][0] = x;
					moves[num][1] = y;
					num++;
				}
			}
		}
		return moves;
	}

	public void displayInConsole() {
		for (int y = 2; y >= 0; y--) {
			for (int x = 0; x < 3; x++) {
				System.out.print('|');
				System.out.print(grid[x][y] == TicTacToe.PLAYX ? 'X' : (grid[x][y] == TicTacToe.PLAYO ? 'O' : ' '));
			}
			System.out.println('|');
		}
		System.out.println();
	}

	public void reset() {
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				grid[x][y] = 0;
			}
		}
		currentplay = TicTacToe.PLAYX;
	}

	public int getWinner() {
		// HORIZONTAL
		for (int y = 0; y < 3; y++) {
			int first = grid[0][y];
			if (first == TicTacToe.NONE) {
				continue;
			}
			for (int x = 1; x < 3; x++) {
				if (grid[x][y] != first) {
					break;
				}
				if (x == 2)
					return first;
			}
		}
		// VERTICAL
		for (int x = 0; x < 3; x++) {
			int first = grid[x][0];
			if (first == TicTacToe.NONE) {
				continue;
			}
			for (int y = 1; y < 3; y++) {
				if (grid[x][y] != first) {
					break;
				}
				if (y == 2)
					return first;
			}
		}
		// DIAGONAL
		if (grid[0][0] != TicTacToe.NONE && grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2])
			return grid[0][0];
		else if (grid[0][2] != TicTacToe.NONE && grid[0][2] == grid[1][1] && grid[1][1] == grid[2][0])
			return grid[0][2];

		else
			return TicTacToe.NONE;
	}

	public boolean hasWinner() {
		if (isFull())
			return true;
		return getWinner() != TicTacToe.NONE;
	}

	public boolean isFull() {
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				if (grid[x][y] == TicTacToe.NONE)
					return false;
			}
		}
		return true;
	}
}
/*
 * 
 * X X X ^ X X X | X X X | ---->
 */
