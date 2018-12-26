package computerai;

import java.util.ArrayList;

import main.Board;

public class ComputerAI {
	private static final int NOTFINISHED = 3923;
	private final Board board;
	private final int player;
	private int boardsAnalyzed;

	public ComputerAI(Board board, int player) {
		this.board = board;
		this.player = player;
	}

	public int staticEvaluateBoard(Board board) {
		boardsAnalyzed++;
		if (!board.hasWinner())
			return NOTFINISHED;
		int winner = board.getWinner();
		if (winner == 0)
			return 0;
		else if (winner == player)
			return 1;
		else
			return -1;
	}

	/**
	 * @param board
	 * @return AraryList<int[2]{x,y}>
	 */
	public static ArrayList<Move> getPossibleMoves(Board board) {
		ArrayList<Move> moves = new ArrayList<Move>();
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				if (board.canPlace(x, y)) {
					moves.add(new Move(x, y));
				}
			}
		}
		return moves;
	}

	public Move minimax(int currdepth, Board board, ArrayList<Move> moves, boolean maximize, int exalphabeta) {
		for (int i = 0; i < moves.size(); i++) {
			Board b = new Board(board);
			b.put(moves.get(i).x, moves.get(i).y);
			int value = staticEvaluateBoard(b);
			if (value != NOTFINISHED) {
				moves.get(i).value = value;
				if (maximize ? (value > exalphabeta) : (value < exalphabeta)) {
					exalphabeta = value;
				}
			} else {
				moves.get(i).value = minimax(currdepth + 1, b, getPossibleMoves(b), !maximize, exalphabeta).value;
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
		ArrayList<Move> bestMoves = new ArrayList<Move>();
		for (Move move : moves) {
			if (extreme == move.value) {
				bestMoves.add(move);
			}
		}
		return bestMoves.get((int) (Math.random() * bestMoves.size()));
	}

	/**
	 * @return new int[]{ x, y, boardsEvaluated}
	 */
	public int[] getAIMove() {
		boardsAnalyzed = 0;
		Move move = minimax(0, board, getPossibleMoves(board), true, Integer.MIN_VALUE);
		System.out.println(boardsAnalyzed + " boards were analyzed");
		return new int[] { move.x, move.y };
	}
}

class Move {
	public final int x, y;
	public int value;

	public Move(int x, int y) {
		this.x = x;
		this.y = y;
	}
}