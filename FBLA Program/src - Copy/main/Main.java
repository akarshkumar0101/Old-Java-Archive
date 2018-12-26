package main;

public class Main {

	public static void main(String[] args) {
		// printMem();
		Application.start();
	}

	public static void printMem() {
		Thread printMemThread = new Thread(() -> {
			while (true) {
				System.out.println("KB: "
						+ (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		printMemThread.start();
	}
}
