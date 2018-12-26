
import java.util.Scanner;

public class Program3B {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		while (scan.hasNextLine()) {
			String in = scan.nextLine();
			String[] inps = in.split(" ");
			String input = inps[0];
			int x = 0;
			int y = 0;
			for (int i = 0; i < input.length(); i++) {
				switch (input.charAt(i)) {
				case ('N'):
					y++;
					break;
				case ('S'):
					y--;
					break;
				case ('E'):
					x++;
					break;
				case ('W'):
					x--;
					break;
				}
			}
			boolean XPos = true;
			if (x < 0) {
				x *= -1;
				XPos = false;
				for (int i = 0; i < x; i++) {
					System.out.print("E");
				}
			}

			if (y > 0) {
				for (int i = 0; i < y; i++) {
					System.out.print("S");
				}
			}

			if (y < 0) {
				y *= -1;
				for (int i = 0; i < y; i++) {
					System.out.print("N");
				}
			}

			if (x > 0 && XPos) {
				for (int i = 0; i < x; i++) {
					System.out.print("W");
				}
			}
			System.out.println();
		}
	}
}
