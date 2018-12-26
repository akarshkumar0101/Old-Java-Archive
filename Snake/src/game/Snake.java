package game;

import java.util.ArrayList;

import util.Coordinate;
import util.Direction;
import util.UserInterface;

public class Snake implements Runnable {

	private int DELAY = 70; // 70 delay of the game to slow down the snake to
							// manageable speeds

	private final ArrayList<Coordinate> body = new ArrayList<Coordinate>();
	private Direction realDirection = Direction.RIGHT;
	private Direction virtualDirection = Direction.RIGHT;

	// variable for growing snake
	private int generateGrowth = 1;

	final Thread threadToMove = new Thread(this);

	private final UserInterface updateUI;

	private int lifetime = 0;

	final Thread timer = new Thread(new Runnable() {

		@Override
		public void run() {
			while (true) {
				lifetime++;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(lifetime);
			}
		}

	});

	public Snake(UserInterface updateUI) {
		body.add(Coordinate.getCoordinate(3, 36));
		this.updateUI = updateUI;

	}

	public void attemptTurn(Direction dir) {
		if (dir == null)
			return;

		if (realDirection == dir)
			return;

		if (body.size() == 1) {
			virtualDirection = dir;
			return;
		}

		else if (dir != realDirection.getOppositeDirection()) {
			virtualDirection = dir;
		}

	}

	public ArrayList<Coordinate> getLocation() {
		return body;
	}

	@Override
	public void run() {
		timer.start();
		Coordinate crash = null;

		while (crash == null) {

			crash = move();

			try {
				Thread.sleep(DELAY);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		// highlight coordinate of crash with variable crash

		updateUI.endGameAndHighlightCrash(crash);

	}

	// returns coordinates of crash, null if no crash
	public Coordinate move() {

		realDirection = virtualDirection;
		Coordinate last = getTail();

		Coordinate future = null;
		if (realDirection == Direction.RIGHT || realDirection == Direction.LEFT) {
			future = Coordinate.getCoordinate(getHead().getX() + realDirection.toInt(), getHead().getY());
		} else if (realDirection == Direction.UP || realDirection == Direction.DOWN) {
			future = Coordinate.getCoordinate(getHead().getX(), getHead().getY() + realDirection.toInt());
		}

		// move snake
		for (int i = body.size() - 1; i > 0; i--) {
			body.set(i, body.get(i - 1));
		}
		body.set(0, future);

		// generate body if needed
		if (generateGrowth != 0) {
			generateGrowth--;
			body.add(last);
		}

		for (int i = 0; i < getLength(); i++) {
			body.set(i, body.get(i).getRealCoordinate());
		}

		updateUI.update(this, last);

		if (getHead() == ((Game) updateUI).getApple()) {
			((Game) updateUI).removeAppleAndGenerateNew();
			generateGrowth += 5;
		}

		// scan for hit
		ArrayList<Coordinate> temp = new ArrayList<Coordinate>(body);
		temp.remove(0);

		System.out.println("head: " + getHead());

		if (temp.contains(future))
			return future;

		return null;
	}

	public int getLength() {
		return body.size();
	}

	public Coordinate getHead() {
		return body.get(0);
	}

	public Coordinate getTail() {
		return body.get(body.size() - 1);
	}

}
