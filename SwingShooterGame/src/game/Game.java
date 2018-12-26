package game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Game extends JPanel{

	private static final long serialVersionUID = 7059646278559620203L;
	
	public Game(){
		super();
		
		
	}
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		Image monkey = Toolkit.getDefaultToolkit().getImage("C:/Users/Akarsh/Documents/Programming/Workspace/Java/SwingShooterGame/pic/monkey.png");
		Image banana = Toolkit.getDefaultToolkit().getImage("C:/Users/Akarsh/Documents/Programming/Workspace/Java/SwingShooterGame/pic/banana.png");
		double rotationRequired = Math.toRadians (100);
		double locationX = banana.getWidth(null) / 2;
		double locationY = banana.getHeight(null) / 2;
		AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		double w = banana.getWidth(null);
		double h = banana.getHeight(null);
		System.out.println("w: " +w+"  h: "+h);
		BufferedImage bimage = new BufferedImage((int)w,(int)h, BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(banana, 0, 0, null);
	    bGr.dispose();
		
		banana = op.filter(bimage, null);
		
		
		g.drawImage(monkey, 20, 20, 200, 200, this);
		g.drawImage(banana, 220, 220, 30, 30, this);
	}
}
