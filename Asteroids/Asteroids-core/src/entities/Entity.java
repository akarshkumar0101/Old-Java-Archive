package entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

import main.Game;

public abstract class Entity {
	//position
	protected float x;
	protected float y;
	
	//speed absolute
	protected float speed;
	//velocity(with direction
	protected float dx;
	protected float dy;
	
	//rotation
	protected float radians;
	protected float rotationSpeed;
	
	//size
	protected float width;
	protected float height;
	
	//shape
	protected float[] shapex;
	protected float[] shapey;
	
	public abstract void update(float dt);
	public abstract void draw(ShapeRenderer sr);
	
	abstract void setShape();
	
	public final float getx(){
		return x;
	}
	public final float gety(){
		return y;
	}
	public final float getRadians(){
		return radians;
	}
	public final float getSpeed(){
		return speed;
	}

	public boolean contains(float x, float y) {
		
		boolean alternate = false;
		
		for (int i = 0, j = shapex.length - 1; i < shapex.length; j = i++) {
			if ((shapey[i] > y) != (shapey[j] > y)
					&& (x < (shapex[j] - shapex[i]) * (y - shapey[i])
							/ (shapey[j] - shapey[i]) + shapex[i])) {
				alternate = !alternate;
			}
 
		}
		
		return alternate;
	}
	
	public void wrap(){
		while(radians<-2*MathUtils.PI){
			radians += 2*MathUtils.PI;
		}
		while(radians> 2*MathUtils.PI){
			radians -= 2*MathUtils.PI;
		}
		
		if(x<0) x = Game.WIDTH;
		if(x>Game.WIDTH) x = 0;
		if(y<0) y = Game.HEIGHT;
		if(y>Game.HEIGHT) y = 0;
	}
	
}
