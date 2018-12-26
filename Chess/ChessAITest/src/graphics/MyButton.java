package graphics;

import javax.swing.*;

import util.Coordinate;

public class MyButton extends JButton {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7930054133442488503L;
	
	private Coordinate coordinate;
	
	public MyButton(String name, Coordinate coordinate){
		super(name);
		this.coordinate = coordinate;
	}
	public MyButton(ImageIcon icon, Coordinate coordinate){
		super();
		super.setIcon(icon);
		this.coordinate = coordinate;
	}
	
	
	public void setCoordiante(Coordinate coordinate){
		this.coordinate = coordinate;
	}
	public Coordinate getCoordinate(){
		return coordinate;
	}
	
	@Override
	public void setIcon(Icon icon){
		super.setIcon(icon);
	}
	
}
