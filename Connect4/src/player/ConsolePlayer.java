package player;

import java.util.Scanner;

import game.Board;
import game.Move;

public class ConsolePlayer extends Player {

	private final Scanner consoleinp;

	public ConsolePlayer(Board board, int color) {
		super(board, color);

		consoleinp = new Scanner(System.in);
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				consoleinp.close();
			}
		}));
	}

	@Override
	public Move getNextMove() {
		System.out.println("Where would you like to move? Type in the column...");
		try {
			int inp = Integer.parseInt(consoleinp.nextLine().replace(" ", ""));

			if (!board.canPut(inp) || inp < 0 || inp >= Board.WIDTH)
				throw new Exception();
			return new Move(inp);
		} catch (Exception e) {
			System.out.println("Invalid input - try again...");
			return getNextMove();
		}
	}

}
