package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/***
 * This is the KeyListener that controls the Bubble Struggle player, add to the gameLabel
 * Space - can be pressed whenever<br>
 * Left/Right - Only one can be pressed at a time (whichever is pressed first)
 * <br>
 * It is recommended you find which key is supposed to be pressed using
 * isActivated(int key)
 * 
 * @author akars<br>
 */
public class PlayerActionListener implements KeyListener {
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int SPACE = 3;

	private boolean leftPressed = false;
	private boolean rightPressed = false;
	private boolean spacePressed = false;

	public PlayerActionListener(){
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			spacePressed = true;
		} else if (!leftPressed && !rightPressed) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT)
				leftPressed = true;
			else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
				rightPressed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_SPACE:
			spacePressed = false;
			break;
		case KeyEvent.VK_LEFT:
			if (leftPressed)
				leftPressed = false;
			break;
		case KeyEvent.VK_RIGHT:
			if (rightPressed)
				rightPressed = false;
			break;
		}

	}

	public boolean isActivated(int key) {
		if (key == LEFT)
			return leftPressed;
		else if (key == RIGHT)
			return rightPressed;
		else if (key == SPACE)
			return spacePressed;
		else
			return false;
	}

}
