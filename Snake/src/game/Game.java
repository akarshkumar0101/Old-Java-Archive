package game;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import util.Coordinate;
import util.Direction;
import util.UserInterface;

public class Game implements UserInterface {
	// it's ONE PLAYER ONLY
	private static final Color OUTOFBOUNDSCOLOR = Color.BLACK;
	private static final Color BACKGROUNDCOLOR = Color.LIGHT_GRAY;
	private static final Color APPLECOLOR = Color.RED;
	private static final Color SNAKECOLOR = Color.GREEN;
	private static final Color HIGHLIGHTCRASHCOLOR = Color.MAGENTA;

	private final JFrame gameFrame = new JFrame("Snake Game TEMP STRING");
	private final JPanel gamePanel = new JPanel();
	private final JLabel[][] gameElements = new JLabel[Coordinate.XLENGTH][Coordinate.YLENGTH];

	private boolean gameStarted = false;

	private final Snake snake;

	private Coordinate apple;

	private KeyListener moveListener = new KeyListener() {

		// USE THIS FOR MOVE
		@Override
		public void keyPressed(KeyEvent e) {
			// if(e.getKeyCode() ==KeyEvent.VK_ESCAPE){
			// snake.threadToMove.suspend();
			// return;
			// }

			Direction direction = getDirection(e);

			if (direction == null)
				return;

			snake.attemptTurn(direction);

			if (!gameStarted) {
				startGame();
				gameStarted = true;
			}

		}

		private Direction getDirection(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_UP)
				return Direction.UP;
			if (e.getKeyCode() == KeyEvent.VK_DOWN)
				return Direction.DOWN;
			if (e.getKeyCode() == KeyEvent.VK_RIGHT)
				return Direction.RIGHT;
			if (e.getKeyCode() == KeyEvent.VK_LEFT)
				return Direction.LEFT;
			return null;
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}

	};

	{
		gamePanel.setLayout(new GridLayout(Coordinate.YLENGTH, Coordinate.XLENGTH));

		for (int y = Coordinate.YLENGTH - 1; y >= 0; y--) {
			for (int x = 0; x < Coordinate.XLENGTH; x++) {

				gameElements[x][y] = new JLabel();

				if (Coordinate.getCoordinate(x, y).isOut()) {
					gameElements[x][y].setBackground(OUTOFBOUNDSCOLOR);
				} else {
					gameElements[x][y].setBackground(BACKGROUNDCOLOR);
				}
				// gameElements[x][y].setBorder(BorderFactory.createLineBorder(Color.BLUE));
				gameElements[x][y].setOpaque(true);

				gamePanel.add(gameElements[x][y]);
			}
		}

		gameFrame.add(gamePanel);
		gameFrame.setSize(700, 400);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public Game() {

		snake = new Snake(this);

		apple = Coordinate.getCoordinate(40, 10);

		gameFrame.addKeyListener(moveListener);

		// update();

		gameFrame.setVisible(true);
	}

	public void startGame() {
		snake.threadToMove.start();
	}

	public void removeAppleAndGenerateNew() {
		// generate new apple
		int x = 0;
		int y = 0;
		do {
			x = (int) (Math.random() * (Coordinate.XLENGTH));
			y = (int) (Math.random() * (Coordinate.YLENGTH));
		} while (x == 0 || x == 1 || x == Coordinate.XLENGTH - 1 || x == Coordinate.XLENGTH - 2 || y == 0 || y == 1
				|| y == Coordinate.YLENGTH - 1 || y == Coordinate.YLENGTH - 2
				|| snake.getLocation().contains(Coordinate.getCoordinate(x, y)));
		apple = Coordinate.getCoordinate(x, y);
	}

	public void colorSnakePixel(Coordinate c) {
		gameElements[c.getX()][c.getY()].setBackground(SNAKECOLOR);
		gameElements[c.getX()][c.getY()].setBorder(BorderFactory.createLineBorder(BACKGROUNDCOLOR, 1));
	}

	public void colorApplePixel(Coordinate c) {
		gameElements[c.getX()][c.getY()].setBackground(APPLECOLOR);
		gameElements[c.getX()][c.getY()].setBorder(BorderFactory.createLineBorder(BACKGROUNDCOLOR, 5));
	}

	public void unColor(Coordinate c) {
		gameElements[c.getX()][c.getY()].setBackground(BACKGROUNDCOLOR);
		gameElements[c.getX()][c.getY()].setBorder(null);
	}

	public Coordinate getApple() {
		return apple;
	}

	@Override
	public void update(Snake snake, Coordinate usedToBeEnd) {
		colorApplePixel(apple);

		updateSnake(snake, usedToBeEnd);

	}

	public void updateSnake(Snake snake, Coordinate usedToBeEnd) {

		if (snake.getLocation().contains(usedToBeEnd)) {

		} else {
			unColor(usedToBeEnd);
		}

		for (int i = 0; i < snake.getLength(); i++) {
			colorSnakePixel(snake.getLocation().get(i));
		}
	}

	@Override
	public void endGameAndHighlightCrash(Coordinate crash) {
		System.out.println("crash@" + crash);
		gameElements[crash.getX()][crash.getY()].setBackground(HIGHLIGHTCRASHCOLOR);
		printCurrentDisplay();

	}

	@Override
	public void printCurrentDisplay() {
		for (int x = 0; x < gameElements.length; x++) {
			for (int y = 0; y < gameElements[0].length; y++) {
				System.out.print(snake.getLocation().contains(Coordinate.getCoordinate(x, y)) ? "0" : "1");

			}
			System.out.println();
		}

	}

	@Override
	public ArrayList<Coordinate> getSnakeLocation() {
		return snake.getLocation();
	}

	@Override
	public Snake getSnake() {
		return snake;
	}

}
