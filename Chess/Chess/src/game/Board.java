package game;

public class Board {
	// Note use getter or setter method for board within this class(getter and
	// setter used to track outside interaction)
	// ALWAYS x, y
	public static final int WIDTH = 8, HEIGHT = 8;
	public static final int[][] ALL_LOCALES;

	private final int[][] board;

	public Board() {
		board = new int[WIDTH][HEIGHT];
		arrangeDefault();
	}

	public Board(Board another) {
		board = new int[WIDTH][HEIGHT];
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				board[x][y] = another.board[x][y];
			}
		}
	}

	public void arrangeDefault() {
		for (int y = 2; y < HEIGHT - 2; y++) {
			for (int x = 0; x < WIDTH; x++) {
				board[x][y] = Piece.NONE;
			}
		}
		for (int x = 0; x < WIDTH; x++) {
			board[x][1] = Piece.WP;
			board[x][6] = Piece.BP;
		}
		board[0][0] = board[7][0] = Piece.WR;
		board[0][7] = board[7][7] = Piece.BR;

		board[1][0] = board[6][0] = Piece.WN;
		board[1][7] = board[6][7] = Piece.BN;

		board[2][0] = board[5][0] = Piece.WB;
		board[2][7] = board[5][7] = Piece.BB;

		board[3][0] = Piece.WQ;
		board[3][7] = Piece.BQ;

		board[4][0] = Piece.WK;
		board[4][7] = Piece.BK;

	}

	/**
	 * @param coor
	 *            should always be int[2], signifying x, y
	 * @return
	 */
	public int get(int... coor) {
		return board[coor[0]][coor[1]];
	}

	/**
	 * This method exists for the purpose of placing pieces in certain locations
	 * to imitate situations OUTSIDE of a real chess game
	 * 
	 * @param piece
	 *            to place
	 * @param coor
	 *            should always be int[2], signifying x, y
	 */
	public void set(int piece, int... coor) {
		board[coor[0]][coor[0]] = piece;
	}

	public void doMove(Move move) {
		board[move.tx()][move.ty()] = board[move.fx()][move.fy()];
		board[move.fx()][move.fy()] = Piece.NONE;
	}

	public boolean playerCanMakeMove(int... moveCoor) {
		return playerCanMakeMove(new Move(moveCoor));
	}

	/**
	 * @param move
	 *            can either be int[4] or fx,fy,tx,ty
	 * @return
	 */
	public boolean playerCanMakeMove(Move move) {
		if (Piece.pieceCanMove(this, move)) {
			Board board = new Board(this);
			int side = board.board[move.fx()][move.fy()];
			board.doMove(move);
			if (!board.isInCheck(side))
				return true;
		}
		return false;
	}

	/**
	 * @param piece
	 *            signifies which side, doesn't have to be a king piece
	 * @return
	 */
	public boolean isInCheck(int piece) {
		piece = Piece.simplePosNeg(piece) * Piece.WK;
		int[] kingLocale = null;
		for (int[] locale : ALL_LOCALES) {
			int currPiece = board[locale[0]][locale[1]];
			if (currPiece == piece) {
				kingLocale = locale;
				break;
			}
		}

		for (int[] locale : ALL_LOCALES) {
			if (Piece.pieceCanMove(this, new Move(locale, kingLocale)))
				return true;
		}
		return false;
	}

	public boolean isInCheckMate(int piece) {
		if (!isInCheck(piece))
			return false;
		for (int[] fromLocale : ALL_LOCALES) {
			for (int[] toLocale : ALL_LOCALES) {
				Move move = new Move(fromLocale, toLocale);
				if (!Piece.areSameSide(piece, board[fromLocale[0]][fromLocale[1]]) || !Piece.pieceCanMove(this, move)) {
					continue;
				}
				Board tempBoard = new Board(this);
				tempBoard.doMove(move);

				if (!tempBoard.isInCheck(piece))
					return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		String result = "";
		for (int y = HEIGHT - 1; y >= 0; y--) {
			for (int x = 0; x < WIDTH; x++) {
				result += Piece.toChar(board[x][y]) + " ";
			}
			result += '\n';
		}
		return result;
	}

	static {
		ALL_LOCALES = new int[64][2];
		int movenum = 0;
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				// ALL_LOCALES[movenum++] = new int[] { x, y };
				ALL_LOCALES[movenum][0] = x;
				ALL_LOCALES[movenum++][1] = y;
			}
		}
	}

	/**
	 * @param coor
	 *            should always be int[2], signifying x, y
	 * @return is a valid place on the board
	 */
	public static boolean isInBoard(int... coor) {
		if (coor[0] < 0 || coor[0] > WIDTH - 1)
			return false;
		else if (coor[1] < 0 || coor[1] > HEIGHT - 1)
			return false;
		else
			return true;
	}

}
