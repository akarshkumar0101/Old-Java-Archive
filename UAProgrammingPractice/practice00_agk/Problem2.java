import java.util.Scanner;

public class Problem2 {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		while (scan.hasNextLine()) {
			String input = scan.nextLine();
			String[] arr = input.split(" ");
			String inp1 = arr[0], inp2 = arr[1];
			String[] inp1arr = inp1.split("/"), inp2arr = inp2.split("/");
			int a = Integer.parseInt(inp1arr[0]), b = Integer.parseInt(inp1arr[1]), c = Integer.parseInt(inp2arr[0]),
					d = Integer.parseInt(inp2arr[1]);
			int numerator = a * d + c * b;
			int denom = b * d;
			int whole = (int) Math.floor(numerator / denom);
			int remainder = numerator - whole * denom;

			int newd = simplify(remainder, denom);
			int ratio = denom / newd;
			int newr = remainder / ratio;
			String out = "";
			if (whole != 0 && newr != 0) {
				out += whole + " " + newr + "/" + newd;
			} else if (whole == 0 && newr != 0) {
				out += newr + "/" + newd;
			} else if (whole != 0 && newr == 0) {
				out += whole;
			} else {
				out += "0";
			}

			System.out.println(out);
		}
	}

	public static int simplify(int a, int b) {
		for (int i = 2; i <= a; i++) {
			if (a % i == 0 && b % i == 0)
				return simplify(a / i, b / i);
		}
		return b;
	}

}
