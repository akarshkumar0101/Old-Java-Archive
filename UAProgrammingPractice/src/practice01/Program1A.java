
import java.util.Scanner;

public class Program1A {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		while (scan.hasNextLine()) {
			String inp = scan.nextLine();
			String[] inps = inp.split(" ");
			int base = Integer.parseInt(inps[1]);
			System.out.println(getB10Double(inps[0], base));
		}

	}

	public static double getB10Double(String num, int base) {
		double ans = 0;
		int decindex = num.indexOf(".");
		for (int i = 0, place = decindex - 1; i < num.length(); i++) {
			if (i == decindex) {
				continue;
			}
			ans += getInt(num.charAt(i)) * Math.pow(base, place);

			place--;
		}
		ans *= 100000;
		ans += .5;
		ans = Math.floor(ans);
		ans /= 100000;

		return ans;
	}

	public static int getInt(char c) {
		try {
			return Integer.parseInt(c + "");
		} catch (Exception e) {
			c = Character.toLowerCase(c);
			int num = c - 'a';
			num += 10;
			return num;

		}
	}
}
