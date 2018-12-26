package main;

import java.io.IOException;
import java.util.Scanner;

import math.CalMath;

public class Main {

	public static void main(String[] args) throws InterruptedException, IOException {
		Scanner in = new Scanner(System.in);
		String sample = "1/10*(((32/3.3^2^(2+3)*((1/100*(4*(32-321^.11)))^2)/6)^1.23)+34)";
		System.out.println("type in an input for example: \n"+sample);
		
		boolean run=true;
		while(run){
			String s = in.nextLine();
			System.out.println("Answer: "+CalMath.getValue(s));
		}
		in.close();
	}
	
}
