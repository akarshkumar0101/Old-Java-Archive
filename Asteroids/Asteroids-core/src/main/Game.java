package main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

import managers.GameStateManager;

public class Game implements ApplicationListener {

	public static int WIDTH;
	public static int HEIGHT;

	public static OrthographicCamera camera;

	private GameStateManager gsm;

	// initial init method
	@Override
	public void create() {
		// setup game dimensions
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();

		// setup camera
		camera = new OrthographicCamera(WIDTH, HEIGHT);
		camera.translate(WIDTH / 2, HEIGHT / 2);
		camera.update();

		// setup gsm
		gsm = new GameStateManager();

	}

	// constant update method
	@Override
	public void render() {
		// clear screen
		Gdx.gl30.glClearColor(0, 0, 0, 1);
		Gdx.gl30.glClear(GL20.GL_COLOR_BUFFER_BIT);

		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.draw();

	}

	@Override
	public void resize(int width, int height) {
		System.out.println("resize");
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}

}
