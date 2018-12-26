package entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;

import main.Game;

public class Asteroid extends Entity {

	public static final int SMALL = 0, MEDIUM = 1, LARGE = 2;

	public int type;

	public int numOfVertices;

	private float[] distanceFromCenter;

	public Asteroid(float x, float y, int type, float radians) {
		this.type = type;
		this.x = x;
		this.y = y;

		if (this.type == SMALL) {
			width = height = MathUtils.random(20, 30);
			numOfVertices = 6;
			speed = MathUtils.random(80, 90);
		}
		if (this.type == MEDIUM) {
			width = height = MathUtils.random(30, 40);
			numOfVertices = 8;
			speed = MathUtils.random(55, 75);
		}
		if (this.type == LARGE) {
			width = height = MathUtils.random(40, 50);
			numOfVertices = 10;
			speed = MathUtils.random(30, 50);
		}

		this.radians = radians;

		shapex = new float[numOfVertices];
		shapey = new float[numOfVertices];
		distanceFromCenter = new float[numOfVertices];
		for (int i = 0; i < numOfVertices; i++) {

			distanceFromCenter[i] = MathUtils.random(width / 2, width);

		}

		dx = MathUtils.cos(radians) * speed;
		dy = MathUtils.sin(radians) * speed;

	}

	public Asteroid() {
		this(MathUtils.random(5, Game.WIDTH - 5), MathUtils.random(5, Game.HEIGHT - 5), LARGE,
				MathUtils.random(MathUtils.PI));
	}

	public Asteroid[] destroy(float r) {
		if (type == SMALL)
			return null;

		if (type == MEDIUM) {
			Asteroid a = new Asteroid(x, y, SMALL, MathUtils.random(r + MathUtils.PI / 2, r - MathUtils.PI / 2));
			Asteroid b = new Asteroid(x, y, SMALL, MathUtils.random(r + MathUtils.PI / 2, r - MathUtils.PI / 2));
			return new Asteroid[] { a, b };
		}
		if (type == LARGE) {
			Asteroid a = new Asteroid(x, y, MEDIUM, MathUtils.random(r + MathUtils.PI / 2, r - MathUtils.PI / 2));
			Asteroid b = new Asteroid(x, y, MEDIUM, MathUtils.random(r + MathUtils.PI / 2, r - MathUtils.PI / 2));
			return new Asteroid[] { a, b };
		}

		return null;
	}

	@Override
	public void update(float dt) {
		x += dx * dt;
		y += dy * dt;

		setShape();

		wrap();

	}

	@Override
	public void draw(ShapeRenderer sr) {
		float[] points = new float[2 * numOfVertices];
		for (int i = 0; i < numOfVertices; i++) {
			points[2 * i] = shapex[i];
			points[2 * i + 1] = shapey[i];
		}

		sr.setColor(1, 1, 0, 1);

		sr.begin(ShapeType.Line);

		sr.polygon(points);
		// sr.line(0, 0, x, y);

		// sr.circle(x, y, width);
		// for (int i = 0; i < numOfVertices; i++) {
		// float rad = 2 * MathUtils.PI / numOfVertices * i;
		// sr.line(x, y, x + width * MathUtils.cos(radians + rad), y + width *
		// MathUtils.sin(radians + rad));
		// }

		sr.end();

	}

	@Override
	void setShape() {
		for (int i = 0; i < numOfVertices; i++) {
			float rad = 2 * MathUtils.PI / numOfVertices * i;
			shapex[i] = x + distanceFromCenter[i] * MathUtils.cos(radians + rad);
			shapey[i] = y + distanceFromCenter[i] * MathUtils.sin(radians + rad);
		}
	}

}
