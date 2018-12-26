package main;

import java.util.ArrayList;
import java.util.Scanner;

public class MathCompetition {

	public static void main(String[] args) {
		ArrayList<String> inputStrings = new ArrayList<String>();
		Scanner scan = new Scanner(System.in);
		String inputString = "";

		while (true) {
			String inp = scan.nextLine();
			if (inp.equals(""))
				break;
			inputStrings.add(inp);
		}

		scan.close();
	}

	public String getMathString(String inp) {
		try {
			if(!onlyContainsMathChars(inp)) throw new Exception();
			if(getNumOfOccurances(inp, '(') != getNumOfOccurances(inp, ')')) throw new Exception();
			
			
			String result = "";

			return result;
		} catch (Exception e) {
			return "INVALID";
		}
	}
	
	public static boolean onlyContainsMathChars(String inp){
		for(char c:inp.toCharArray()){
			if(c>='0' &&c<='9'){}
			else if(c=='*'||c=='/'||c=='+'||c=='-'){}
			else if(c=='('||c==')'){}
			else return false;
			
		}
		return true;
	}
	public static int getNumOfOccurances(String s, char c){
		int result = 0;
		for(char cc: s.toCharArray()){
			if(cc==c) result ++;
		}
		return result;
	}

}
