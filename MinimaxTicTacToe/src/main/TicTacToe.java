package main;

import java.util.Scanner;

import computerai.ComputerAI;
import ui.TFrame;

public class TicTacToe {
	public static final int NONE = 0, PLAYX = 1, PLAYO = -1;
	public static final int PVP = 0, PVC = 1, CVP = 2, CVC = 3;

	public final Board board;
	private int playtype;

	private ComputerAI playerx, playero;
	private ComputerAI current;

	private TFrame frame;

	private final Scanner consoleInp = new Scanner(System.in);

	public TicTacToe() {
		board = new Board();
	}

	public void start() {
		frame = new TFrame(this);

		board.reset();
		playerx = null;
		playero = null;
		// take inp from user on what kind of play they want pvp, pvc,etc
		playtype = PVC;
		if (playtype == PVC || playtype == CVC) {
			playero = new ComputerAI(board, PLAYO);
		}
		if (playtype == CVP || playtype == CVC) {
			playerx = new ComputerAI(board, PLAYX);
		}

		current = playerx;
		while (!board.hasWinner()) {
			board.displayInConsole();
			frame.updateUI();
			int[] move = getNextMove(current);
			board.put(move[0], move[1]);
			if (current == playerx) {
				current = playero;
			} else {
				current = playerx;
			}
		}
		board.displayInConsole();
		frame.updateUI();
		int winner = board.getWinner();
		System.out.println("Winner is: " + (winner == PLAYX ? 'X' : (winner == PLAYO ? 'O' : 'D')));
	}

	/**
	 * @param x
	 * @param y
	 * @return true if successfully took inp
	 */
	public void takeInp(int x, int y) {
		if (board.hasWinner()) {
			// System.exit(0);
		}
		if (current == null) {
			board.put(x, y);
			frame.updateUI();
		}
		if (board.hasWinner()) {
			// System.exit(0);
		}
		int[] move = getNextMove(playero);
		board.put(move[0], move[1]);
		frame.updateUI();
		if (board.hasWinner()) {
			// System.exit(0);
		}
	}

	public int[] getNextMove(ComputerAI ai) {
		if (ai == null) {
			System.out.println("Where would you like to move? ex. \"x y\"");
			String inp = consoleInp.nextLine();
			try {
				String[] moves = inp.split(" ");
				int[] move = new int[] { Integer.parseInt(moves[0]), Integer.parseInt(moves[1]) };
				if (!board.canPlace(move[0], move[1]))
					throw new Exception();
				return move;
			} catch (Exception e) {
				System.out.println("Invalid input - try again");
				return getNextMove(ai);
			}
		} else {
			System.out.println("Computer AI is moving...");
			return ai.getAIMove();
		}
	}

}
