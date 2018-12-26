package gui;

import java.awt.*;
import java.awt.event.*;

import game.*;
import util.*;

import javax.swing.*;

public class GameFrame extends JFrame{
	
	private static final long serialVersionUID = 8363016111837989129L;

	private static  GameFrame instance;
	
	private static final Button[][] buttons = new Button[8][8];
	
	private static final int NORMAL_JFRAME_SIZE =650;
	private static final int MINIMUM_JFRAME_SIZE = 450;
	
	private static Side pov; //POINT OF VIEW
	
	
	private final static ActionListener listener = new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			Button button = (Button)e.getSource();
			System.out.println("You clicked: " +button.getCoordinate());
			uploadBoard(new Board(), Side.getOpposite(pov));
		}
		
	};
	
	private GameFrame(String inp){
		super(inp);
	}
	public static void initialize(){
		
		instance = new GameFrame("ChessPro by Akarsh Kumar");
		instance.setSize(NORMAL_JFRAME_SIZE,NORMAL_JFRAME_SIZE);
		instance.setMinimumSize(new Dimension(MINIMUM_JFRAME_SIZE,MINIMUM_JFRAME_SIZE));
		instance.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		instance.setResizable(true);
		instance.setLayout(new GridLayout(8,8));
		instance.setIconImage((new ImageIcon(instance.getClass().getResource("/images/jframeimage.png"))).getImage());
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		instance.setLocation((int)(screenSize.getWidth() - instance.getWidth())/2,(int) (screenSize.getHeight() - instance.getHeight())/2);
		//instance.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		for(int y = 7 ; y>=0; y--){
			for(int x = 0; x<8; x++){
				buttons[x][y] = new Button(Coordinate.getCoordinate(x, y));
				buttons[x][y].addActionListener(listener);
				buttons[x][y].setToolTipText(Coordinate.getCoordinate(x, y).toString());
				instance.add(buttons[x][y]);
			}
		}
		pov = Side.WHITE;
		instance.setVisible(false);
		
	}
	public static void uploadBoard(Board board ,Side p){
		pov = p;
		if(p == Side.WHITE){
			for(int y =0; y <8; y++){
				for(int x = 0; x<8; x++){
					buttons[x][y].setIcon(board.getPiece(Coordinate.getCoordinate(x,y)).getImage());
					buttons[x][y].setToolTipText(getActualCoordinate(Coordinate.getCoordinate(x, y)).toString());
				}
			}
		}
		else{
			for(int y =0; y <8; y++){
				for(int x = 0; x<8; x++){
					buttons[x][y].setIcon(board.getPiece(Coordinate.getCoordinate(7-x,7-y)).getImage());
					buttons[x][y].setToolTipText(getActualCoordinate(Coordinate.getCoordinate(x, y)).toString());
				}
			}
		}
		
		if(!instance.isVisible()) instance.setVisible(true);
		instance.repaint();
		
	}
	private static Coordinate getActualCoordinate(Coordinate c){
		if(pov == Side.WHITE) return c;
		return Coordinate.getCoordinate(7 - c.getX(), 7 - c.getY());
		
	}
	
	
	
}
class Button extends JButton{
	

	private static final long serialVersionUID = 8815220957847118416L;
	
	private Coordinate coordinate;
	
	public Button(String name, Coordinate c){//REMOVE AT THE END OF PROJECT
		super(name);
		coordinate = c;
	} 
	public Button(ImageIcon icon, Coordinate c){
		super(icon);
		coordinate = c;
	}
	public Button(Coordinate c){
		super();
		coordinate =c;
	}
	
	
	public void setCoordiante(Coordinate c){
		coordinate = c;
	}
	public Coordinate getCoordinate(){
		return coordinate;
	}
}
