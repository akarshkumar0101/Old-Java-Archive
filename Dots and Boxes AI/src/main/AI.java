package main;

import java.util.ArrayList;
import java.util.List;

public class AI {

	private final Board board;
	private final int player;

	public AI(Board board, int player) {
		this.board = board;
		this.player = player;
	}

	public String getNextMove() {
		String res = "";
		int[] bestMove = getAIMove();

		res = bestMove[0] + " " + bestMove[1] + " " + bestMove[2];
		return res;
	}

	int boardsAnalyzed = 0;

	/**
	 * @return new int[]{ x, y,pos}
	 */
	public int[] getAIMove() {
		System.out.println("ai getting move...");
		boardsAnalyzed = 0;
		Move move = minimax(0, board, player, Integer.MIN_VALUE);
		System.out.println(boardsAnalyzed + " boards were analyzed");
		return new int[] { move.move[0], move.move[1], move.move[2] };
	}

	public Move minimax(int currdepth, Board board, int currentPlayer, int exalphabeta) {
		boolean maximize = currentPlayer == player;
		List<Move> moves = board.availableMoves();
		for (int i = 0; i < moves.size(); i++) {
			Board b = new Board(board);
			b.put(moves.get(i).move[0], moves.get(i).move[1], moves.get(i).move[2], currentPlayer);
			int value = scoreBoard(b);
			if (value != NOT_FINISHED) {
				moves.get(i).value = value;
				if (maximize ? (value > exalphabeta) : (value < exalphabeta)) {
					exalphabeta = value;
				}
			} else {
				moves.get(i).value = minimax(currdepth + 1, b, currentPlayer ^ 1, exalphabeta).value;
				if (maximize ? (moves.get(i).value > exalphabeta) : (moves.get(i).value < exalphabeta)) {
					exalphabeta = moves.get(i).value;
				}
			}
			// System.out.println(exalphabeta + " " + moves.get(i).value + " " +
			// maximize);
			if (maximize ? (moves.get(i).value > exalphabeta) : (moves.get(i).value < exalphabeta)) {
				for (int j = i + 1; j < moves.size(); j++) {
					moves.get(j).value = maximize ? Integer.MIN_VALUE : Integer.MAX_VALUE;
				}
				break;
			}
		}

		int extreme = maximize ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		for (Move move : moves) {
			if (maximize ? (move.value > extreme) : (move.value < extreme)) {
				extreme = move.value;
			}
		}
		List<Move> bestMoves = new ArrayList<Move>();
		for (Move move : moves) {
			if (extreme == move.value) {
				bestMoves.add(move);
			}
		}
		return bestMoves.get((int) (Math.random() * bestMoves.size()));
	}

	private final int NOT_FINISHED = -2432942;

	private int scoreBoard(Board board) {
		if (board.isGameOver()) {
			boardsAnalyzed++;
			if (board.getWinner() == player)
				return Integer.MAX_VALUE;
			else
				return Integer.MIN_VALUE;
		} else {
			int res = board.player0numBoxes - board.player1numBoxes;
			// if (player == 1)return -res;
			return NOT_FINISHED;
		}
	}

}
