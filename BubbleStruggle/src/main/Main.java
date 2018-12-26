package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import input.PlayerActionListener;

//no need for private get set safety freak out
//this is not a public API, just a simple game

public class Main {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);

		PlayerActionListener board = new PlayerActionListener();

		frame.addKeyListener(board);
		
		frame.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == ' ')
					return;
				System.out.println(board.isActivated(PlayerActionListener.SPACE));
				System.out.println(board.isActivated(PlayerActionListener.LEFT));
				System.out.println(board.isActivated(PlayerActionListener.RIGHT));
				System.out.println();
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

		});
		frame.setVisible(true);
	}
}
