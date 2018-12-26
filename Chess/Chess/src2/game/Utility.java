package game;

import java.util.ArrayList;

public abstract class Utility {
	private Utility() {
	}

	public static boolean areSameCoor(int[]... coors) {
		return coors[0][0] == coors[1][0] && coors[0][1] == coors[1][1];
	}

	// no use for ellipses b/c it would make unnecessarily complex 3d array
	public static boolean areSameMove(int[][] move1, int[][] move2) {
		return areSameCoor(move1[0], move2[0]) && areSameCoor(move1[1], move2[1]);
	}

	public static boolean arrayListContainsMove(ArrayList<Object> list, Object o) {
		if (o.getClass() == int[][].class) {
			for (Object element : list) {

				if (element.getClass() == int[][].class && areSameMove((int[][]) element, (int[][]) o))
					return true;
			}
			return false;
		} else
			return list.contains(o);
	}

	static byte[][] locales;
	static {
		locales = new byte[64][2];
		// for(byte x=0; )
	}
}
