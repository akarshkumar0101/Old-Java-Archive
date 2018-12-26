
import java.util.Scanner;

public class Program3A {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		while (scan.hasNextLine()) {
			String input = scan.nextLine();
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
			System.out.println(x + " " + y);
		}
	}
}
