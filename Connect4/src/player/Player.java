package player;

import game.Board;
import game.Move;

public abstract class Player {
	protected final Board board;
	protected final int color;

	public Player(Board board, int color) {
		this.board = board;
		this.color = color;
	}

	public abstract Move getNextMove();

	public int getColor() {
		return color;
	}
}
