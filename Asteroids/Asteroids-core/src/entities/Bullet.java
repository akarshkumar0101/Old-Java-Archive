package entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;

public class Bullet extends Entity {

	private float timer = 0;
	private float lifetime = 1;

	private boolean remove = false;

	public Bullet(float x, float y, float radians) {
		this.x = x;
		this.y = y;

		speed = 400;
		this.radians = radians;

		dx = MathUtils.cos(radians) * speed;
		dy = MathUtils.sin(radians) * speed;

	}

	public boolean shouldRemove() {
		return remove;
	}

	@Override
	public void update(float dt) {

		if (timer > lifetime) {
			remove = true;
		}

		x += dx * dt;
		y += dy * dt;

		setShape();

		wrap();

		timer += dt;

	}

	@Override
	public void draw(ShapeRenderer sr) {
		sr.setColor(1, 0, 1, 1);

		sr.begin(ShapeType.Line);

		// sr.circle(x, y, 3);
		float x2 = x - MathUtils.cos(radians) * 20;
		float y2 = y - MathUtils.sin(radians) * 20;
		sr.line(x, y, x2, y2);

		sr.end();
	}

	@Override
	void setShape() {

	}

}
