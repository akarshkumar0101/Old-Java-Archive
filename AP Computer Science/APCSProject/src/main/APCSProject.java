package main;

import java.util.ArrayList;

public class APCSProject {
	static ArrayList<String> strings = new ArrayList<String>();

	public static void main(String[] args) {
		add("hello");
		add("bob");
		add("holly");
		add("steve");
		add("fixer");
		add("akarsh");

		for (String s : strings) {
			System.out.println(s);
		}
	}

	public static void add(String s) {
		if (strings.isEmpty()) {
			strings.add(s);
			return;
		}
		if (s.compareTo(strings.get(0)) < 0) {
			strings.add(0, s);
			return;
		}
		for (int i = 0; i < strings.size() - 1; i++) {
			String a = strings.get(i);
			String b = strings.get(i + 1);
			if (s.compareTo(a) > 0 && s.compareTo(b) < 0) {
				strings.add(i + 1, s);
				return;
			}
		}
		strings.add(s);
		return;
	}
}