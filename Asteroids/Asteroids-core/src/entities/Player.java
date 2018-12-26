package entities;

import main.Game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;

public class Player extends Entity{
	
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;
	
	private float maxSpeed;
	private float acceleration;
	/**
	 * if the player isn't accelerating friction comes into play
	 */
	private float friction;
	
	public Player(){
		x = Game.WIDTH / 2;
		y = Game.HEIGHT / 2;
		
		maxSpeed = 300;
		acceleration = 200;
		friction = 10;
		
		shapex = new float[4];
		shapey = new float[4];
		radians = MathUtils.PI/2;
		
		rotationSpeed=3;
		
	}
	@Override
	void setShape(){
		shapex[0] = x+ MathUtils.cos(radians)*8;
		shapey[0] = y+ MathUtils.sin(radians)*8;
		
		shapex[1] = x+ MathUtils.cos(radians- 4*MathUtils.PI/5)*8;
		shapey[1] = y+ MathUtils.sin(radians- 4*MathUtils.PI/5)*8;
		
		shapex[2] = x+ MathUtils.cos(radians+MathUtils.PI)*5;
		shapey[2] = y+ MathUtils.sin(radians+MathUtils.PI)*5;
		
		shapex[3] = x+ MathUtils.cos(radians+ 4*MathUtils.PI/5)*8;
		shapey[3] = y+ MathUtils.sin(radians+ 4*MathUtils.PI/5)*8;
		
	}
	
	public void setLeft(boolean b){
		left = b;
	}
	public void setRight(boolean b){
		right = b;
	}
	public void setUp(boolean b){
		up = b;
	}
	public void setDown(boolean b){
		down = b;
	}
	@Override
	public void update(float dt){
		//turning
		if(left){
			radians += rotationSpeed *dt;
		}
		else if(right){
			radians -= rotationSpeed *dt;
		}
		
		//acceleration
		if(up){
			//increment speed
			dx += MathUtils.cos(radians) * acceleration *dt;
			dy += MathUtils.sin(radians) * acceleration *dt;
		}
		if(down){
			//decrement speed
			dx -= MathUtils.cos(radians) * acceleration *dt;
			dy -= MathUtils.sin(radians) * acceleration *dt;
		}
		//friction
		float vec = (float) Math.sqrt(dx*dx + dy *dy);
		if(vec> 0){
			dx-= (dx/vec) + friction *dt;
			dy-= (dy/vec) + friction *dt;
		}
		
		if(vec> maxSpeed){
			dx = (dx/vec) * maxSpeed;
			dy = (dy/vec) * maxSpeed;
		}
//		if(vec< -maxSpeed){
//			dx = (dx/vec) * maxSpeed;
//			dy = (dy/vec) * maxSpeed;
//		}
		
		//increment distance from the speed
		x += dx*dt;
		y += dy *dt;
		
		setShape();
		
		wrap();
		
	}
	@Override
	public void draw(ShapeRenderer sr){
		sr.setColor(0,1,1,1);
		
		sr.begin(ShapeType.Line);
		
		sr.polygon(new float[]{
				shapex[0], shapey[0],
				shapex[1], shapey[1],
				shapex[2], shapey[2],
				shapex[3], shapey[3]
						});
		
		sr.end();
		
	}
	
	
}
