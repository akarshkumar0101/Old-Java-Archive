
import java.util.ArrayList;
import java.util.Scanner;

public class Program4B {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		ArrayList<String> strs = new ArrayList<>();
		while (scan.hasNextLine()) {
			String inp = scan.nextLine();
			int width = 0, height = 0;
			try {
				String[] inps = inp.split(" ");
				width = Integer.parseInt(inps[0]);
				height = Integer.parseInt(inps[1]);
			} catch (Exception e) {
				strs.add(inp);
				continue;
			}
			int numlines = strs.size();
			int whitelines = height - numlines;
			int secondhalfLines = whitelines / 2;
			int firstHalfLines = whitelines - secondhalfLines;

			for (int i = 0; i < width + 2; i++) {
				if (i == 0 || i == width + 1) {
					System.out.print("+");
				} else {
					System.out.print("-");
				}
			}
			System.out.println();

			for (int i = 0; i < firstHalfLines - 1; i++) {
				System.out.print("|");

				for (int x = 0; x < width; x++) {
					System.out.print(" ");
				}
				System.out.print("|");
				System.out.println();
			}

			for (String str : strs) {
				System.out.print("|");
				int whiteSpace = width - str.length();
				int half = whiteSpace / 2;
				int secondhalf = whiteSpace - half;
				for (int i = 0; i < half; i++) {
					System.out.print(" ");
				}
				System.out.print(str);
				for (int i = 0; i < secondhalf; i++) {
					System.out.print(" ");
				}
				System.out.print("|");
				System.out.println();
			}
			for (int i = 0; i < secondhalfLines; i++) {
				System.out.print("|");

				for (int x = 0; x < width; x++) {
					System.out.print(" ");
				}
				System.out.print("|");
				System.out.println();
			}

			for (int i = 0; i < width + 2; i++) {
				if (i == 0 || i == width + 1) {
					System.out.print("+");
				} else {
					System.out.print("-");
				}
			}
			System.out.println();

			strs.clear();
		}

	}
}
