package ui;

import game.ChessGame;

/**
 * the superclass of all ui's made for this chess program
 * 
 * @author akars
 *
 */
public abstract class ChessGameUI {

	protected ChessGame game;

	protected ChessGameUI(ChessGame game) {
		this.game = game;
	}

	/**
	 * This method should inform(ex. highlight) the user that the pawn has
	 * reached the end of the board and prompt(ex. JOptionPane) the user for an
	 * upgrade option (queen/knight/bishop/rook) and return the new piece to be
	 * made
	 * 
	 * @param side
	 *            of pawn
	 * @param locale
	 *            locale of upgrading pawn
	 * @return
	 */
	protected abstract int promptPawnUpgrade(int side, int... locale);

}
