import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class C5AKGGNR {
	public static final File inFile = new File("grades.txt");
	public static final File outFile = new File("calculated_grades.txt");

	public static void main(String[] args) {
		Scanner scan = null;
		try {
			scan = new Scanner(inFile);
		} catch (FileNotFoundException e) {
			System.out.println("Could not find the file grades.txt in the same directory as program. Terminating...");
			return;
		}

		PrintWriter printOut = null;
		try {
			printOut = new PrintWriter(outFile);
		} catch (FileNotFoundException e) {
			System.out.println("Unable to output to calculated_grades.txt. Terminating...");
			return;
		}
		while (scan.hasNextLine()) {
			String inp = scan.nextLine();
			String[] strs = inp.split(" ");

			List<Integer> grades = new ArrayList<>();

			for (int i = 2; i < strs.length; i++) {
				grades.add(Integer.parseInt(strs[i]));
			}

			int average = 0;
			for (int grade : grades) {
				average += grade;
			}
			double averagedouble = average / grades.size();
			averagedouble += 0.5;
			average = (int) averagedouble;

			String ans = strs[0] + " " + strs[1] + " " + average + " ";

			char letter = ' ';
			if (average > 0) {
				letter = 'F';
			}
			if (average > 59) {
				letter = 'D';
			}
			if (average > 69) {
				letter = 'C';
			}
			if (average > 79) {
				letter = 'B';
			}
			if (average > 89) {
				letter = 'A';
			}
			ans += letter;

			printOut.println(ans);

		}

		scan.close();
		printOut.close();

	}

}
