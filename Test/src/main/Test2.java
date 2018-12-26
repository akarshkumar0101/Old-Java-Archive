package main;

import java.util.ArrayList;
import java.util.List;

public class Test2 {

	public static void main(String[] args) {
		List<Coordinate> questions = new ArrayList<>();

		for (int i = 0; i < 30; i++) {
			Coordinate coor = null;
			boolean run = false;
			do {
				coor = new Coordinate((int) (Math.random() * 10), (int) (Math.random() * 10));
				if (questions.contains(coor)) {
					run = true;
				}
				if (coor.x == 0 && coor.y % 2 == 0) {
					run = true;
				}
			} while (false);
			questions.add(coor);
		}
		for (int y = 9; y >= 0; y--) {
			for (int x = 0; x < 10; x++) {
				boolean question = questions.contains(new Coordinate(x, y));
				System.out.print("|" + (question ? "?" : " "));
			}
			System.out.println("|");
		}
	}

}

class Coordinate {
	int x, y;

	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean equals(Object another_) {
		Coordinate another = (Coordinate) another_;
		return x == another.x && y == another.y;
	}
}