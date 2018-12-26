package main;

public class ThisIsMine {

	public ThisIsMine() {

	}

	public ThisIsMine(String s) {

	}

	public ThisIsMine(int i) {

	}

	public static void main(String[] args) {
		ThisIsMine a = new ThisIsMine();
		ThisIsMine b = new ThisIsMine("whatup");
		ThisIsMine c = new ThisIsMine(4);
	}

}

interface hi {
	static int a = 3;
}
