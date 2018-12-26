package phys;

import game.GameLevel;
import game.Updatable;

/**
 * @author akars
 */
public abstract class PhysObject extends Updatable{
	
	public static final double GRAVITY = 500;
	
	public Vector acc;
	public Vector vel;
	public Vector pos;

	public PhysObject(GameLevel gl) {
		super(gl);
		acc = new Vector(0,GRAVITY);
		vel = new Vector();
		pos = new Vector();
		"hello".contains("he");
	}
	
	public PhysObject(Vector acc, Vector vel, Vector pos) {
		this.acc = acc;
		this.vel = vel;
		this.pos = pos;
	}
	
}
