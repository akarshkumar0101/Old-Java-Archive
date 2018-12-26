package main;

import java.util.ArrayList;
import java.util.Collections;

public class Knapsack {

	public static void main(String[] args) {
		// always wt, val
		int[] wts = { 10, 20, 30 };
		int[] vals = { 60, 100, 120 };
		final int wtlim = 25;

		int[][] ans = KnapSackSolution.calKnap(wts, vals, wtlim);

		System.out.println("Optimal condition:");
		for (int[] diamond : ans) {
			System.out.println("wt: " + diamond[0] + ", val:" + diamond[1]);
		}

	}

}

class KnapSackSolution {

	public static int[][] calKnap(int[] wts, int[] vals, int wtlim) {
		ArrayList<Diamond> diamonds = new ArrayList<Diamond>();
		for (int i = 0; i < wts.length; i++) {
			if (wts[i] <= wtlim) {
				diamonds.add(new Diamond(wts[i], vals[i]));
			}
		}
		Solution solution = new Solution(wtlim);

		Collections.sort(diamonds);
		Collections.reverse(diamonds);

		for (Diamond d : diamonds) {
			if (solution.canAdd(d)) {
				solution.add(d);
			} else {
				break;
			}
		}

		int[][] rtrn = new int[solution.list.size()][2];
		for (int i = 0; i < solution.list.size(); i++) {
			rtrn[i][0] = solution.list.get(i).wt;
			rtrn[i][1] = solution.list.get(i).val;
		}
		return rtrn;
	}

	private static class Diamond implements Comparable<Diamond> {
		public final int wt;
		public final int val;
		public final int eff;

		public Diamond(int wt, int val) {
			this.wt = wt;
			this.val = val;
			eff = val / wt;
		}

		@Override
		public int compareTo(Diamond o) {
			if (eff < o.eff)
				return -1;
			else if (eff > o.eff)
				return 1;
			return 0;
		}
	}

	private static class Solution {
		public ArrayList<Diamond> list = new ArrayList<Diamond>();
		public final int wtlim;

		public Solution(int wtlim) {
			this.wtlim = wtlim;
		}

		public boolean canAdd(Diamond d) {
			return !((totalWt() + d.wt) > wtlim);
		}

		public void add(Diamond d) {
			list.add(d);
		}

		public int totalWt() {
			int w = 0;
			for (Diamond d : list) {
				w += d.wt;
			}
			return w;
		}

		public int totalVal() {
			int v = 0;
			for (Diamond d : list) {
				v += d.val;
			}
			return v;
		}
	}

	private static class Group {
		public ArrayList<Diamond> list = new ArrayList<Diamond>();

		public Group() {
		}

		public int totalVal() {
			int v = 0;
			for (Diamond d : list) {
				v += d.val;
			}
			return v;
		}

		public int totalWt() {
			int w = 0;
			for (Diamond d : list) {
				w += d.wt;
			}
			return w;
		}

		public int eff() {
			return totalVal() / totalWt();
		}
	}
}