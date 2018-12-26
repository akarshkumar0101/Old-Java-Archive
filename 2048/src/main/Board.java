package main;

import java.util.ArrayList;

public class Board {

	private int[][] board;

	public Board() {
		board = new int[4][4];
	}

	public Board(Board inp) {
		board = new int[4][4];
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				board[x][y] = inp.get(x, y);
			}
		}
	}

	public boolean isEqualTo(Board inp) {

		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				if (get(x, y) != inp.get(x, y))
					return false;
			}
		}
		return true;
	}

	public void reset() {
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				set(x, y, 0);
			}

		}
	}

	public boolean isFull() {

		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				if (get(x, y) == 0)
					return false;
			}
		}
		return true;
	}

	public boolean hasEmpty() {
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				if (board[x][y] == 0)
					return true;
			}
		}
		return false;
	}

	public void generate() {
		if (isFull())
			return;

		int number = 0;
		if (Math.random() < .8) {
			number = 2;
		} else {
			number = 4;
		}

		while (true) {
			int x = (int) (Math.random() * 4);
			int y = (int) (Math.random() * 4);
			if (get(x, y) == 0) {
				set(x, y, number);
				return;
			}
		}

	}

	public int get(int x, int y) {
		return board[x][y];
	}

	public void set(int x, int y, int value) {
		board[x][y] = value;
	}

	public void display() {
		System.out.println(myToString());
	}

	public String myToString() {
		String result = "";

		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				result = result + board[x][y] + " ";
			}
			result = result + "\n";
		}

		return result;
	}

	public int moveRight() {

		int result = 0;

		for (int y = 0; y < 4; y++) {

			ArrayList<Integer> location = new ArrayList<Integer>();
			int numberOfStuff = 0;

			for (int x = 3; x >= 0; x--) {
				if (get(x, y) != 0) {
					numberOfStuff++;
					location.add(x);
				}
			}

			if (numberOfStuff == 0) {
				continue;
			}
			if (numberOfStuff == 1) {
				set(3, y, get(location.get(0), y));
				set(2, y, 0);
				set(1, y, 0);
				set(0, y, 0);
				continue;
			}

			if (numberOfStuff == 2) {
				if (get(location.get(0), y) == get(location.get(1), y)) {
					result += (get(location.get(0), y) * 2);
					set(3, y, get(location.get(0), y) * 2);
					set(2, y, 0);
					set(1, y, 0);
					set(0, y, 0);
				} else {
					set(3, y, get(location.get(0), y));
					set(2, y, get(location.get(1), y));
					set(1, y, 0);
					set(0, y, 0);
				}
				continue;
			}

			if (numberOfStuff == 3) {
				if (get(location.get(0), y) == get(location.get(1), y)) {
					result += (get(location.get(0), y) * 2);
					set(3, y, get(location.get(0), y) * 2);
					set(2, y, get(location.get(2), y));
					set(1, y, 0);
					set(0, y, 0);
				} else if (get(location.get(1), y) == get(location.get(2), y)) {
					result += (get(location.get(1), y) * 2);
					set(3, y, get(location.get(0), y));
					set(2, y, get(location.get(1), y) * 2);
					set(1, y, 0);
					set(0, y, 0);
				} else {
					set(3, y, get(location.get(0), y));
					set(2, y, get(location.get(1), y));
					set(1, y, get(location.get(2), y));
					set(0, y, 0);
				}

				continue;
			}
			if (numberOfStuff == 4) {
				if ((get(3, y) == get(2, y)) && (get(1, y) == get(0, y))) {
					result += (get(3, y) * 2);
					result += (get(1, y) * 2);
					set(3, y, get(3, y) * 2);
					set(2, y, get(1, y) * 2);
					set(1, y, 0);
					set(0, y, 0);

				} else if ((get(3, y) == get(2, y)) && (get(1, y) != get(0, y))) {
					result += (get(3, y) * 2);
					set(3, y, get(3, y) * 2);
					set(2, y, get(1, y));
					set(1, y, get(0, y));
					set(0, y, 0);
				} else if (get(2, y) == get(1, y)) {
					result += (get(2, y) * 2);
					set(2, y, get(2, y) * 2);
					set(1, y, get(0, y));
					set(0, y, 0);

				} else if ((get(3, y) != get(2, y)) && (get(1, y) == get(0, y))) {
					result += (get(1, y) * 2);
					set(1, y, get(1, y) * 2);
					set(0, y, 0);
				}

				continue;
			}

		}

		return result;

	}

	public int moveLeft() {

		int result = 0;

		for (int y = 0; y < 4; y++) {

			ArrayList<Integer> location = new ArrayList<Integer>();
			int numberOfStuff = 0;

			for (int x = 0; x < 4; x++) {
				if (get(x, y) != 0) {
					numberOfStuff++;
					location.add(x);
				}
			}

			if (numberOfStuff == 0) {
				continue;
			}
			if (numberOfStuff == 1) {
				set(0, y, get(location.get(0), y));
				set(1, y, 0);
				set(2, y, 0);
				set(3, y, 0);
				continue;
			}

			if (numberOfStuff == 2) {
				if (get(location.get(0), y) == get(location.get(1), y)) {
					result += (get(location.get(0), y) * 2);
					set(0, y, get(location.get(0), y) * 2);
					set(1, y, 0);
					set(2, y, 0);
					set(3, y, 0);
				} else {
					set(0, y, get(location.get(0), y));
					set(1, y, get(location.get(1), y));
					set(2, y, 0);
					set(3, y, 0);
				}
				continue;
			}

			if (numberOfStuff == 3) {
				if (get(location.get(0), y) == get(location.get(1), y)) {
					result += (get(location.get(0), y) * 2);
					set(0, y, get(location.get(0), y) * 2);
					set(1, y, get(location.get(2), y));
					set(2, y, 0);
					set(3, y, 0);
				} else if (get(location.get(1), y) == get(location.get(2), y)) {
					result += (get(location.get(1), y) * 2);
					set(0, y, get(location.get(0), y));
					set(1, y, get(location.get(1), y) * 2);
					set(2, y, 0);
					set(3, y, 0);
				} else {
					set(0, y, get(location.get(0), y));
					set(1, y, get(location.get(1), y));
					set(2, y, get(location.get(2), y));
					set(3, y, 0);
				}

				continue;
			}
			if (numberOfStuff == 4) {
				if ((get(3, y) == get(2, y)) && (get(1, y) == get(0, y))) {
					result += (get(3, y) * 2);
					result += (get(1, y) * 2);
					set(0, y, get(0, y) * 2);
					set(1, y, get(2, y) * 2);
					set(2, y, 0);
					set(3, y, 0);

				}

				else if ((get(3, y) != get(2, y)) && (get(1, y) == get(0, y))) {
					result += (get(1, y) * 2);
					set(0, y, get(0, y) * 2);
					set(1, y, get(2, y));
					set(2, y, get(3, y));
					set(3, y, 0);
				}

				else if (get(2, y) == get(1, y)) {
					result += (get(2, y) * 2);
					set(1, y, get(1, y) * 2);
					set(2, y, get(3, y));
					set(3, y, 0);

				} else if ((get(3, y) == get(2, y)) && (get(1, y) != get(0, y))) {
					result += (get(3, y) * 2);
					set(2, y, get(2, y) * 2);
					set(3, y, 0);
				}

				continue;
			}

		}

		return result;

	}

	public int moveUp() {

		int result = 0;

		for (int x = 0; x < 4; x++) {

			ArrayList<Integer> location = new ArrayList<Integer>();
			int numberOfStuff = 0;

			for (int y = 0; y < 4; y++) {
				if (get(x, y) != 0) {
					numberOfStuff++;
					location.add(y);
				}
			}

			if (numberOfStuff == 0) {
				continue;
			}
			if (numberOfStuff == 1) {
				set(x, 0, get(x, location.get(0)));
				set(x, 1, 0);
				set(x, 2, 0);
				set(x, 3, 0);
				continue;
			}

			if (numberOfStuff == 2) {
				if (get(x, location.get(0)) == get(x, location.get(1))) {
					result += (get(x, location.get(0)) * 2);
					set(x, 0, get(x, location.get(0)) * 2);
					set(x, 1, 0);
					set(x, 2, 0);
					set(x, 3, 0);
				} else {
					set(x, 0, get(x, location.get(0)));
					set(x, 1, get(x, location.get(1)));
					set(x, 2, 0);
					set(x, 3, 0);
				}
				continue;
			}

			if (numberOfStuff == 3) {
				if (get(x, location.get(0)) == get(x, location.get(1))) {
					result += (get(x, location.get(0)) * 2);
					set(x, 0, get(x, location.get(0)) * 2);
					set(x, 1, get(x, location.get(2)));
					set(x, 2, 0);
					set(x, 3, 0);
				} else if (get(x, location.get(1)) == get(x, location.get(2))) {
					result += (get(x, location.get(1)) * 2);
					set(x, 0, get(x, location.get(0)));
					set(x, 1, get(x, location.get(1)) * 2);
					set(x, 2, 0);
					set(x, 3, 0);
				} else {
					set(x, 0, get(x, location.get(0)));
					set(x, 1, get(x, location.get(1)));
					set(x, 2, get(x, location.get(2)));
					set(x, 3, 0);
				}

				continue;
			}
			if (numberOfStuff == 4) {
				if ((get(x, 3) == get(x, 2)) && (get(x, 1) == get(x, 0))) {
					result += (get(x, 2) * 2);
					result += (get(x, 0) * 2);
					set(x, 0, get(x, 0) * 2);
					set(x, 1, get(x, 2) * 2);
					set(x, 2, 0);
					set(x, 3, 0);

				}

				else if ((get(x, 3) != get(x, 2)) && (get(x, 1) == get(x, 0))) {
					result += (get(x, 0) * 2);
					set(x, 0, get(x, 0) * 2);
					set(x, 1, get(x, 2));
					set(x, 2, get(x, 3));
					set(x, 3, 0);
				} else if (get(x, 2) == get(x, 1)) {
					result += (get(x, 1) * 2);
					set(x, 1, get(x, 1) * 2);
					set(x, 2, get(x, 3));
					set(x, 3, 0);

				} else if ((get(x, 3) == get(x, 2)) && (get(x, 1) != get(x, 0))) {
					result += (get(x, 2) * 2);
					set(x, 2, get(x, 2) * 2);
					set(x, 3, 0);
				}

				continue;
			}

		}

		return result;

	}

	public int moveDown() {

		int result = 0;

		for (int x = 0; x < 4; x++) {

			ArrayList<Integer> location = new ArrayList<Integer>();
			int numberOfStuff = 0;

			for (int y = 3; y >= 0; y--) {
				if (get(x, y) != 0) {
					numberOfStuff++;
					location.add(y);
				}
			}

			if (numberOfStuff == 0) {
				continue;
			}
			if (numberOfStuff == 1) {
				set(x, 3, get(x, location.get(0)));
				set(x, 2, 0);
				set(x, 1, 0);
				set(x, 0, 0);
				continue;
			}

			if (numberOfStuff == 2) {
				if (get(x, location.get(0)) == get(x, location.get(1))) {
					result += (get(x, location.get(0)) * 2);
					set(x, 3, get(x, location.get(0)) * 2);
					set(x, 2, 0);
					set(x, 1, 0);
					set(x, 0, 0);
				} else {
					set(x, 3, get(x, location.get(0)));
					set(x, 2, get(x, location.get(1)));
					set(x, 1, 0);
					set(x, 0, 0);
				}
				continue;
			}

			if (numberOfStuff == 3) {
				if (get(x, location.get(0)) == get(x, location.get(1))) {
					result += (get(x, location.get(0)) * 2);
					set(x, 3, get(x, location.get(0)) * 2);
					set(x, 2, get(x, location.get(2)));
					set(x, 1, 0);
					set(x, 0, 0);
				} else if (get(x, location.get(1)) == get(x, location.get(2))) {
					result += (get(x, location.get(1)) * 2);
					set(x, 3, get(x, location.get(0)));
					set(x, 2, get(x, location.get(1)) * 2);
					set(x, 1, 0);
					set(x, 0, 0);
				} else {
					set(x, 3, get(x, location.get(0)));
					set(x, 2, get(x, location.get(1)));
					set(x, 1, get(x, location.get(2)));
					set(x, 0, 0);
				}

				continue;
			}
			if (numberOfStuff == 4) {
				if ((get(x, 3) == get(x, 2)) && (get(x, 1) == get(x, 0))) {
					result += (get(x, 3) * 2);
					result += (get(x, 1) * 2);
					set(x, 3, get(x, 3) * 2);
					set(x, 2, get(x, 1) * 2);
					set(x, 1, 0);
					set(x, 0, 0);

				} else if ((get(x, 3) == get(x, 2)) && (get(x, 1) != get(x, 0))) {
					result += (get(x, 3) * 2);
					set(x, 3, get(x, 3) * 2);
					set(x, 2, get(x, 1));
					set(x, 1, get(x, 0));
					set(x, 0, 0);
				}

				else if (get(x, 2) == get(x, 1)) {
					result += (get(x, 1) * 2);
					set(x, 2, get(x, 2) * 2);
					set(x, 1, get(x, 0));
					set(x, 0, 0);

				} else if ((get(x, 3) != get(x, 2)) && (get(x, 1) == get(x, 0))) {

					result += (get(x, 1) * 2);
					set(x, 1, get(x, 1) * 2);
					set(x, 0, 0);
				}

				continue;
			}

		}

		return result;

	}

	public boolean canMoveRight() {
		Board temp = new Board(this);
		temp.moveRight();
		if (temp.isEqualTo(this))
			return false;
		return true;
	}

	public boolean canMoveLeft() {
		Board temp = new Board(this);
		temp.moveLeft();
		if (temp.isEqualTo(this))
			return false;
		return true;
	}

	public boolean canMoveUp() {
		Board temp = new Board(this);
		temp.moveUp();
		if (temp.isEqualTo(this))
			return false;
		return true;
	}

	public boolean canMoveDown() {
		Board temp = new Board(this);
		temp.moveDown();
		if (temp.isEqualTo(this))
			return false;
		return true;
	}

}
