package main;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Main {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(700, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(null);
		
		int [][] puzzle = generateRandomPuzzle();
		
		Game game1 = new Game();
		game1.setBounds(20, 20, 200, 200);
		frame.add(game1);
		game1.setBorder(BorderFactory.createLineBorder(Color.red));
		
		Game game2 = new Game();
		game2.setBounds(180, 180, 200, 200);
		frame.add(game2);
		game2.setBorder(BorderFactory.createLineBorder(Color.red));
		
		frame.setVisible(true);
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame.remove(game1);
		game1.repaint();
		
	}
	private static int[][] generateRandomPuzzle(){
		int [][] pieces = new int[4][4];
		for(int x=0; x<4; x++){
			for(int y=0; y<4; y++){
				pieces[x][y] = 0;
			}
		}
		
		for(int i=1; i <16;i ++){
			int x =0,y=0;
			do{
				x= (int)(Math.random()*4);
				y= (int)(Math.random()*4);
			}while(pieces[x][y] !=0);
			pieces[x][y] = i;
			
		}
		
		return pieces;
	}
	public static void printPuzzle(int[][] a){
		for(int y=0; y <4; y ++){
			for(int x=0; x<4; x++){
				System.out.print(a[x][y]+" ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
}
@SuppressWarnings("serial")
class Game extends JPanel{
	
	MouseAdapter mouseAdapter = new MouseAdapter(){
		
	};
	
	public Game(){
		
		super();
		this.addMouseListener(mouseAdapter);
		this.addMouseMotionListener(mouseAdapter);
		this.addMouseWheelListener(mouseAdapter);
		
	}
	
}
















