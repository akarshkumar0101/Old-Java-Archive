package main;

import java.util.Scanner;

import math.Node;

public class Main {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		String inp = scan.nextLine();
		Node expression = Node.parseNode(inp);
		System.out.println(expression);
		System.out.println(expression.evaluate());
	}

}
