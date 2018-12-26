package main;

import game.*;
import util.*;
import piece.*;
import gui.*;

import java.util.Scanner;

public class Starter {

	public static void start() {
		Coordinate.initialize();
		Board board = new Board();

		//System.out.println(board);
		Scanner scan = new Scanner(System.in);
		GameFrame.initialize();
		GameFrame.uploadBoard(board,Side.WHITE);
		
		/*while (true) {
			board.move(new Move(Coordinate.getCoordinate(scan.nextInt(),
					scan.nextInt()), Coordinate.getCoordinate(scan.nextInt(),
					scan.nextInt())));
			System.out.println(board);
			System.out.println(board.isChecked(Side.BLACK));
			System.out.println(board.isCheckMated(Side.BLACK));
			
		}
		 */
	}
}
