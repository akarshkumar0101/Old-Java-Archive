package main;

public class Test3 {

	public static void main(String[] args) {
		for (int a = 1; a < 1000; a++) {
			for (int b = 1; b < 1000; b++) {
				double ratio = (double) (a * a + b * b) / (double) (a * b + 1);

				if (ratio % 1 == 0) {
					System.out.println(a + ", " + b + ": " + ratio);
					System.out.println("int");
				}
			}
		}
	}

}
