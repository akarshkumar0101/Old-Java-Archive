package main;

import java.util.ArrayList;
import java.util.Scanner;

public class EnglishToNumber {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		while (true) {
			System.out.println(Input.toLongValue(scan.nextLine()));
		}

	}

}

class Input {

	private long a;
	private long b;

	private static final String[] basicNumString = { "one", "two", "three", "four", "five", "six", "seven", "eight",
			"nine", "ten" };
	private static final String[] teensString = { "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen",
			"seventeen", "eighteen", "nineteen" };
	private static final String[] tensString = { "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty",
			"ninety" };

	private static final String hundredString = "hundred";

	private static final String thousandString = "thousand";
	private static final String millionString = "million";
	private static final String billionString = "billion";
	private static final String trillionString = "trillion";
	private static final String quadrillionString = "quadrillion";

	public Input(String a, String b) {
		this.a = Long.parseLong(a);

		try {
			this.b = Long.parseLong(b);
		} catch (Exception e) {
			this.b = toLongValue(b);
		}

	}

	public static long toLongValue(String englishValue) {
		long result = 0;
		String[] wordsArray = englishValue.split(" ");
		ArrayList<String> words = new ArrayList<String>();
		for (String s : wordsArray) {
			words.add(s);
		}

		ArrayList<ArrayList<String>> everytval = new ArrayList<ArrayList<String>>();

		ArrayList<String> temp = new ArrayList<String>();

		for (int i = 0; i < words.size(); i++) {
			String current = words.get(i);
			temp.add(current);
			if (isAddOn(current)) {
				everytval.add(temp);
				temp = new ArrayList<String>();
			}
		}
		if (!temp.isEmpty()) {
			everytval.add(temp);
		}

		for (ArrayList<String> val : everytval) {
			result += getValue(val);
		}
		return result;
	}

	/**
	 * four hundred ninety two thousand six hundred forty five seven thousand
	 * one hundred eighty nine billion
	 */
	public static long getValue(ArrayList<String> num) {
		String last = num.get(num.size() - 1);
		ArrayList<String> noaddon = new ArrayList<String>(num);
		noaddon.remove(noaddon.size() - 1);
		if (last.equals(thousandString))
			return toNum(noaddon) * 1000L;
		if (last.equals(millionString))
			return toNum(noaddon) * 1000000L;
		if (last.equals(billionString))
			return toNum(noaddon) * 1000000000L;
		if (last.equals(trillionString))
			return toNum(noaddon) * 1000000000000L;
		if (last.equals(quadrillionString))
			return toNum(noaddon) * 1000000000000000L;
		return toNum(num);
	}

	/**
	 * simple numbers 1-999
	 * 
	 * nine hundred || ninety nine five hundred || forty five
	 * 
	 * forty nine
	 * 
	 */
	public static long toNum(ArrayList<String> words) {
		long result = 0;

		String first = words.get(0);
		String last = words.get(words.size() - 1);

		if (words.size() == 1) { // one word number
			int temp = arrayIndexOf(first, basicNumString);
			if (temp > -1)
				return temp + 1;
			temp = arrayIndexOf(first, teensString);
			if (temp > -1)
				return 11 + temp;
			temp = arrayIndexOf(first, tensString);
			if (temp > -1)
				return (temp + 2) * 10;
			throw new RuntimeException("error");
		}
		if (words.size() == 2) { // two word number
			// forty five
			// two hundred
			if (last.equals(hundredString)) {
				long hundreds = arrayIndexOf(first, basicNumString);
				result = hundreds + 1;
				if (!(hundreds > -1))
					throw new RuntimeException("error");
				return result * 100;
			} else {
				long tens = arrayIndexOf(first, tensString);
				result += (tens + 2) * 10;
				if (!(tens > -1))
					throw new RuntimeException("no tens in two word num");
				long ones = arrayIndexOf(last, basicNumString);
				result += ones + 1;
				if (!(ones > -1))
					throw new RuntimeException("no tens in two word num");
				return result;
			}
		}
		if (words.size() >= 3) { // more than three word number
			long hundreds = arrayIndexOf(first, basicNumString);
			if (!(hundreds > -1))
				throw new RuntimeException("error");
			result = hundreds + 1;
			result *= 100;

			String third = words.get(2);
			try {
				String fourth = words.get(3);
				result = result + arrayIndexOf(fourth, basicNumString) + 1;
				result = result + (arrayIndexOf(third, tensString) + 2) * 10;
				return result;
			} catch (Exception e) {
				int temp = arrayIndexOf(third, basicNumString);
				if (temp > -1) {
					result += temp + 1;
				} else {
					temp = arrayIndexOf(third, teensString);
					if (temp <= -1) {
						temp = arrayIndexOf(third, tensString);
						if (temp <= -1)
							throw new RuntimeException("error");
						result += (temp + 2) * 10;
						return result;
					}
					result += temp + 11;
					return result;
				}
				return result;
			}

		}
		throw new RuntimeException("error");
	}

	private static boolean isAddOn(String inp) {
		if (inp.equals(thousandString))
			return true;
		if (inp.equals(millionString))
			return true;
		if (inp.equals(billionString))
			return true;
		if (inp.equals(trillionString))
			return true;
		if (inp.equals(quadrillionString))
			return true;
		return false;
	}

	private static int arrayIndexOf(String inp, String[] array) {
		for (int i = 0; i < array.length; i++) {
			if (inp.equals(array[i]))
				return i;
		}
		return -1;
	}

	public String equalityString() {
		if (a > b)
			return ">";
		if (a < b)
			return "<";
		return "=";
	}

	@Override
	public String toString() {
		return a + " " + b;
	}

}
