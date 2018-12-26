package main;

import game.*;
import util.*;

import java.util.*;

import pieces.ChessPiece;
import pieces.Knight;

public class Starter {
	
	public static void start(){
		
		
		
		Scanner scan  = new Scanner (System.in);
		
		
		Board board = new Board();
		System.out.println(board);
		
		while(true){
			int a = scan.nextInt();
			int b = scan.nextInt();
			int c = scan.nextInt();
			int d = scan.nextInt();
			System.out.println(Board.isValidMoveForPiece(board,
				Coordinate.getCoordinate(a, b), 
				Coordinate.getCoordinate(c, d))
			);
			
		}
		
	}
	

}
