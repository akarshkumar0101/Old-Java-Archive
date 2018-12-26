package main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopMain {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Asteroids";

		cfg.width = 500;
		cfg.height = 400;

		// cfg.width = 1000;
		// cfg.height = 800;

		cfg.useGL30 = true;
		cfg.resizable = true;

		// cfg.fullscreen = true;
		Game game = new Game();
		new LwjglApplication(game, cfg);

	}
}
