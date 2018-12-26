package game;

import javax.swing.JFrame;


public class Main {
	
	public static void main(String[] args) {
		Game game = new Game();
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		frame.add(game);
		frame.setVisible(true);
		
		
	}

}
