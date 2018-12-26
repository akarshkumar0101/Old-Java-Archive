import java.util.Scanner;

public class C3AKGGNR {

	public static final char[] vowels = new char[] { 'a', 'e', 'i', 'o', 'u' };

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		boolean run = true;
		do {
			System.out.println("Enter a word, phrase, or a sentence to take the vowels out of it: ");
			String inp = scan.nextLine();
			System.out.println(removeVowels(inp));

			System.out.println("Would you like to enter another word, phrase, or sentence?");
			System.out.println("Type 0 to terminate or type 1 to run again.");
			int option = scan.nextInt();
			if (option == 0) {
				run = false;
			}
		} while (run);

		scan.close();

	}

	public static String removeVowels(String inp) {
		String ans = "";
		for (char c : inp.toCharArray()) {
			if (!isVowel(c)) {
				ans += c;
			}
		}
		return ans;
	}

	public static boolean isVowel(char c) {
		for (char vowel : vowels) {
			if (vowel == c)
				return true;
		}
		return false;
	}

}
