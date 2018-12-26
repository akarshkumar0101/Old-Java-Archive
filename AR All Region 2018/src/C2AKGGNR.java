import java.util.Scanner;

public class C2AKGGNR {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		boolean run = true;
		do {
			try {
				System.out.println("Enter a natural number larger than 2 to find the factor sum of it: ");
				int inp = scan.nextInt();
				if (inp <= 2)
					throw new Exception();

				System.out.println(factorSum(inp));

				System.out.println("Would you like to compute another factor sum?");
				System.out.println("Type 0 to terminate this program or type 1 to run again.");
				int option = scan.nextInt();
				if (option == 0) {
					run = false;
				}

			} catch (Exception e) {
				System.out.println("Invalid input or number is not greater than 2. Try again.");
			}
		} while (run);

		scan.close();

	}

	public static int factorSum(int n) {
		int sum = 1;
		for (int i = 2; i <= Math.sqrt(n); i++) {
			if (n % i == 0) {
				sum += i;
				if (i != Math.sqrt(n)) {
					sum += n / i;
				}
			}
		}

		return sum;
	}

}
