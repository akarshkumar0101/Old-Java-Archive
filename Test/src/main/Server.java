package main;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

	public static void main(String[] args) throws IOException {
		System.out.println("Running Server");
		ServerSocket ss = new ServerSocket(105);
		try {
			while (true) {
				System.out.println("whatup");
				Socket sock = ss.accept();
				System.out.println("ok waiting for inp");

				Scanner clientinp = new Scanner(sock.getInputStream());
				System.out.println("got inp now");
				int inp = clientinp.nextInt();

				double ans = inp / 2;

				PrintStream ps = new PrintStream(sock.getOutputStream());
				ps.println(ans);

				clientinp.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			ss.close();
		}
		System.out.println("Closing Server");
	}

}
