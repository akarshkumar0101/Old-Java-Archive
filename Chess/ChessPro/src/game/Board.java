package game;

import piece.*;
import util.*;

public class Board {
	
	private Piece[][] board;
	
	public Board() {
		board = new Piece[8][8];
		setupNewBoard();
	}
	
	// COPY CONST
	public Board(Board another) {
		
		board = new Piece[8][8];

		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				Piece select = another.getPiece(Coordinate.getCoordinate(x, y));
				if(select.getClass() == Space.class) board[x][y] = new Space(select);
				if(select.getClass() == Pawn.class) board[x][y] = new Pawn(select);
				if(select.getClass() == Rook.class) board[x][y] = new Rook(select);
				if(select.getClass() == Knight.class) board[x][y] = new Knight(select);
				if(select.getClass() == Bishop.class) board[x][y] = new Bishop(select);
				if(select.getClass() == Queen.class) board[x][y] = new Queen(select);
				if(select.getClass() == King.class) board[x][y] = new King(select);
			}
		}
	}
	
	public void setupNewBoard() {
		for (int i = 0; i < 8; i++) {
			board[i][1] = new Pawn(Coordinate.getCoordinate(i, 1), Side.WHITE);
			board[i][6] = new Pawn(Coordinate.getCoordinate(i, 6), Side.BLACK);
		}
		for (int y = 2; y < 6; y++) {
			for (int x = 0; x < 8; x++) {
				board[x][y] = new Space(Coordinate.getCoordinate(x, y));
			}
		}
		for (int i = 0; i < 8; i = i + 7) {
			board[0][i] = new Rook(Coordinate.getCoordinate(0, i), (i == 0)? Side.WHITE : Side.BLACK);
			board[1][i] = new Knight(Coordinate.getCoordinate(1, i), (i == 0)? Side.WHITE : Side.BLACK);
			board[2][i] = new Bishop(Coordinate.getCoordinate(2, i), (i == 0)? Side.WHITE : Side.BLACK);
			board[3][i] = new Queen(Coordinate.getCoordinate(3, i), (i == 0)? Side.WHITE : Side.BLACK);
			board[4][i] = new King(Coordinate.getCoordinate(4, i), (i == 0)? Side.WHITE : Side.BLACK);
			board[5][i] = new Bishop(Coordinate.getCoordinate(5, i), (i == 0)? Side.WHITE : Side.BLACK);
			board[6][i] = new Knight(Coordinate.getCoordinate(6, i), (i == 0)? Side.WHITE : Side.BLACK);
			board[7][i] = new Rook(Coordinate.getCoordinate(7, i), (i == 0)? Side.WHITE : Side.BLACK);
		}
		
	}
	public boolean equals(Board another) {

		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {

				Coordinate coordinate = Coordinate.getCoordinate(x, y);

				if (another.getPiece(coordinate).getClass() != getPiece(coordinate).getClass())return false;
				if (!(getPiece(coordinate).equals(another.getPiece(coordinate))))return false;

			}
		}

		return true;
	}
	
	public Piece getPiece(Coordinate c){
		if (c==null) return null;
		return board[c.getX()][c.getY()];
	}
	
	
	public void removeAt(Coordinate coordinate) {
		if(coordinate == null) return;
		board[coordinate.getX()][coordinate.getY()] = new Space(coordinate);
	}
	public void setPiece(Piece piece){
		if(piece.getCoordinate() == null) return;
		board[piece.getCoordinate().getX()][piece.getCoordinate().getY()] = piece;
	}

	public void move(Move move) {
		Piece mover = getPiece(move.getMovingFrom());
		mover.moveTo(move.getMovingTo());
		setPiece(mover);
		
		removeAt(move.getMovingFrom());
	}
	
	
	public boolean isValidMove(Move move){ //DOES DETAIL CHECK TO SEE IF IT WILL CAUSE CHECK
		Side side = getPiece(move.getMovingFrom()).getSide();
		
		if(Piece.isValidMove(this, move)){
			
			Board temp = new Board(this);
			temp.move(move);
			if(!temp.isChecked(side)) return true;
			
		}
		return false;
		
	}
	
	public boolean isChecked(){
		if(isChecked(Side.WHITE) || isChecked(Side.BLACK)) return true;
		return false;
	}
	public boolean isChecked(Side side){
		Piece king = null;
		for(int x = 0; x<8; x++){
			for(int y = 0 ; y<8; y++){
				Piece p = getPiece(Coordinate.getCoordinate(x, y));
				if(p.getClass() == King.class && p.getSide() == side){
					king = p;
					break;
				}
			}
		}
		for(int x = 0; x<8; x++){
			for(int y = 0 ; y<8; y++){
				Coordinate c = Coordinate.getCoordinate(x, y);
				if(Piece.isValidMove(this, new Move(c, king.getCoordinate()))){
					return true;
				}
			}
		}
		return false;
	}
	public boolean isCheckMated(){
		if(isCheckMated(Side.WHITE) || isCheckMated(Side.BLACK)) return true;
		return false;
	}
	public boolean isCheckMated(Side side){
		
		if(!isChecked(side)) return false;
		
		for(int x1 = 0 ; x1<8; x1++){
			for(int y1 = 0 ; y1<8; y1++){
				Coordinate a = Coordinate.getCoordinate(x1, y1);
				if(getPiece(a).getSide() != side) continue;
				for(int x2 = 0 ; x2<8; x2++){
					for(int y2 = 0 ; y2<8; y2++){
						Coordinate b = Coordinate.getCoordinate(x2, y2);
						if(isValidMove(new Move(a,b))){
							Board temp = new Board(this);
							temp.move(new Move(a,b));
							if(!temp.isChecked(side)) return false;
						}
					}
				}
			}
		}
		
		
		return true;
	}
	
	
	public int getWhiteValue(){
		int result = 0;
		for(int x = 0; x<8; x++){
			for(int y = 0; y <8; y++){
				Piece select = getPiece(Coordinate.getCoordinate(x, y));
				if(select.getSide() == Side.WHITE && select.getClass() != King.class){
					result += select.getValue();
				}
			}
		}
		
		return result ++;
	}
	public int getBlackValue(){
		int result = 0;
		for(int x = 0; x<8; x++){
			for(int y = 0; y <8; y++){
				Piece select = getPiece(Coordinate.getCoordinate(x, y));
				if(select.getSide() == Side.BLACK && select.getClass() != King.class){
					result += select.getValue();
				}
			}
		}
		
		return result ++;
	}
	@Override
	public String toString() {
		String result = "";

		for (int y = 7; y >= 0; y--) {
			for (int x = 0; x < 8; x++) {
				result = result + board[x][y] + ((x<7) ?"| ": "");
			}
			result = result +((y>0) ?"\n\n\n\n": "\n\n" );
		}

		return result;

	}
	
	

}
