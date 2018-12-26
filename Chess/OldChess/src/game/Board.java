package game;

import pieces.Bishop;
import pieces.ChessPiece;
import pieces.King;
import pieces.Knight;
import pieces.NoPiece;
import pieces.Pawn;
import pieces.Queen;
import pieces.Rook;
import util.Coordinate;

public class Board {
	private ChessPiece[][] board;

	public Board() {
		board = new ChessPiece[8][8];
		setupNewBoard();
	}

	public Board(ChessPiece[][] board) {
		this.board = new ChessPiece[8][8];
		setBoard(board);
	}

	public Board(Board another) {

		board = new ChessPiece[8][8];

		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				Coordinate coordinate = new Coordinate(x, y);

				board[x][y] = copy(another.getPiece(coordinate));

			}
		}

	}

	public ChessPiece copy(ChessPiece another) {

		if (another.getClass() == NoPiece.class)
			return new NoPiece((NoPiece) another);
		if (another.getClass() == Pawn.class)
			return new Pawn((Pawn) another);
		if (another.getClass() == Rook.class)
			return new Rook((Rook) another);
		if (another.getClass() == Knight.class)
			return new Knight((Knight) another);
		if (another.getClass() == Bishop.class)
			return new Bishop((Bishop) another);
		if (another.getClass() == Queen.class)
			return new Queen((Queen) another);
		if (another.getClass() == King.class)
			return new King((King) another);

		return null;
	}

	// isEqualTo
	public boolean equals(Board another) {

		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {

				Coordinate coordinate = new Coordinate(x, y);

				if (!(another.getPiece(coordinate).getClass() == getPiece(coordinate).getClass()))
					return false;
				else {
					if (!(getPiece(coordinate).equals((another.getPiece(coordinate)))))
						return false;
				}

			}
		}

		return true;
	}

	public void setupNewBoard() {
		for (int i = 0; i < 8; i++) {
			board[i][1] = new Pawn(new Coordinate(i, 1), true);
			board[i][6] = new Pawn(new Coordinate(i, 6), false);
		}
		for (int y = 2; y < 6; y++) {
			for (int x = 0; x < 8; x++) {
				board[x][y] = new NoPiece(new Coordinate(x, y));
			}
		}
		for (int i = 0; i < 8; i = i + 7) {
			board[0][i] = new Rook(new Coordinate(0, i), (i == 0));
			board[1][i] = new Knight(new Coordinate(1, i), (i == 0));
			board[2][i] = new Bishop(new Coordinate(2, i), (i == 0));
			board[3][i] = new Queen(new Coordinate(3, i), (i == 0));
			board[4][i] = new King(new Coordinate(4, i), (i == 0));
			board[5][i] = new Bishop(new Coordinate(5, i), (i == 0));
			board[6][i] = new Knight(new Coordinate(6, i), (i == 0));
			board[7][i] = new Rook(new Coordinate(7, i), (i == 0));
		}

	}

	public void wipeBoard() {
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				board[x][y] = new NoPiece(new Coordinate(x, y));
			}
		}
	}

	public void setBoard(ChessPiece[][] board) {
		this.board = board;
	}

	public void setPiece(ChessPiece piece) {
		board[piece.getCoordinate().getX()][piece.getCoordinate().getY()] = copy(piece);
	}

	public void setPiece(ChessPiece piece, Coordinate coordinate) {
		piece.setCoordinate(coordinate);
		piece.setHasMovedTrue();
		board[coordinate.getX()][coordinate.getY()] = copy(piece);
	}

	public void removeAt(Coordinate coordinate) {
		board[coordinate.getX()][coordinate.getY()] = new NoPiece(coordinate);
	}

	public void move(Coordinate coordinate1, Coordinate coordinate2) {
		setPiece(getPiece(coordinate1), coordinate2);
		removeAt(coordinate1);
	}

	public ChessPiece[][] getBoard() {
		return board;
	}

	public ChessPiece getPiece(Coordinate coordinate) {
		return board[coordinate.getX()][coordinate.getY()];
	}

	public int getNumberOfPieces() {
		int result = 0;

		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				if (!(board[x][y].getSimpleName().equals("NoPiece"))) {
					result++;
				}
			}
		}

		return result;
	}

	public int getNumberOfWhitePieces() {
		int result = 0;

		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				if (!(board[x][y].getSimpleName().equals("NoPiece")) && board[x][y].getPlayer()) {
					result++;
				}
			}
		}

		return result;
	}

	public int getNumberOfBlackPieces() {
		int result = 0;

		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				if (!(board[x][y].getSimpleName().equals("NoPiece")) && !(board[x][y].getPlayer())) {
					result++;
				}
			}
		}

		return result;
	}

	@Override
	public String toString() {
		String result = "";

		for (int y = 7; y >= 0; y--) {
			for (int x = 0; x < 8; x++) {
				result = result + board[x][y].toString() + " ";
			}
			result = result + "\n";
		}

		return result;

	}

}
