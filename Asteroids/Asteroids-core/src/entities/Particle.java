package entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;

public class Particle extends Entity {
	private float radius;

	public Particle(float x, float y) {
		this.x = x;
		this.y = y;

		radians = MathUtils.random(2 * MathUtils.PI);

		speed = MathUtils.random(30, 150);
		dx = MathUtils.cos(radians) * speed;
		dy = MathUtils.sin(radians) * speed;
		radius = MathUtils.random(.8f, 1.5f);
	}

	public boolean shouldRemove() {
		return (radius < .1f);
	}

	@Override
	public void update(float dt) {
		x += dx * dt;
		y += dy * dt;

		setShape();

		wrap();
	}

	private final Color color = new Color((float) (Math.random()), (float) (Math.random()), (float) (Math.random()),
			(float) (Math.random()));

	@Override
	public void draw(ShapeRenderer sr) {
		sr.setColor(1, 1, 1, 1);
		// sr.setColor(color);

		sr.begin(ShapeType.Filled);
		radius -= .02f;
		// sr.line(0, 0, x, y);
		sr.circle(x, y, radius);

		sr.end();

	}

	@Override
	void setShape() {

	}

}
