import java.util.Scanner;

public class Problem0 {
	/*
	 * ___d__s___ ___d_|s___ s________d __________sd_____ __________s_d_____
	 */

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		while (scan.hasNextLine()) {
			String input = scan.nextLine();

			int doglocale = input.indexOf('d');
			int squlocale = input.indexOf('s');

			String middle = input.substring(doglocale + 1, squlocale);
			String after = input.substring(squlocale + 1, input.length());

			// input.substring(squlocale+)

		}
	}

}
