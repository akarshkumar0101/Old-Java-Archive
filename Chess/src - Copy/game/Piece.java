package game;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.HashMap;

/**
 * This class is made abstract and Board does not use Piece[][] for decreased
 * memory consumption and decreased processing time. Pieces are saved using
 * integer values. This class contains static methods that can be useful for
 * piece calculations.
 * 
 * @author akars
 */
public abstract class Piece {

	// Values board uses to save pieces... NOT piece material values
	public static final int NONE = 0;
	public static final int WP = 1, WB = 2, WN = 3, WR = 4, WQ = 5, WK = 6;
	public static final int BP = -WP, BB = -WB, BN = -WN, BR = -WR, BQ = -WQ, BK = -WK;

	private Piece() {
	};

	public static boolean areSameSide(int piece1, int piece2) {
		if (piece1 != 0 && simplePosNeg(piece1) == simplePosNeg(piece2))
			return true;
		else
			return false;
	}

	public static boolean areOppSide(int piece1, int piece2) {
		if (piece1 != 0 && simplePosNeg(piece1) == -simplePosNeg(piece2))
			return true;
		else
			return false;
	}

	public static int simplePosNeg(int x) {
		return x < 0 ? -1 : (x > 0 ? 1 : 0);
	}

	public static char toChar(int piece) {
		char ch = 0;
		int pieceType = Math.abs(piece);
		if (pieceType == WP) {
			ch = 'p';
		} else if (pieceType == WB) {
			ch = 'b';
		} else if (pieceType == WN) {
			ch = 'n';
		} else if (pieceType == WR) {
			ch = 'r';
		} else if (pieceType == WQ) {
			ch = 'q';
		} else if (pieceType == WK) {
			ch = 'k';
		}

		if (piece > 0) {
			ch = Character.toUpperCase(ch);
		}

		return ch;
	}

	private static HashMap<Integer, Image> images = new HashMap<Integer, Image>();
	static {
		images.put(WP, Toolkit.getDefaultToolkit()
				.getImage("C:\\Users\\akars\\Documents\\Eclipse Projects\\OldChess\\resources\\images\\Pawntrue.png"));
		images.put(BP, Toolkit.getDefaultToolkit()
				.getImage("C:\\Users\\akars\\Documents\\Eclipse Projects\\OldChess\\resources\\images\\Pawnfalse.png"));

		images.put(WR, Toolkit.getDefaultToolkit()
				.getImage("C:\\Users\\akars\\Documents\\Eclipse Projects\\OldChess\\resources\\images\\Rooktrue.png"));
		images.put(BR, Toolkit.getDefaultToolkit()
				.getImage("C:\\Users\\akars\\Documents\\Eclipse Projects\\OldChess\\resources\\images\\Rookfalse.png"));

		images.put(WB, Toolkit.getDefaultToolkit().getImage(
				"C:\\Users\\akars\\Documents\\Eclipse Projects\\OldChess\\resources\\images\\Bishoptrue.png"));
		images.put(BB, Toolkit.getDefaultToolkit().getImage(
				"C:\\Users\\akars\\Documents\\Eclipse Projects\\OldChess\\resources\\images\\Bishopfalse.png"));

		images.put(WN, Toolkit.getDefaultToolkit().getImage(
				"C:\\Users\\akars\\Documents\\Eclipse Projects\\OldChess\\resources\\images\\Knighttrue.png"));
		images.put(BN, Toolkit.getDefaultToolkit().getImage(
				"C:\\Users\\akars\\Documents\\Eclipse Projects\\OldChess\\resources\\images\\Knightfalse.png"));

		images.put(WQ, Toolkit.getDefaultToolkit()
				.getImage("C:\\Users\\akars\\Documents\\Eclipse Projects\\OldChess\\resources\\images\\Queentrue.png"));
		images.put(BQ, Toolkit.getDefaultToolkit().getImage(
				"C:\\Users\\akars\\Documents\\Eclipse Projects\\OldChess\\resources\\images\\Queenfalse.png"));

		images.put(WK, Toolkit.getDefaultToolkit()
				.getImage("C:\\Users\\akars\\Documents\\Eclipse Projects\\OldChess\\resources\\images\\Kingtrue.png"));
		images.put(BK, Toolkit.getDefaultToolkit()
				.getImage("C:\\Users\\akars\\Documents\\Eclipse Projects\\OldChess\\resources\\images\\Kingfalse.png"));

	}

	public static Image getTempImage(int piece) {
		return images.get(piece);
	}

	/**
	 * This method does not check for checks. It simply says whether the piece
	 * can move to a location based on the piece's nature and the board
	 * conditions
	 * 
	 * @param board
	 * @param move
	 * @return
	 */
	public static boolean pieceCanMove(Board board, Move move) {
		if (!Board.isInBoard(move.getTo()))
			return false;

		int piece = board.get(move.getFrom());

		if (piece == NONE)
			return false;

		else if (move.fx() == move.tx() && move.fy() == move.ty())
			return false;

		// if (Math.abs(board.get(move.getTo())) != WK) return true;

		else if (areSameSide(board.get(move.getFrom()), board.get(move.getTo())))
			return false;

		int pieceType = Math.abs(piece);

		if (pieceType == WK)
			return kCanMove(board, move);
		else if (pieceType == WQ)
			return qCanMove(board, move);
		else if (pieceType == WR)
			return rCanMove(board, move);
		else if (pieceType == WN)
			return nCanMove(board, move);
		else if (pieceType == WB)
			return bCanMove(board, move);
		else if (pieceType == WP)
			return pCanMove(board, move);
		else
			throw new RuntimeException("Invalid piece");
	}

	private static boolean kCanMove(Board board, Move move) {
		int dx = Math.abs(move.tx() - move.fx());
		int dy = Math.abs(move.ty() - move.fy());
		if (dx > 1 || dy > 1)
			return false;
		else
			return true;
	}

	private static boolean qCanMove(Board board, Move move) {
		return (rCanMove(board, move) || bCanMove(board, move));
	}

	private static boolean rCanMove(Board board, Move move) {
		int dx = move.tx() - move.fx();
		int dy = move.ty() - move.fy();

		if (!(dx == 0 || dy == 0))
			return false;

		int xch = simplePosNeg(dx);
		int ych = simplePosNeg(dy);
		for (int i = 1; i < Math.max(Math.abs(dx), Math.abs(dy)); i++) {
			if (board.get(xch * i + move.fx(), ych * i + move.fy()) != NONE)
				return false;
		}
		return true;
	}

	private static boolean nCanMove(Board board, Move move) {
		int dx = Math.abs(move.tx() - move.fx());
		int dy = Math.abs(move.ty() - move.fy());

		if (dx > 2 || dy > 2)
			return false;
		else if (dx == dy || dx == 0 || dy == 0)
			return false;
		else
			return true;
	}

	private static boolean bCanMove(Board board, Move move) {
		int dx = move.tx() - move.fx();
		int dy = move.ty() - move.fy();

		if (Math.abs(dx) != Math.abs(dy))
			return false;

		int xch = simplePosNeg(dx);
		int ych = simplePosNeg(dy);
		for (int i = 1; i < Math.abs(dx); i++) {
			if (board.get(xch * i + move.fx(), ych * i + move.fy()) != NONE)
				return false;
		}
		return true;
	}

	private static boolean pCanMove(Board board, Move move) {
		int piece = board.get(move.getFrom());

		int dx = move.tx() - move.fx();
		int dy = move.ty() - move.fy();

		int sim = simplePosNeg(piece);

		if (dx == 0 && dy * sim == 1 && board.get(move.getTo()) == NONE)
			return true;
		else if (dy * sim == 1 && Math.abs(dx) == 1 && areOppSide(piece, board.get(move.getTo())))
			return true;

		boolean moved = !(piece > 0 ? (move.fy() == 1) : (move.fy() == Board.WIDTH - 2));

		if (!moved && (dx == 0 && dy * sim == 2) && board.get(move.fx(), move.fy() + sim) == NONE
				&& board.get(move.fx(), move.fy() + 2 * sim) == NONE)
			return true;
		else
			return false;
	}

	public static boolean kCanCastle(Board board, Move move) {
		if (!move.isCastle())
			throw new RuntimeException("Can't send normal move to this function");
		return false;
	}

}