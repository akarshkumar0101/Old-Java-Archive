package main;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) throws UnknownHostException, IOException {
		Scanner localinp = new Scanner(System.in);
		int inp = localinp.nextInt();

		System.out.println("about to start socket");
		Socket sock = new Socket("192.168.1.23", 105);
		System.out.println("got it");

		PrintStream ps = new PrintStream(sock.getOutputStream());

		Scanner servinp = new Scanner(sock.getInputStream());
		ps.println(inp);

		double ans = servinp.nextDouble();
		System.out.println("aight: " + ans);

	}

}
