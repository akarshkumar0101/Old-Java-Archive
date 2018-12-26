package main;

import java.util.Scanner;

import game.Board;
import game.Piece;
import ui.TestingFrame;

public class Main {

	public static void main(String[] args) {
		Board board = new Board();

		Scanner scan = new Scanner(System.in);

		TestingFrame frame = new TestingFrame(null, board);

		while (!(board.isInCheckMate(Piece.WK) || board.isInCheckMate(Piece.BK))) {
			System.out.println(board);
			System.out.println("W Check: " + board.isInCheck(Piece.WP));
			System.out.println("W CheckMate: " + board.isInCheckMate(Piece.WP));
			System.out.println("B Check: " + board.isInCheck(Piece.BP));
			System.out.println("B CheckMate: " + board.isInCheckMate(Piece.BP));
			String inpString = scan.nextLine().replace(" ", "");
			int[] ints = new int[] { Integer.parseInt(inpString.charAt(0) + ""),
					Integer.parseInt(inpString.charAt(1) + ""), Integer.parseInt(inpString.charAt(2) + ""),
					Integer.parseInt(inpString.charAt(3) + "") };
			// board.doMove(new Move(ints));
			frame.revalidateFrame();
			frame.repaintFrame();
		}
		scan.close();
	}
}
