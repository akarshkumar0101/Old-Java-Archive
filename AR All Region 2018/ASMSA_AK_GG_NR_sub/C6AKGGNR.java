import java.util.HashMap;
import java.util.Scanner;

public class C6AKGGNR {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		HashMap<Integer, Integer> frequencyTable = new HashMap<>();

		while (scan.hasNextLine()) {
			String inp = scan.nextLine();
			try {
				int num = Integer.parseInt(inp);
				if (num < -1)
					throw new Exception();

				if (frequencyTable.containsKey(num)) {
					frequencyTable.put(num, frequencyTable.get(num) + 1);
				} else {
					frequencyTable.put(num, 1);
				}
				if (num == -1) {
					break;
				}
			} catch (Exception e) {
				System.out.println(inp
						+ " was an improper input that is not a number or is less than -1. Please try again with a whole number or enter -1 to terminate.");
			}

		}
		int maxValue = Integer.MIN_VALUE;
		for (int key : frequencyTable.keySet()) {
			if (key > maxValue) {
				maxValue = key;
			}
		}
		System.out.println("Frequency Table");
		for (int key = 0; key <= maxValue; key++) {
			if (frequencyTable.containsKey(key)) {
				System.out.print(key + "|");
				for (int i = 0; i < frequencyTable.get(key); i++) {
					System.out.print("X");
				}
				System.out.println();
			}
		}

		scan.close();

	}

}
