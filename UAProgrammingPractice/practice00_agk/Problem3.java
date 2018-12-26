import java.util.Scanner;

public class Problem3 {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		while (scan.hasNextLine()) {
			String input = scan.nextLine();
			int inp = Integer.parseInt(input);
			int maxnum = -1;
			int maxa = -1;
			for (int i = 1; i < inp; i++) {
				int a = inp - i;
				int b = i;

				int num = countOnes(a, b);
				if (num > maxnum) {
					maxnum = num;
					maxa = a;
				}
			}
			System.out.println(maxnum);
		}
	}

	public static int countOnes(int a, int b) {
		String astr = Integer.toBinaryString(a), bstr = Integer.toBinaryString(b);
		return numOnes(astr) + numOnes(bstr);
	}

	public static int numOnes(String a) {
		int result = 0;
		for (char c : a.toCharArray()) {
			if (c == '1') {
				result++;
			}
		}
		return result;
	}

}
