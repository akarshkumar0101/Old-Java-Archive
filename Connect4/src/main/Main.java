package main;

import game.Connect4;

public class Main {

	public static void main(String[] args) {
		Connect4 game = new Connect4(Connect4.PVC);
		game.start();
	}

}
