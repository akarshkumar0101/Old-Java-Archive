
import java.util.Scanner;

public class Test {

	static char[] digits = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
			'F' };

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		System.out.println(scan.nextLine());
		scan.close();
	}

	public static String reverseString(String str) {
		String ans = "";

		for (int i = str.length() - 1; i >= 0; i--) {
			ans += str.charAt(i);
		}
		return ans;
	}

	public static String convertBase(String numStr, int initBase, int finBase) {
		String ans = "";

		int num = 0;

		for (int i = 0; i < numStr.length(); i++) {
			int digit = findChar(numStr.charAt(numStr.length() - 1 - i));
			num += digit * Math.pow(initBase, i);
		}

		int firstExp = (int) (Math.log(num) / Math.log(finBase));
		for (int i = firstExp; i >= 0; i--) {

		}

		return ans;
	}

	public static int findChar(char c) {
		for (int i = 0; i < digits.length; i++) {
			if (digits[i] == c)
				return i;
		}
		return -1;
	}
}
// 1st: print info about team, school, initials
// 2nd: reverse string
// 3rd: convert temperatures
// 4th: calculator
// 5th: array of numbers statistics
// 6th: