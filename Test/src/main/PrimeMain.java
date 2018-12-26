package main;

public class PrimeMain {

	public static void primeMain() {
		boolean[] lights = new boolean[50];
		for (int i = 0; i < 50; i++) {
			lights[i] = false;
		}

		for (int n = 1; n <= 50; n++) {

			for (int i = 1; i <= 50; i++) {
				if (isPrime(i)) {
					lights[i - 1] = !lights[i - 1];
					System.out.print("|");
				} else {
					System.out.print(" ");
				}
				System.out.print(" ");
			}
			System.out.println();

			for (int i = 0; i < 50; i++) {
				System.out.print((lights[i] ? "O" : "X") + " ");
			}
			System.out.println();

		}
		System.out.println(isPrime(1));

	}

	public static boolean isPrime(int n) {
		if (n < 2)
			return false;
		for (int i = 2; i <= Math.sqrt(n); i++) {
			if (n % i == 0)
				return false;
		}
		return true;
	}

}
