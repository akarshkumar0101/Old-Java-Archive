import java.util.ArrayList;
import java.util.Arrays;

public final class Sorting {
	/**
	 * "aa","zz"
	 */
	public static final int INCREASING = 40302034;
	/**
	 * "zz", "aa"
	 */
	public static final int DECREASING = 85723894;

	private Sorting() {
	}

	public static void main(String[] args) {
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(234);
		list.add(45);
		list.add(32);
		list.add(87);
		list.add(6876);
		
		Integer[] array = new Integer[5];
		array[0] = 3;
		array[1] = 45;
		array[2] = 32;
		array[3] = 87;
		array[4] = 6876;
		Arrays.sort(new int[3]);
		sort(list,INCREASING);
		sort(array,INCREASING);
		
		
		for(int i: list){
			System.out.println(i);
		}
	}

	public static int[] sort(int[] arrayToSort) {
		
		return null;
	}

	public static <E extends Comparable<E>> void sort(E[] arrayToSort, int sortingType) {
		ArrayList<E> list = new ArrayList<E>();
		for(E element: arrayToSort)
			list.add(element);
		sort(list, sortingType);
		int i=0;
		for(E ele: list){
			arrayToSort[i] = ele;
			i++;
		}
	}

	public static <E extends Comparable<E>> void sort(ArrayList<E> listToSort, int sortingType) {

		ArrayList<ArrayList<E>> base = new ArrayList<ArrayList<E>>();
		for (int i = 0; i < listToSort.size(); i++) {
			ArrayList<E> temp = new ArrayList<E>();
			temp.add(listToSort.get(i));
			base.add(temp);
		}
		int i=0;
		for(E ele:combineAllSortedLists(base, sortingType).get(0)){
			listToSort.set(i, ele);
			i++;
		}
	}

	private static <E extends Comparable<E>> ArrayList<ArrayList<E>> combineAllSortedLists(
			ArrayList<ArrayList<E>> bases, int sortingType) {
		if (bases.size() == 1)
			return bases;

		if (bases.size() % 2 == 1) {
			bases.set(0, combineSortedLists(bases.get(0), bases.get(1), sortingType));
			bases.remove(1);
		}

		for (int i = 0; i < bases.size() / 2; i++) {
			bases.set(i, combineSortedLists(bases.get(i), bases.get(i + 1), sortingType));
			bases.remove(i + 1);
		}

		return combineAllSortedLists(bases, sortingType);
	}

	private static <E extends Comparable<E>> ArrayList<E> combineSortedLists(ArrayList<E> firstList,
			ArrayList<E> secondList, int sortingType) {
		ArrayList<E> result = new ArrayList<E>();
		int firindex = 0, secindex = 0;

		if (sortingType == INCREASING) {
			while (firindex != firstList.size() && secindex != secondList.size()) {
				if (firstList.get(firindex).compareTo(secondList.get(secindex)) <= 0) {
					result.add(firstList.get(firindex));
					firindex++;
				} else if (firstList.get(firindex).compareTo(secondList.get(secindex)) > 0) {
					result.add(secondList.get(secindex));
					secindex++;
				}
			}
		} else if (sortingType == DECREASING) {
			while (firindex != firstList.size() && secindex != secondList.size()) {
				if (firstList.get(firindex).compareTo(secondList.get(secindex)) >= 0) {
					result.add(firstList.get(firindex));
					firindex++;
				} else if (firstList.get(firindex).compareTo(secondList.get(secindex)) < 0) {
					result.add(secondList.get(secindex));
					secindex++;
				}
			}
		}

		if (firindex != firstList.size()) {
			for (; firindex < firstList.size(); firindex++) {
				result.add(firstList.get(firindex));
			}
		} else if (secindex != secondList.size()) {
			for (; secindex < secondList.size(); secindex++) {
				result.add(secondList.get(secindex));
			}
		}
		return result;
	}

}

class Sorting2 {
	/**
	 * "aa","zz"
	 */
	public static final int INCREASING = 40302034;
	/**
	 * "zz", "aa"
	 */
	public static final int DECREASING = 85723894;

	private Sorting2() {
	}

	public static int[] sort(int[] arrayToSort) {

		return null;
	}

	public static <E extends Comparable<E>> E[] sort(E[] arrayToSort, int sortingType) {

		return null;
	}

	public static <E extends Comparable<E>> ArrayList<E> sort(ArrayList<E> listToSort, int sortingType) {

		ArrayList<ArrayList<E>> base = new ArrayList<ArrayList<E>>();
		for (int i = 0; i < listToSort.size(); i++) {
			ArrayList<E> temp = new ArrayList<E>();
			temp.add(listToSort.get(i));
			base.add(temp);
		}

		return combineAllSortedLists(base, sortingType).get(0);
	}

	private static <E extends Comparable<E>> ArrayList<ArrayList<E>> combineAllSortedLists(
			ArrayList<ArrayList<E>> bases, int sortingType) {
		if (bases.size() == 1)
			return bases;

		if (bases.size() % 2 == 1) {
			bases.set(0, combineSortedLists(bases.get(0), bases.get(1), sortingType));
			bases.remove(1);
		}

		for (int i = 0; i < bases.size() / 2; i++) {
			bases.set(i, combineSortedLists(bases.get(i), bases.get(i + 1), sortingType));
			bases.remove(i + 1);
		}

		return combineAllSortedLists(bases, sortingType);
	}

	private static <E extends Comparable<E>> ArrayList<E> combineSortedLists(ArrayList<E> firstList,
			ArrayList<E> secondList, int sortingType) {
		ArrayList<E> result = new ArrayList<E>();
		int firindex = 0, secindex = 0;

		if (sortingType == INCREASING) {
			while (firindex != firstList.size() && secindex != secondList.size()) {
				if (firstList.get(firindex).compareTo(secondList.get(secindex)) <= 0) {
					result.add(firstList.get(firindex));
					firindex++;
				} else if (firstList.get(firindex).compareTo(secondList.get(secindex)) > 0) {
					result.add(secondList.get(secindex));
					secindex++;
				}
			}
		} else if (sortingType == DECREASING) {
			while (firindex != firstList.size() && secindex != secondList.size()) {
				if (firstList.get(firindex).compareTo(secondList.get(secindex)) >= 0) {
					result.add(firstList.get(firindex));
					firindex++;
				} else if (firstList.get(firindex).compareTo(secondList.get(secindex)) < 0) {
					result.add(secondList.get(secindex));
					secindex++;
				}
			}
		}

		if (firindex != firstList.size()) {
			for (; firindex < firstList.size(); firindex++) {
				result.add(firstList.get(firindex));
			}
		} else if (secindex != secondList.size()) {
			for (; secindex < secondList.size(); secindex++) {
				result.add(secondList.get(secindex));
			}
		}
		return result;
	}
}