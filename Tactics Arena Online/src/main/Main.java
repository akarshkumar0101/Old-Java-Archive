package main;

import game.board.Board;
import game.board.NormalBoard;
import game.unit.Knight;
import game.unit.Unit;
import game.util.Direction;

public class Main {

	public static void main(String[] args) {
		Board board = new NormalBoard();
		Unit unit = new Knight(board, Direction.LEFT, (byte) 4, (byte) 4);
		board.getSquare((byte) 4, (byte) 4).setUnit(unit);
		for (byte y = -1; y < 12; y++) {
			for (byte x = -1; x < 12; x++) {
				if (!board.isInBoard(x, y)) {
					System.out.print("X ");
				} else {
					System.out.print("* ");
				}
			}
			System.out.println();
		}
	}

}
