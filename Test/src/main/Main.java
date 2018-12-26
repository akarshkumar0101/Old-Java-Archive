package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		Scanner scan = new Scanner(new File("C:\\Users\\akars\\Desktop\\zombiecafedishes.txt"));

		String best = "";
		double maxeff = 0;
		while (scan.hasNextLine()) {
			// System.out.println(scan.nextLine());
			String[] words = scan.nextLine().replaceAll("\\s+", " ").split(" ");

			int i = 0;
			if (words[0].equals("Level")) {
				i += 2;
			}
			String name = "";
			while (!isTime(words[i])) {
				if (!isInt(words[i])) {
					name += words[i] + " ";
				}
				i++;
			}
			System.out.println(name + " ");

			double hours = 0;
			while (isTime(words[i])) {
				hours += timeInHours(words[i]);
				i++;
			}
			// skip cost
			int cost = Integer.parseInt(words[i++].substring(1));
			// skip serving size
			i++;
			// skip earnings
			i++;

			int profit = Integer.parseInt(words[i].substring(1));

			double eff = profit / hours;
			if (maxeff < eff) {
				maxeff = eff;
				best = name;
			}
			System.out.println("\tHrs: " + hours);
			System.out.println("\tCost: " + cost);
			System.out.println("\tProfit: " + profit);
			System.out.println("\tEfficiency: " + profit / hours);
		}
		System.out.println("Best: " + best);
		System.out.println("\tEfficiency: " + maxeff);

		scan.close();
	}

	public static boolean isInt(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isTime(String s) {
		if (s.endsWith(",")) {
			s = s.substring(0, s.length() - 1);
		}
		return (s.endsWith("M") || s.endsWith("H") || s.endsWith("D")) && (isInt(s.substring(0, s.length() - 1)));
	}

	public static double timeInHours(String s) {
		if (s.endsWith(",")) {
			s = s.substring(0, s.length() - 1);
		}
		int num = Integer.parseInt(s.substring(0, s.length() - 1));
		if (s.endsWith("M"))
			return (double) num / 60;
		if (s.endsWith("H"))
			return num;
		else
			return (double) num * 24;
	}

	// this program is not the most efficient but it is the most code concise
	public static void watchThis() {
		int h = new Byte("10");
		int w = new Byte("10");
		for (int val = 0; val < h * w; val++) {
			int ih = val / w, iw = val - ih * w;
			System.out.print(((iw == 0 || iw == w - 1 || ih == 0 || ih == h - 1 || (iw - ih) % 3 == 0) ? "*" : " ")
					+ (iw == w - 1 ? "\n" : ""));
		}
	}
}
