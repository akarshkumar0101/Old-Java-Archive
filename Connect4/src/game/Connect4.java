package game;

import player.AIPlayer;
import player.ConsolePlayer;
import player.Player;

/**
 * connect 4 game class
 * 
 * @author akars
 */
public class Connect4 {
	public static final int PVP = 0, PVC = 1, CVP = 2, CVC = 3;
	private final Board board;

	private Player redPlayer;
	private Player yellowPlayer;

	private Player currentPlayer;

	public Connect4(int gameType) {
		board = new Board();

		if (gameType == 0 || gameType == 1) {
			redPlayer = new ConsolePlayer(board, Board.RED);
		} else {
			redPlayer = new AIPlayer(board, Board.RED, AIPlayer.HARD);
		}
		if (gameType == 0 || gameType == 2) {
			yellowPlayer = new ConsolePlayer(board, Board.YELLOW);
		} else {
			yellowPlayer = new AIPlayer(board, Board.YELLOW, AIPlayer.HARD);
		}
	}

	public void start() {
		int runs = 0;
		int winner = 0;

		currentPlayer = redPlayer;
		while (true) {
			board.displayInConsole();

			board.doMove(currentPlayer.getNextMove());
			currentPlayer = (currentPlayer == redPlayer) ? yellowPlayer : redPlayer;
			runs++;
			if (runs >= 7) {
				winner = board.getWinner();
				if (winner != Board.NONE) {
					break;
				}
				if (runs == 42) {
					if (board.isFull()) {
						break;
					}
				}
			}
			System.out.println(((AIPlayer) yellowPlayer).evaluateBoard(board));
		}
		System.out.println(((AIPlayer) yellowPlayer).evaluateBoard(board));
		board.displayInConsole();
		char winnerchar = Board.getChar(winner);
		System.out.println((winnerchar == ' ') ? ("Ends with draw") : (winnerchar + " won with four in a row"));
	}

}
