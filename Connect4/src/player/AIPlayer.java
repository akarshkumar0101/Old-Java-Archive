package player;

import java.util.ArrayList;

import game.Board;
import game.Move;

public class AIPlayer extends Player {

	public static int EASY = 3;
	public static int MEDIUM = 5;
	public static int HARD = 7;
	public static int EXPERT = 9;

	private int maxdepth;

	public AIPlayer(Board board, int color, int maxdepth) {
		super(board, color);
		this.maxdepth = maxdepth;
	}

	int boards = 0, mini = 0;

	@Override
	public Move getNextMove() {
		boards = 0;
		mini = 0;
		long start = System.currentTimeMillis();
		Move move = minimax(0, board, true, Integer.MAX_VALUE);
		System.out.println("boards: " + boards + "\nminimax: " + mini + "\nin "
				+ (double) (System.currentTimeMillis() - start) / 1000 + " seconds");
		return move;
	}

	private static int[][] evaluationTable = { { 3, 4, 5, 7, 5, 4, 3 }, { 4, 6, 8, 10, 8, 6, 4 },
			{ 5, 8, 11, 13, 11, 8, 5 }, { 5, 8, 11, 13, 11, 8, 5 }, { 4, 6, 8, 10, 8, 6, 4 }, { 3, 4, 5, 7, 5, 4, 3 } };

	// here is where the evaluation table is called
	public int evaluateBoard(Board board) {
		boards++;
		int utility = 0;
		int sum = 0;
		for (int y = 0; y < Board.HEIGHT; y++) {
			for (int x = 0; x < Board.WIDTH; x++) {
				if (board.get(x, y) == color) {
					sum += evaluationTable[y][x];
				} else if (board.get(x, y) != Board.NONE) {
					sum -= evaluationTable[y][x];
				}
			}
		}
		return utility + sum;
	}

	public int score(Board board) {
		int score = 0;
		for (int r = 0; r < Board.HEIGHT; r++) {
			if (r <= Board.HEIGHT - 4) {
				for (int c = 0; c < Board.WIDTH; c++) {
					score += score(board, r, c);
				}
			} else {
				for (int c = 0; c <= Board.WIDTH - 4; c++) {
					score += score(board, r, c);
				}
			}
		}

		return score;
	}

	public int getHeuristicScore(char player, int col, int depth, int maxDepth) {
		int score = 0, row = firstAvailableRow[col] + 1, redCount, blackCount;
		redWinFound = blackWinFound = false;

		///////////////////////////////////////////////////////////////////////
		// Check row
		///////////////////////////////////////////////////////////////////////
		redCount = blackCount = 0;
		char[] boardRow = board[row];
		int cStart = col - 3, colStart = cStart >= 0 ? cStart : 0, colEnd = Board.WIDTH - 3 - (colStart - cStart);
		for (int c = colStart; c < colEnd; c++) {
			redCount = blackCount = 0;
			for (int val = 0; val < 4; val++) {
				char mark = boardRow[c + val];
				if (mark == Board.RED) {
					redCount++;
				} else if (mark == Board.YELLOW) {
					blackCount++;
				}
			}
			if (redCount == 4) {
				redWinFound = true;
				if (depth <= 2)
					return Integer.MIN_VALUE + 1;
			} else if (blackCount == 4) {
				blackWinFound = true;
				if (depth <= 2)
					return Integer.MAX_VALUE - 1;
			}
			score += getScoreIncrement(redCount, blackCount, player);
		}

		///////////////////////////////////////////////////////////////////////
		// Check column
		///////////////////////////////////////////////////////////////////////
		redCount = blackCount = 0;
		int rowEnd = Math.min(Board.HEIGHT, row + 4);
		for (int r = row; r < rowEnd; r++) {
			char mark = board[r][col];
			if (mark == Board.RED) {
				redCount++;
			} else if (mark == Board.YELLOW) {
				blackCount++;
			}
		}
		if (redCount == 4) {
			redWinFound = true;
			if (depth <= 2)
				return Integer.MIN_VALUE + 1;
		} else if (blackCount == 4) {
			blackWinFound = true;
			if (depth <= 2)
				return Integer.MAX_VALUE - 1;
		}
		score += getScoreIncrement(redCount, blackCount, player);

		///////////////////////////////////////////////////////////////////////
		// Check major diagonal
		///////////////////////////////////////////////////////////////////////
		int minValue = Math.min(row, col), rowStart = row - minValue;
		colStart = col - minValue;
		for (int r = rowStart, c = colStart; r <= Board.HEIGHT - 4 && c <= Board.WIDTH - 4; r++, c++) {
			redCount = blackCount = 0;
			for (int val = 0; val < 4; val++) {
				char mark = board[r + val][c + val];
				if (mark == Board.RED) {
					redCount++;
				} else if (mark == Board.YELLOW) {
					blackCount++;
				}
			}
			if (redCount == 4) {
				redWinFound = true;
				if (depth <= 2)
					return Integer.MIN_VALUE + 1;
			} else if (blackCount == 4) {
				blackWinFound = true;
				if (depth <= 2)
					return Integer.MAX_VALUE - 1;
			}
			score += getScoreIncrement(redCount, blackCount, player);
		}

		///////////////////////////////////////////////////////////////////////
		// Check minor diagonal
		///////////////////////////////////////////////////////////////////////
		minValue = Math.min(Board.HEIGHT - 1 - row, col);
		rowStart = row + minValue;
		colStart = col - minValue;
		for (int r = rowStart, c = colStart; r >= 3 && c <= Board.WIDTH - 4; r--, c++) {
			redCount = blackCount = 0;
			for (int val = 0; val < 4; val++) {
				char mark = board[r - val][c + val];
				if (mark == Board.RED) {
					redCount++;
				} else if (mark == Board.YELLOW) {
					blackCount++;
				}
			}
			if (redCount == 4) {
				redWinFound = true;
				if (depth <= 2)
					return Integer.MIN_VALUE + 1;
			} else if (blackCount == 4) {
				blackWinFound = true;
				if (depth <= 2)
					return Integer.MAX_VALUE - 1;
			}
			score += getScoreIncrement(redCount, blackCount, player);
		}
		return score;
	}

	public int score(Board board, int row, int col) {
		int score = 0;
		boolean unblocked = true;
		int tally = 0;

		if (row < Board.HEIGHT - 3) {
			unblocked = true;
			tally = 0;
			for (int r = row; r < row + 4; r++) {

				if (board.get(col, r) == Board.RED) {
					unblocked = false;
				}
				if (board.get(col, r) == Board.YELLOW) {
					tally++;
				}
			}
			if (unblocked == true) {
				score = score + (tally * tally * tally * tally);
			}
			if (col < Board.WIDTH - 3) {
				unblocked = true;
				tally = 0;
				for (int r = row, c = col; r < row + 4; r++, c++) {
					if (board.get(c, r) == Board.RED) {
						unblocked = false;
					}
					if (board.get(c, r) == Board.YELLOW) {
						tally++;
					}
				}
				if (unblocked == true) {
					score = score + (tally * tally * tally * tally);
				}
			}
		}
		if (col < Board.WIDTH - 3) {
			unblocked = true;
			tally = 0;
			for (int c = col; c < col + 4; c++) {
				if (board.get(c, row) == Board.RED) {
					unblocked = false;
				}
				if (board.get(c, row) == Board.YELLOW) {
					tally++;
				}
			}
			if (unblocked == true) {
				score = score + (tally * tally * tally * tally);
			}

			if (row > 2) {
				unblocked = true;
				tally = 0;
				for (int r = row, c = col; c < col + 4; r--, c++) {
					if (board.get(c, r) == Board.RED) {
						unblocked = false;
					}
					if (board.get(c, r) == Board.YELLOW) {
						tally++;
					}
				}
				if (unblocked == true) {
					score = score + (tally * tally * tally * tally);
				}
			}
		}

		return score;

	}

	public static boolean gameEnded(Board board) {
		int winner = board.getWinner();
		if (winner == Board.RED || winner == Board.YELLOW)
			return true;
		// now winner is none
		else if (board.isFull())
			return true;
		else
			return false;
	}

	public static ArrayList<Move> getPossibleMoves(Board board) {
		ArrayList<Move> result = new ArrayList<Move>();
		for (int x = 0; x < Board.WIDTH; x++) {
			if (board.canPut(x)) {
				result.add(new Move(x));
			}
		}
		return result;
	}

	int recur = 0;

	public Move minimax(int currdepth, Board board, boolean maximize, int exalphabeta) {
		mini++;
		ArrayList<Move> possibleMoves = getPossibleMoves(board);

		boolean parentmaximizes = !maximize;

		// minimized or maximized move so far //
		int bestMoveSoFar = maximize ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		int a = 0;
		int thisrec = recur++;
		for (Move move : possibleMoves) {
			// System.out.println(thisrec + " " + a++);
			Board boardAfterMove = new Board(board);
			boardAfterMove.doMove(move);

			// if found a win/tie or reached end of the line, then evaluate
			if (gameEnded(boardAfterMove) || currdepth == maxdepth) {
				move.setValue(score(boardAfterMove));
			}
			// keep going deeper
			else {
				int childvalue = minimax(currdepth + 1, boardAfterMove, !maximize, bestMoveSoFar).getValue();
				move.setValue(childvalue);
			}
			int val = move.getValue();
			bestMoveSoFar = maximize ? Integer.max(val, bestMoveSoFar) : Integer.min(val, bestMoveSoFar);

			// alpha beta pruning right here

			// if found that children won't let this move be as good as good as
			// a brother move (depending on if parent maximizes) then return a
			// move with terrible score to make sure it doesn't get chosen
			if (parentmaximizes ? (bestMoveSoFar < exalphabeta) : (bestMoveSoFar > exalphabeta)) {
				// System.out.println("pruning");
				move.setValue(parentmaximizes ? Integer.MIN_VALUE : Integer.MAX_VALUE);
				return move;
			}
		}
		// choose random best move
		ArrayList<Move> bestMoves = new ArrayList<Move>();
		for (Move move : possibleMoves) {
			if (move.getValue() == bestMoveSoFar) {
				bestMoves.add(move);
			}
		}
		// System.out.println(bestMoves.size());
		return bestMoves.get((int) (Math.random() * bestMoves.size()));
	}
}