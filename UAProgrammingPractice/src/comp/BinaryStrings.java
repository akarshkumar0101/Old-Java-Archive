package comp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class BinaryStrings {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		while (scan.hasNextLine()) {
			ArrayList<String> inps = new ArrayList<String>();
			while (true) {
				String str = scan.nextLine();
				if (str.isEmpty()) {
					break;
				}
				inps.add(str);
			}
			for (int i1 = 0; i1 < inps.size(); i1++) {
				for (int i2 = 0; i2 < inps.size(); i2++) {
					if (i1 == i2) {
						continue;
					}
					if (leftContains(inps.get(i1), inps.get(i2)) || rightContains(inps.get(i1), inps.get(i2))) {
						inps.set(i2, "");
					}

				}
			}
			Iterator<String> itr = inps.iterator();
			while (itr.hasNext()) {
				if (itr.next().isEmpty()) {
					itr.remove();
				}
			}

			String a = inps.get(0);
			String b = inps.get(1);

			String ans = "";
			try {
				ans = combine(a, b);
			} catch (Exception e) {
				ans = combine(b, a);
			}
			System.out.println(ans);

		}
	}
	// 10111
	// 101
	// 11
	// 111
	// 10
	// 0111

	public static String combine(String a, String b) {
		String ans = "";

		int overlapi = -1;
		for (int bi = b.length() - 1; bi >= 1; bi--) {
			String subb = b.substring(0, bi);
			if (rightContains(a, subb)) {
				overlapi = bi;
				break;
			}
		}
		ans += a + b.substring(overlapi);

		return ans;
	}

	public static boolean leftContains(String a, String b) {
		try {
			return a.substring(0, b.length()).equals(b);
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean rightContains(String a, String b) {
		try {
			return a.substring(a.length() - b.length(), a.length()).equals(b);
		} catch (Exception e) {
			return false;
		}
	}
}
