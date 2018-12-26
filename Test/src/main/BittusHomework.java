package main;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class BittusHomework {
	private static JFrame frame = null;

	public static void main(String[] args) {
		frame = new JFrame("this is a frame");
		initFrame();
	}

	public static void initFrame() {

		frame.setSize(900, 900);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		frame.setVisible(true);
	}
}
