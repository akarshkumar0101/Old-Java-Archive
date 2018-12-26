package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

import game.ClueAI;
import game.Knowledge;
import game.Player;

//have to be in room to guess, can accuse anywhere though
public class Main {

	private static final Scanner scan = new Scanner(System.in);

	public static void main(String[] args) throws FileNotFoundException {
		System.setErr(new PrintStream(new File("myboterror.txt")));

		ClueAI ai = handleBeginning();

		String input = "";
		do {
			input = scan.nextLine();
			ai.handle(input);
		} while (!input.startsWith("Win"));

		scan.close();
	}

	public static ClueAI handleBeginning() {
		String idstr = scan.nextLine();
		String playercountstr = scan.nextLine();
		String boardstr = scan.nextLine();
		int id = Integer.parseInt(idstr.split(" ")[1]);
		int playercount = Integer.parseInt(playercountstr.split(" ")[1]);
		int boardlen = Integer.parseInt(boardstr.split(" ")[1]);

		int[][] board = new int[boardlen][boardlen];

		for (int i = 0; i < boardlen; i++) {
			String line = scan.nextLine();
			String[] rooms = line.split(" ");
			for (int x = 0; x < rooms.length; x++) {
				board[x][i] = Integer.parseInt(rooms[x]) - 1;
			}
		}
		Knowledge.establishBoard(board);
		ClueAI ai = new ClueAI(id);

		for (int i = 0; i < playercount; i++) {
			if (i != id) {
				new Player(i);
			}
		}

		return ai;
	}
}
