package main;

import java.util.ArrayList;
import java.util.Scanner;

public class PrimeFactorization {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int a = scan.nextInt();
		for (int r : getFactoriztionTree(a)) {
			System.out.print(r + " * ");
		}
	}

	public static ArrayList<Integer> getFactoriztionTree(int a) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(a);
		return split(list);
	}

	public static ArrayList<Integer> split(ArrayList<Integer> list) {

		for (int i = 0; i < list.size(); i++) {
			if (!isPrime(list.get(i))) {
				break;
			}
			if (i == list.size() - 1)
				return list;
		}

		for (int i = 0; i < list.size(); i++) {
			int num = list.get(i);
			if (isPrime(num)) {
				continue;
			}
			ArrayList<Integer> split = splitNum(list.get(i));

			list.remove(i);
			list.add(split.get(0));
			list.add(split.get(1));

		}

		return split(list);
	}

	private static ArrayList<Integer> splitNum(int num) {
		ArrayList<Integer> list = new ArrayList<Integer>();

		if (isPrime(num))
			throw new RuntimeException("is prime");

		for (int i = 2; i <= Math.sqrt(num); i++) {
			if (num % i == 0) {
				list.add(num / i);
				list.add(i);
				return list;
			}
		}
		throw new RuntimeException("couldn't find divisible num");
	}

	private static boolean isPrime(int value) {
		if (value < 2)
			return false;
		return isPrimeRecur(value, (int) Math.sqrt(value));
	}

	private static boolean isPrimeRecur(int value, int check) {
		if (check == 1)
			return true;
		if (value % check == 0)
			return false;
		return (isPrimeRecur(value, --check));
	}

}
