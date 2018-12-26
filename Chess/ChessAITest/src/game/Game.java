package game;



import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import pieces.*;
import util.*;
import graphics.*;


public class Game {
	
	//CONSTANTS
	//JFRAME IS SQUARE
	private final int NORMAL_JFRAME_SIZE =650;
	private final int MINIMUM_JFRAME_SIZE = 450;
	
	private final Color LIGHT_SQUARE_COLOR = Color.WHITE;
	private final Color DARK_SQUARE_COLOR = Color.GRAY;
	private final Color PIECE_CHOSEN_COLOR = Color.GREEN;
	private final Color ALL_AVAILABLE_MOVES_COLOR = Color.RED;
	private final Color CHECKER_COLOR = Color.ORANGE;
	
	
	private JLabel[] characters = new JLabel[8];
	private JLabel[] numbers = new JLabel[8];
	
	
	private Input input;
	private boolean firstPartOfInput;
	
	private Board currentBoard;
	private ArrayList<Board> history;
	private boolean playerTurn;
	private int winner;
	
	
	private JFrame frame;
	private MyButton [][] buttons;
	private JButton backButton;
	
	
	private ActionListener listener = new ActionListener(){
		
		@Override
		public void actionPerformed(ActionEvent event){
			if(playerTurn){
				Coordinate coordinatePicked = ((MyButton)(event.getSource())).getCoordinate();
				System.out.println("You pressed: " + coordinatePicked.getX() + ", " + coordinatePicked.getY());
				
				pickCoordinate(coordinatePicked);
			}
			else{ //AI
				
				pickCoordinate(new Coordinate(6,7));
				pickCoordinate(new Coordinate(7,5));
				
			
			}
			
			
			
		}
	};
	
	private ActionListener backButtonListener = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent event) {
			
			
			undoTurn();
		}
	};
	
	
	
	public Game(){
		firstPartOfInput = true;
		input = new Input();
		
		winner = -1;
		playerTurn= true;
		currentBoard = new Board();
		history = new ArrayList<Board>();
		frame = new JFrame("Chess by Akarsh Kumar");
		buttons = new MyButton[8][8];
		
		
		
		
		frame.setSize(NORMAL_JFRAME_SIZE,NORMAL_JFRAME_SIZE);
		frame.setMinimumSize(new Dimension(MINIMUM_JFRAME_SIZE,MINIMUM_JFRAME_SIZE));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLayout(new GridLayout(9,9));
		frame.setIconImage((new ImageIcon(getClass().getResource("/images/jframeimage.png"))).getImage());
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((int)(screenSize.getWidth() - frame.getWidth())/2,(int) (screenSize.getHeight() - frame.getHeight())/2);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		setupAllButtonsAndLabels();
		setupJFrame();
		
		frame.setVisible(false);
		
		
	}
	
	
	public void setupAllButtonsAndLabels(){
		
		ImageIcon icon = new ImageIcon("C:\\Users\\Akarsh\\Documents\\Eclipse\\Chess\\src\\graphics\\Chess Icons\\BackButton.png");
		Image img = icon.getImage();
		img = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		icon = new ImageIcon(img);
		backButton = new JButton(icon);
		backButton.setBackground(Color.WHITE);
		backButton.setToolTipText("Back");
		
		char ch = 'a';
		for(int i=0; i <8; i ++){
			characters[i] = new JLabel(ch+"");
			characters[i].setHorizontalAlignment(SwingConstants.CENTER);
			characters[i].setVerticalAlignment(SwingConstants.CENTER);
			characters[i].setToolTipText("Column " +ch);
			ch++;
			
			numbers[i] = new JLabel((i+1) +"");
			numbers[i].setHorizontalAlignment(SwingConstants.CENTER);
			numbers[i].setVerticalAlignment(SwingConstants.CENTER);
			numbers[i].setToolTipText("Row " + (i+1));
		}
		
		for(int y = 0 ; y<8;y++){
			for(int x = 0 ; x<8; x++){
				//buttons[x][y] = new MyButton(currentBoard.getPiece(new Coordinate(x,y)).toString(), new Coordinate(x,y));
				buttons[x][y] = new MyButton(currentBoard.getPiece(new Coordinate(x,y)).getImage() ,new Coordinate(x,y));
				buttons[x][y].setToolTipText(x+ "," + y);
				
				if((y%2==0 && x%2 ==1) || (y%2 ==1 &&x%2 ==0)) buttons[x][y].setBackground(DARK_SQUARE_COLOR);
				else buttons[x][y].setBackground(LIGHT_SQUARE_COLOR);
			}
		}
		
	}
	
	
	public void start(){
		
		
		backButton.addActionListener(backButtonListener);
		
		for(int y = 0 ; y<8;y++){
			for(int x = 0 ; x<8; x++){
				buttons[x][y].addActionListener(listener);
			}
		}
		
		
		frame.setVisible(true);
		
	}
	
	
	//UPDATES ENTIRE JFRAME AND BUTTONS TO CURRENTBOARD
	public void updateAll(){
		for(int y = 0 ; y<8;y++){
			for(int x = 0 ; x<8; x++){
				//buttons[x][y].setText(currentBoard.getPiece(new Coordinate(x,y)).toString());
				buttons[x][y].setIcon(currentBoard.getPiece(new Coordinate(x,y)).getImage());
			}
		}
		frame.repaint();
	}
	
	
	
	//SETS UP INITIAL BUTTONS INTO JFRAME IN CORRECT ORDER
	public void setupJFrame(){
		
		frame.add(backButton);
		
		//number labels
		
		setupJFrame(true);
		
		
	}
	public void setupJFrame(boolean player){
		if(player){
			for(int i=0; i <8; i++){
				frame.add(characters[i]);
			}
			
			for(int y = 0 ; y<8;y++){
				
				frame.add(numbers[7-y]);
				
				for(int x = 0 ; x<8; x++){
					frame.add(buttons[x][7-y]);
				}
				
			}
			
			
		}
		else{
			for(int i=0; i <8; i++){
				frame.add(characters[7 - i]);
			}
			
			for(int y = 0 ; y<8;y++){
				
				frame.add(numbers[y]);
				
				for(int x = 0 ; x<8; x++){
					frame.add(buttons[7-x][y]);
				}
				
			}
			
		}
		updateAll();
		
		
	}
	public void removeAllInJFrame(){
		for (int x=0; x<8; x++){
			frame.remove(characters[x]);
			frame.remove(numbers[x]);
			for (int y=0; y<8; y++){
				frame.remove(buttons[x][y]);
			}
		}
		
	}
	
	public void undoTurn(){
		
		if(!firstPartOfInput){
			unhighlightAll();
			firstPartOfInput = true;
		}
		
		else if(canUndo()){
			currentBoard = new Board(history.get(history.size()-1));
			history.remove(history.size()-1);
			System.out.println("You pressed: Back");
			switchTurns();
			
		}
		else{
			//FILE COMPLAINT- CANT  UNDO TURN (TOO EARLY IN THE GAME NO HISTORY)
			System.out.println("NO HISTORY");
		}
		firstPartOfInput = true;
		unhighlightAll();
		if(checkIfChecked(currentBoard,playerTurn)){
			highlight(findChecker(currentBoard, playerTurn), CHECKER_COLOR, true);
			frame.repaint();
		}
	}
	
	public boolean canUndo(){
		if(history.size()>0) return true;
		else return false;
		
	}
	
	public void move(Coordinate coordinate1, Coordinate coordinate2){
		
		
		Board temp = new Board(currentBoard);
		
		history.add(temp);
		
		currentBoard.move(coordinate1, coordinate2);

		updateAll();
		
	}
	
	public void switchTurns(){
		
		playerTurn = !playerTurn;
		removeAllInJFrame();
		setupJFrame(playerTurn);
		
	}
	
	
	public void highlight(Coordinate inp, Color col, boolean repaint){
		buttons[inp.getX()][inp.getY()].setBackground(col);
		if(repaint) frame.repaint();
	}

	public void unhighlightAll(){
		backButton.setBackground(Color.WHITE);
		for(int x = 0; x<8; x++){
			for(int y=0;y <8; y++){
				if(((y%2 ==1 && x%2==0) || (y%2 ==0 && x%2==1)) && buttons[x][y].getBackground() != DARK_SQUARE_COLOR){
					highlight(new Coordinate(x,y), DARK_SQUARE_COLOR ,false);
				}
				if(((y%2 ==0 && x%2==0) || (y%2 ==1 && x%2==1)) && buttons[x][y].getBackground() != LIGHT_SQUARE_COLOR){
					highlight(new Coordinate(x,y), LIGHT_SQUARE_COLOR , false);
				}
			}
		}
		frame.repaint();
	}
	
	public void highlightAllAvailableMoves(Coordinate coordinate){
		for(int y=0; y <8; y++){
			for(int x=0; x<8; x++){
				if(isActualValidMove(currentBoard, coordinate, new Coordinate(x,y))){
					highlight(new Coordinate(x,y), ALL_AVAILABLE_MOVES_COLOR, false);
				}
			}
		}
		frame.repaint();
	}
	
	@SuppressWarnings("deprecation")
	public void pickCoordinate(Coordinate coordinatePicked){
		
		if(firstPartOfInput){
			
			if(currentBoard.getPiece(coordinatePicked).getClass() != NoPiece.class && currentBoard.getPiece(coordinatePicked).getPlayer() ==playerTurn){
				input.firstCoordinate = new Coordinate(coordinatePicked);
				highlight(coordinatePicked, PIECE_CHOSEN_COLOR,true);
				highlightAllAvailableMoves(input.firstCoordinate);
				firstPartOfInput = false;
			}
			
		}
		else{
			
			input.secondCoordinate = new Coordinate(coordinatePicked);
			
			if(isActualValidMove(currentBoard, input.firstCoordinate, input.secondCoordinate)){
				
				move(input.firstCoordinate, input.secondCoordinate);
				
				if(history.get(history.size()-1).getPiece(input.firstCoordinate).getClass() == Pawn.class){
					checkAndUpdatePawnStatus();
				}
				
				System.out.println("W: " + checkIfChecked(currentBoard , true) + "\nB: " + checkIfChecked(currentBoard , false));
				System.out.println("WM: " + checkIfCheckMate(currentBoard , true) + "\nBM: " + checkIfCheckMate(currentBoard , false));
				System.out.println();
				
				
				
				if(checkIfCheckMate( currentBoard, true)|| checkIfCheckMate(currentBoard , false)){
					if(winner ==1 || winner==2){
						JLabel label = new JLabel();
						label.setText("0");
						label.setVerticalAlignment(SwingConstants.CENTER);
						label.setHorizontalAlignment(SwingConstants.LEFT);
						
						if(winner ==1){
							unhighlightAll();
							System.out.println("WHITE WINS");
							label.setText("White wins by checkmate!");
							JOptionPane.showMessageDialog(frame,label, "Winner!",  JOptionPane.INFORMATION_MESSAGE, ChessPiece.getImage(new King(new Coordinate(0,0), true)));
						}
						if(winner ==2){
							unhighlightAll();
							System.out.println("BLACK WINS");
							label.setText("Black wins by checkmate!");
							JOptionPane.showMessageDialog(frame,  label, "Winner!", JOptionPane.INFORMATION_MESSAGE , ChessPiece.getImage(new King(new Coordinate(0,0), false)));
						}
						frame.dispose();
						Starter.thread.resume();
					}
				}
				switchTurns();
				
			}
			
			unhighlightAll();
			firstPartOfInput = true;
			
			if(checkIfChecked(currentBoard , playerTurn)){
				highlight(findChecker(currentBoard, playerTurn) , CHECKER_COLOR , true);
			}
			
		}
		
	}
	
	private boolean isValidMove(Board board, Coordinate movingFrom, Coordinate movingTo){
		
		if(board.getPiece(movingFrom).getClass() == NoPiece.class)return false;
		
		if(board.getPiece(movingFrom).getPlayer() == board.getPiece(movingTo).getPlayer() && board.getPiece(movingTo).getClass() != NoPiece.class) return false;
		
		
		
		if(board.getPiece(movingFrom).getClass() == Pawn.class) return Pawn.isValidMove(board, movingFrom, movingTo);
		if(board.getPiece(movingFrom).getClass() == Rook.class) return Rook.isValidMove(board, movingFrom, movingTo);
		if(board.getPiece(movingFrom).getClass() == Bishop.class) return Bishop.isValidMove(board, movingFrom, movingTo);
		if(board.getPiece(movingFrom).getClass() == Knight.class) return Knight.isValidMove(board, movingFrom, movingTo);
		if(board.getPiece(movingFrom).getClass() == Queen.class) return Queen.isValidMove(board, movingFrom, movingTo);
		if(board.getPiece(movingFrom).getClass() == King.class) return King.isValidMove(board, movingFrom, movingTo);
		
		
		
		
		
		
		return true;
	}
	
	public boolean isActualValidMove(Board board, Coordinate movingFrom, Coordinate movingTo){
		if(isValidMove(board, movingFrom, movingTo)){
			Board tempBoard = new Board(board);
			tempBoard.move(movingFrom, movingTo);
			if(!checkIfChecked(tempBoard, board.getPiece(movingFrom).getPlayer()))return true;
		}
		return false;
	}
	
	public void checkAndUpdatePawnStatus(){
		for(int x=0; x <8 ;x++){
			if(currentBoard.getPiece(new Coordinate(x, 0)).getClass() == Pawn.class && !currentBoard.getPiece(new Coordinate(x, 0)).getPlayer()){
				currentBoard.setPiece(new Queen(new Coordinate(x,0), false));
			}
			if(currentBoard.getPiece(new Coordinate(x, 7)).getClass() == Pawn.class && currentBoard.getPiece(new Coordinate(x, 7)).getPlayer()){
				currentBoard.setPiece(new Queen(new Coordinate(x,7), true));
			}
		}
	}
	
	public boolean checkIfChecked(Board board, boolean player){
		
		Coordinate king = new Coordinate(0,0);
		for(int y = 0; y<8; y++){
			for(int x=0; x<8 ; x++){
				if(board.getPiece(new Coordinate(x,y)).getClass() == King.class && board.getPiece(new Coordinate(x,y)).getPlayer() == player) king.set(x, y);
			}
		}
		
		for(int y = 0; y<8; y++){
			for(int x=0; x<8 ; x++){
				if(isValidMove(board , new Coordinate(x,y), king)) return true;
			}
		}
		
		return false;
		
	}
	
	public Coordinate findChecker(Board board, boolean player){
		
		Coordinate king = new Coordinate(0,0);
		for(int y = 0; y<8; y++){
			for(int x=0; x<8 ; x++){
				if(board.getPiece(new Coordinate(x,y)).getClass() == King.class && board.getPiece(new Coordinate(x,y)).getPlayer() == player) king.set(x, y);
			}
		}
		
		for(int y = 0; y<8; y++){
			for(int x=0; x<8 ; x++){
				if(isValidMove(board , new Coordinate(x,y), king)){
					return new Coordinate(x,y);
				}
			}
		}
		return null;
	}
	
	public boolean checkIfCheckMate(Board board, boolean player){
		
		if(!checkIfChecked(board, player)) return false;
		
		
		for(int y1 = 0; y1 <8; y1++){
			for(int x1 = 0; x1 <8; x1++){
				
				if(board.getPiece(new Coordinate(x1, y1)).getPlayer() == player && board.getPiece(new Coordinate(x1, y1)).getClass()!= NoPiece.class){
					
					for(int y2 = 0; y2 <8; y2++){
						for(int x2 = 0; x2 <8; x2++){
							
							if(isValidMove(board, new Coordinate(x1, y1), new Coordinate(x2, y2))){
								
								Board tempBoard = new Board(board);
								tempBoard.move(new Coordinate(x1, y1), new Coordinate(x2, y2));
								
								if(!checkIfChecked(tempBoard, player)){
									//System.out.println(x1 +" " + y1 + " " +x2 + " " +y2);
									return false;
								}
							}
							
						}
					}
					
				}
				
			}
		}
		
		
		winner = (player)? 2:1;
		
		return true;
	}
	
	
	
}
