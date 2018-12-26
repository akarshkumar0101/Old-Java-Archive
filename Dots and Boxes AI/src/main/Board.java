package main;

import java.util.ArrayList;
import java.util.List;

public class Board {
	private final int numBoxesLength;
	private final int xlen, ylen;
	private final byte[][] board;

	// int[]{numBoxX, numBoxY, player}
	private final List<int[]> playerBoxes;
	public int player0numBoxes;
	public int player1numBoxes;

	public Board(int numBoxesLength) {
		this.numBoxesLength = numBoxesLength;
		xlen = numBoxesLength + 1;
		ylen = 2 * numBoxesLength + 1;
		board = new byte[xlen][ylen];
		for (int y = 0; y < ylen; y++) {
			for (int x = 0; x < xlen; x++) {
				board[x][y] = -1;
			}
		}

		playerBoxes = new ArrayList<>(2 * numBoxesLength * (numBoxesLength + 1));
		player0numBoxes = 0;
		player1numBoxes = 0;
	}

	public Board(Board another) {
		numBoxesLength = another.numBoxesLength;
		xlen = another.xlen;
		ylen = another.ylen;
		board = new byte[xlen][ylen];
		for (int y = 0; y < ylen; y++) {
			for (int x = 0; x < xlen; x++) {
				board[x][y] = another.board[x][y];
			}
		}
		playerBoxes = new ArrayList<>(another.playerBoxes);
		player0numBoxes = another.player0numBoxes;
		player1numBoxes = another.player1numBoxes;
	}

	public boolean put(int numBoxX, int numBoxY, int pos, int player) {
		int x = numBoxX;
		int y = 2 * numBoxY + 1;

		if (pos == 0) {
			y--;
		} else if (pos == 1) {
			x++;
		} else if (pos == 2) {
			y++;
		} else if (pos == 3) {

		}

		if (board[x][y] != -1)
			return false;
		board[x][y] = (byte) player;
		check(numBoxX, numBoxY, player);
		if (pos == 0) {
			check(numBoxX, numBoxY - 1, player);
		} else if (pos == 1) {
			check(numBoxX + 1, numBoxY, player);
		} else if (pos == 2) {
			check(numBoxX, numBoxY + 1, player);
		} else if (pos == 3) {
			check(numBoxX - 1, numBoxY, player);
		}
		return true;
	}

	private void check(int numBoxX, int numBoxY, int lastPlayer) {
		if (numBoxX < 0 || numBoxY < 0 || numBoxX > numBoxesLength - 1 || numBoxY > numBoxesLength - 1)
			return;
		if (getBoxStatus(numBoxX, numBoxY) != -1)
			return;
		int topx = numBoxX, topy = numBoxY * 2;
		int midy = numBoxY * 2 + 1, leftx = numBoxX, rightx = numBoxX + 1;
		int botx = numBoxX, boty = numBoxY * 2 + 2;

		if (board[topx][topy] != -1 && board[leftx][midy] != -1 && board[rightx][midy] != -1
				&& board[botx][boty] != -1) {
			playerBoxes.add(new int[] { numBoxX, numBoxY, lastPlayer });
			if (lastPlayer == 0) {
				player0numBoxes++;
			} else if (lastPlayer == 1) {
				player1numBoxes++;
			}
		}
	}

	public int getBoxStatus(int numBoxX, int numBoxY) {
		for (int[] arr : playerBoxes) {
			if (arr[0] == numBoxX && arr[1] == numBoxY)
				return arr[2];
		}
		return -1;
	}

	public List<Move> availableMoves() {
		List<Move> list = new ArrayList<>();
		for (int y = 0; y < ylen; y++) {
			int xmax = xlen;
			if (y % 2 == 0) {
				xmax--;
			}
			for (int x = 0; x < xmax; x++) {
				if (board[x][y] == -1) {
					int numBoxX = x, numBoxY = y / 2;
					list.add(new Move(new int[] { numBoxX, numBoxY, y % 2 == 0 ? 0 : 3 }));
				}
			}
		}
		return list;
	}

	public boolean isGameOver() {
		int numBoxes = numBoxesLength * numBoxesLength;
		if (player0numBoxes > numBoxes / 2 || player1numBoxes > numBoxes / 2 || playerBoxes.size() == numBoxes)
			return true;
		else
			return false;
	}

	public int getWinner() {
		if (player0numBoxes > player1numBoxes)
			return 0;
		else if (player1numBoxes > player0numBoxes)
			return 1;
		else
			return -1;
	}

	@Override
	public String toString() {
		String s = "";
		for (int y = 0; y < ylen; y++) {
			for (int x = 0; x < xlen; x++) {
				if (y % 2 == 0) {
					s += "*";
					if (board[x][y] != -1) {
						s += "---";
					} else {
						s += "   ";
					}
				} else {
					if (board[x][y] != -1) {
						s += "| ";
					} else {
						s += "  ";
					}
					int numBoxX = x, numBoxY = (y - 1) / 2;
					int player = getBoxStatus(numBoxX, numBoxY);
					s += player == -1 ? " " : player;
					s += " ";
				}
			}
			s += "\n";
		}
		return s;
	}
}

class Move {
	public int value;
	public int[] move;

	public Move(int[] move) {
		this.move = move;
	}

	@Override
	public String toString() {
		return move[0] + " " + move[1] + " " + move[2];
	}
}
