package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class Game implements Runnable {

	private static Board board;
	private static int currentScore;

	private static JFrame frame;
	private static JLabel[][] labels;
	private static boolean won;
	private static boolean care;
	private static boolean pressed;

	private KeyListener key = new KeyListener() {

		@Override
		public void keyPressed(KeyEvent event) {
			if (!pressed) {
				care = false;

				if (event.getKeyCode() == KeyEvent.VK_UP) {
					if (board.canMoveUp()) {
						currentScore += board.moveUp();
						care = true;
					}
				}
				if (event.getKeyCode() == KeyEvent.VK_DOWN) {
					if (board.canMoveDown()) {
						currentScore += board.moveDown();
						care = true;
					}
				}
				if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
					if (board.canMoveRight()) {
						currentScore += board.moveRight();
						care = true;
					}
				}
				if (event.getKeyCode() == KeyEvent.VK_LEFT) {
					if (board.canMoveLeft()) {
						currentScore += board.moveLeft();
						care = true;
					}
				}

				frame.setTitle("2048! - Score: " + currentScore);

				update();
				pressed = true;
			}

		}

		@Override
		public void keyReleased(KeyEvent event) {

			checkIfWon();

			if (care) {
				gen();
			}

			checkIfLost();
			pressed = false;

		}

		@Override
		public void keyTyped(KeyEvent arg0) {

		}

	};

	private MouseListener mouse = new MouseListener() {
		private int x, y;

		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent e) {
			x = e.getX();
			y = e.getY();
		}

		@Override
		public void mouseReleased(MouseEvent e) {

			boolean care2 = false;

			if (Math.sqrt(Math.pow(e.getX() - x, 2) + Math.pow(e.getY() - y, 2)) < 50)
				return;

			if (Math.abs(x - e.getX()) > Math.abs(y - e.getY())) { // XXX
				if (e.getX() > x) { // RIGHT
					if (board.canMoveRight()) {
						currentScore += board.moveRight();
						care2 = true;
					}
				}
				if (e.getX() < x) { // LEFT
					if (board.canMoveLeft()) {
						currentScore += board.moveLeft();
						care2 = true;
					}
				}

			}
			if (Math.abs(x - e.getX()) < Math.abs(y - e.getY())) { // YYY

				if (e.getY() > y) { // DOWN
					if (board.canMoveDown()) {
						currentScore += board.moveDown();
						care2 = true;
					}
				}
				if (e.getY() < y) { // UP
					if (board.canMoveUp()) {
						currentScore += board.moveUp();
						care2 = true;
					}
				}

			}

			frame.setTitle("2048! - Score: " + currentScore);

			update();

			checkIfWon();

			if (care2) {
				// gen();
				Thread thread = new Thread(new Game(true));
				thread.start();
			}

			checkIfLost();

		}

	};

	public Game() {
		won = false;
		care = false;
		pressed = false;

		board = new Board();
		currentScore = 0;
		frame = new JFrame("2048!");
		frame.addKeyListener(key);
		frame.addMouseListener(mouse);
		frame.setSize(900, 900);
		frame.setLayout(new GridLayout(4, 4));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setBackground(Color.GRAY);
		// frame.setResizable(false);
		frame.setIconImage((new ImageIcon(getClass().getResource("/images/jframeimage.png"))).getImage());
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((int) (screenSize.getWidth() - frame.getWidth()) / 2,
				(int) (screenSize.getHeight() - frame.getHeight()) / 2);

		labels = new JLabel[4][4];

		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				labels[x][y] = new JLabel("");
				frame.add(labels[x][y]);
				Border border = BorderFactory.createLineBorder(Color.GRAY, 6, false);
				labels[x][y].setBorder(border);
				labels[x][y].setHorizontalAlignment(SwingConstants.CENTER);
				labels[x][y].setVerticalAlignment(SwingConstants.CENTER);
				labels[x][y].setFont(new Font("normal", Font.BOLD, 30));
				labels[x][y].setOpaque(true);
			}
		}

		frame.setVisible(true);

	}

	public Game(boolean b) {

	}

	public void start() {

		for (int i = 0; i < 2; i++) {
			board.generate();
		}
		update();

	}

	public void update() {
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				int num = board.get(x, y);
				if (num == 0) {
					labels[x][y].setText("");
					labels[x][y].setBackground(Color.LIGHT_GRAY);
				} else {
					labels[x][y].setText("" + board.get(x, y));
					if (num == Math.pow(2, 1)) {
						labels[x][y].setBackground(Color.WHITE);
					}
					if (num == Math.pow(2, 2)) {
						labels[x][y].setBackground(Color.PINK);
					}
					if (num == Math.pow(2, 3)) {
						labels[x][y].setBackground(Color.ORANGE);
					}
					if (num == Math.pow(2, 4)) {
						labels[x][y].setBackground(Color.MAGENTA);
					}
					if (num == Math.pow(2, 5)) {
						labels[x][y].setBackground(Color.CYAN);
					}
					if (num == Math.pow(2, 6)) {
						labels[x][y].setBackground(Color.RED);
					}
					if (num == Math.pow(2, 7)) {
						labels[x][y].setBackground(Color.YELLOW);
					}
					if (num == Math.pow(2, 8)) {
						labels[x][y].setBackground(Color.GREEN);
					}
					if (num == Math.pow(2, 9)) {
						labels[x][y].setBackground(Color.DARK_GRAY);
					}
					if (num == Math.pow(2, 10)) {
						labels[x][y].setBackground(Color.GRAY);
					}
					if (num == Math.pow(2, 11)) {
						labels[x][y].setBackground(Color.PINK);
					}
				}

			}
		}

		frame.repaint();

	}

	public void checkIfWon() {
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				if (!won && board.get(x, y) == 2048) {
					won = true;
					int i = JOptionPane.showConfirmDialog(frame, "\tYou've reached 2048!\nWould you like to continue?",
							"You Win!", JOptionPane.YES_NO_OPTION);
					if (i == JOptionPane.NO_OPTION) {
						System.exit(0);
					}
				}

			}
		}
	}

	public void checkIfLost() {
		if (!board.canMoveRight() && !board.canMoveLeft() && !board.canMoveUp() && !board.canMoveDown()) {
			JOptionPane.showMessageDialog(frame, "You lose!", "Game Over!", JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}
	}

	public void gen() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		board.generate();
		update();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		gen();
		Thread.currentThread().stop();
	}

}
