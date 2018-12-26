
import java.util.ArrayList;
import java.util.Scanner;

public class Program4A {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		ArrayList<String> strs = new ArrayList<>();
		while (scan.hasNextLine()) {
			String inp = scan.nextLine();
			int width = 0;
			try {
				width = Integer.parseInt(inp);
			} catch (Exception e) {
				strs.add(inp);
				continue;
			}
			for (String str : strs) {
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
				System.out.println();
			}
			strs.clear();
		}

	}
}
