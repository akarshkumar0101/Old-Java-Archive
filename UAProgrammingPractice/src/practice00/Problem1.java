import java.util.Scanner;

public class Problem1 {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		while (scan.hasNextLine()) {
			int result = Integer.valueOf(scan.nextLine());
			while (result > 9) {
				result = checkSum(result);
			}
			System.out.print(result);
		}
	}

	public static int checkSum(int input_) {
		String input = input_ + "";
		int result = 0;
		for (int i = 0; i < input.length(); i++) {
			result += Integer.parseInt(input.charAt(i) + "");
		}
		return result;
	}

}
