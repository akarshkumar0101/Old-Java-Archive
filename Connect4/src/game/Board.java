package game;

public class Board {
	public static final int WIDTH = 7;
	public static final int HEIGHT = 6;

	public static final int RED = 1;
	public static final int YELLOW = -1;
	public static final int NONE = 0;

	private final int[][] grid;

	private int currentPlayer;

	public Board() {
		grid = new int[WIDTH][HEIGHT];
		currentPlayer = RED;
	}

	public Board(Board another) {
		grid = new int[WIDTH][HEIGHT];
		for (int x = 0; x < WIDTH; x++) {
			int piece = NONE;
			int y = 0;
			do {
				piece = another.grid[x][y];
				grid[x][y] = piece;
				y++;
			} while (piece != NONE && y < HEIGHT);
		}
		currentPlayer = another.currentPlayer;
	}

	public void reset() {
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				grid[x][y] = 0;
			}
		}
		currentPlayer = RED;
	}

	public int get(int x, int y) {
		return grid[x][y];
	}

	public void set(int x, int y, int value) {
		if (value > RED || value < YELLOW)
			throw new RuntimeException("Value sent to this function was out of range");
		grid[x][y] = value;
	}

	public boolean canPut(int col) {
		if (grid[col][HEIGHT - 1] == NONE)
			return true;
		return false;
	}

	public void put(int col) {
		for (int y = 0; y < HEIGHT; y++) {
			if (grid[col][y] == 0) {
				grid[col][y] = currentPlayer;
				currentPlayer *= -1;
				return;
			}
		}
		throw new RuntimeException("Stopping from ruining algorithm by accidently putting in filled column");
	}

	public void doMove(Move move) {
		put(move.getColumn());
	}

	public static char getChar(int piece) {
		if (piece == NONE)
			return ' ';
		else if (piece == RED)
			return 'R';
		else if (piece == YELLOW)
			return 'Y';
		else
			throw new RuntimeException("Invalid piece");
	}

	public void displayInConsole() {
		for (int y = HEIGHT - 1; y >= 0; y--) {
			for (int x = 0; x < WIDTH; x++) {
				System.out.print('|' + "" + getChar(grid[x][y]));
			}
			System.out.println('|');
		}
		System.out.println();
	}

	public int getWinner() {
		// horizontal
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH - 3; x++) {
				if (grid[x][y] == NONE) {
					continue;
				}
				for (int i = 1; i < 4; i++) {
					if (grid[x + i][y] != grid[x][y]) {
						break;
					}
					if (i == 3)
						return grid[x][y];
				}
			}
		}
		// vertical
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT - 3; y++) {
				if (grid[x][y] == NONE) {
					continue;
				}
				for (int i = 1; i < 4; i++) {
					if (grid[x][y + i] != grid[x][y]) {
						break;
					}
					if (i == 3)
						return grid[x][y];
				}
			}
		}
		// diagonal 1 = /
		for (int x = 0; x < WIDTH - 3; x++) {
			for (int y = 0; y < HEIGHT - 3; y++) {
				if (grid[x][y] == NONE) {
					continue;
				}
				for (int i = 1; i < 4; i++) {
					if (grid[x + i][y + i] != grid[x][y]) {
						break;
					}
					if (i == 3)
						return grid[x][y];
				}
			}
		}
		// diagonal 2 = \
		for (int x = 0; x < WIDTH - 3; x++) {
			for (int y = 3; y < HEIGHT; y++) {
				if (grid[x][y] == NONE) {
					continue;
				}
				for (int i = 1; i < 4; i++) {
					if (grid[x + i][y - i] != grid[x][y]) {
						break;
					}
					if (i == 3)
						return grid[x][y];
				}
			}
		}

		return 0;
	}

	public boolean isFull() {
		for (int x = 0; x < WIDTH; x++) {
			if (grid[x][HEIGHT - 1] == NONE)
				return false;
		}
		return true;
	}

}
