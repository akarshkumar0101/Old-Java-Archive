package main;

import java.util.List;
import java.util.Scanner;

public class DotsAndBoxesAIMain {

	public static void main(String[] args) {
		Board board = new Board(2);
		board.put(0, 0, 1, 0);
		// runAllPossible(board, 1);
		// if (true)
		// return;
		List<Move> availableMoves = board.availableMoves();

		Scanner scan = new Scanner(System.in);

		int currentplayer = 0;
		AI ai = new AI(board, 1);
		do {
			System.out.println(board);
			System.out.println("player: " + currentplayer);
			System.out.println("input:boxX boxY pos");

			String inp = currentplayer == 0 ? scan.nextLine() : ai.getNextMove().toString();
			System.out.println("inp: " + inp);
			String inps[] = inp.split(" ");

			if (board.put(Integer.parseInt(inps[0]), Integer.parseInt(inps[1]), Integer.parseInt(inps[2]),
					currentplayer)) {
				currentplayer = currentplayer ^ 1;
			} else {
				System.out.println("Already a line there, invalid move try again");
			}
		} while (!board.isGameOver());
		System.out.println(board);

		System.out.println("winner is: " + board.getWinner());
		scan.close();
	}

	static int it = 0;

	public static void runAllPossible(Board board, int currentplayer) {

		if (board.isGameOver()) {
			if (it++ % 1000000 == 0) {
				System.out.println(it);
			}
			return;
		}
		List<Move> moves = board.availableMoves();
		for (Move move : moves) {
			Board newBoard = new Board(board);
			newBoard.put(move.move[0], move.move[1], move.move[2], currentplayer);
			runAllPossible(newBoard, currentplayer ^ 1);
		}
	}

}
