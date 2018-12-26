package game;

import java.awt.Graphics;

public abstract class Updatable {
	protected final GameLevel gl;
	protected final long birthTime;
	
	public Updatable(GameLevel gl){
		this.gl = gl;
		birthTime = System.nanoTime();
	}
	
	public abstract void update(long prevTime);
	
	public abstract void paint(Graphics g, GameLevel game);
	
	public void destroy(){
		
	}
	
}
