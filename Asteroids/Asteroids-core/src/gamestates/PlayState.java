package gamestates;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import entities.Asteroid;
import entities.Bullet;
import entities.Particle;
import entities.Player;
import managers.GameStateManager;

public class PlayState extends GameState {

	private ShapeRenderer sr;

	private Player player;

	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
	private ArrayList<Particle> particles = new ArrayList<Particle>();

	private int level = 0;

	public PlayState(GameStateManager gsm) {
		super(gsm);
		init();
	}

	@Override
	public void init() {
		sr = new ShapeRenderer();

		player = new Player();

	}

	public void generateAsteroids(int level) {
		for (int i = 0; i < (level * 2 + 2); i++) {
			asteroids.add(new Asteroid());
		}
	}

	@Override
	public void update(float dt) {
		if (dt > .05f) {
			dt = .05f;
		}
		handleInput();

		// update player
		player.update(dt);

		// update bullets
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).update(dt);
			if (bullets.get(i).shouldRemove()) {
				bullets.remove(i);
				i--;
			}
		}
		// update asteroids
		for (int i = 0; i < asteroids.size(); i++) {
			asteroids.get(i).update(dt);
		}
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).update(dt);
			if (particles.get(i).shouldRemove()) {
				particles.remove(i);
				i--;
			}
		}

		// check collisions
		for (int i = 0; i < bullets.size(); i++) {
			Bullet b = bullets.get(i);
			for (int j = 0; j < asteroids.size(); j++) {
				Asteroid a = asteroids.get(j);

				if (a.contains(b.getx(), b.gety())) {
					// BULLET ASTEROID COLLISION
					Asteroid[] spawn = a.destroy(b.getRadians());
					asteroids.remove(j);
					j--;
					bullets.remove(i);
					i--;

					generateParticles(a.getx(), a.gety(), 50);// 50

					if (spawn == null) {
						break;
					}
					for (Asteroid aster : spawn) {
						asteroids.add(aster);
					}
					break;

				}
			}
		}
		if (asteroids.size() == 0) {
			level++;
			generateAsteroids(level);
		}

	}

	public void generateParticles(float x, float y, int num) {
		for (int i = 0; i < num; i++) {
			particles.add(new Particle(x, y));
		}
	}

	@Override
	public void draw() {
		player.draw(sr);
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).draw(sr);
		}
		for (int i = 0; i < asteroids.size(); i++) {
			asteroids.get(i).draw(sr);
		}
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).draw(sr);
		}

	}

	@Override
	public void handleInput() {
		Input input = Gdx.input;
		player.setLeft(input.isKeyPressed(Keys.LEFT));
		player.setRight(input.isKeyPressed(Keys.RIGHT));
		player.setUp(input.isKeyPressed(Keys.UP));
		player.setDown(input.isKeyPressed(Keys.DOWN));
		if (input.isKeyJustPressed(Keys.SPACE)) {
			if (bullets.size() >= 4) {

			} else {
				bullets.add(new Bullet(player.getx(), player.gety(), player.getRadians()));

			}
		}

	}

	@Override
	public void dispose() {

	}

}
