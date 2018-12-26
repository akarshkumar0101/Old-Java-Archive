package util;

import java.util.ArrayList;

import game.Snake;

public interface UserInterface {

	public abstract void update(Snake snake, Coordinate usedToBeEnd);

	public abstract void endGameAndHighlightCrash(Coordinate crash);

	public abstract void printCurrentDisplay();

	public abstract ArrayList<Coordinate> getSnakeLocation();

	public abstract Snake getSnake();

}
