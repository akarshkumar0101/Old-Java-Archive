import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class C4AKGGNR {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		boolean run = true;
		do {
			try {
				System.out.println(
						"Enter an amount of change less than $1.00 in decimal format (ex. 0.94) to be converted into coins: ");
				double inp = scan.nextDouble();

				System.out.println(convertCoins(inp));

				System.out.println("Would you like to convert another value into coins?");
				System.out.println("Type 0 to terminate or type 1 to run again.");
				int option = scan.nextInt();
				if (option == 0) {
					run = false;
				}
			} catch (Exception e) {
				System.out.println("Invalid input. Try again.");
			}
		} while (run);

		scan.close();

	}

	public static String convertCoins(double amountCents) {
		int amount = (int) (amountCents * 100);

		int numQ = amount / 25;
		amount = amount % 25;
		int numD = amount / 10;
		amount = amount % 10;
		int numN = amount / 5;
		amount = amount % 5;
		int numP = amount / 1;

		List<String> coins = new ArrayList<String>();
		if (numQ != 0) {
			coins.add(numQ + " Quarter" + (numQ > 1 ? "s" : ""));
		}
		if (numD != 0) {
			coins.add(numD + " Dime" + (numD > 1 ? "s" : ""));
		}
		if (numN != 0) {
			coins.add(numN + " Nickel" + (numN > 1 ? "s" : ""));
		}
		if (numP != 0) {
			coins.add(numP + " " + (numP > 1 ? "Pennies" : "Penny"));
		}
		boolean plural = coins.size() > 1;
		boolean commas = coins.size() > 2;
		String answer = "";
		for (int i = 0; i < coins.size(); i++) {
			if (plural && i == coins.size() - 1) {
				if (!commas) {
					answer += " ";
				}
				answer += "and ";
			}
			answer += coins.get(i);
			if (commas && i != coins.size() - 1) {
				answer += ", ";
			}
		}

		return answer;
	}

}
