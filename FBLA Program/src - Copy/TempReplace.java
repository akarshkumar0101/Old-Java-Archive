import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class TempReplace {

	public static void main(String[] args) {
		replaceAllDir("volunteer", "employee", new File(""));
	}

	public static void replaceAllDir(String a, String b, File dir) {
		for (File file : dir.listFiles()) {
			if (file.isFile()) {
				replaceAllFile(a, b, file);
			} else if (file.isDirectory()) {
				replaceAllDir(a, b, file);
			}
		}
	}

	public static void replaceAllFile(String a, String b, File file) {
		String s = getString(file);
		s = s.replaceAll(a, b);
		writeToFile(s, file);
	}

	public static String getString(File file) {
		String s = "";
		Scanner scan = null;
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (scan.hasNextLine()) {
			s += scan.nextLine();
		}

		return s;
	}

	public static void writeToFile(String s, File file) {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		pw.write(s);
		pw.close();
	}

}
