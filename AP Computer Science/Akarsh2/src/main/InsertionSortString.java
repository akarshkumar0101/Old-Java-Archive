package main;

public class InsertionSortString {
	public static void sort(String[] array) {

		for (int index = 1; index < array.length; index++) {

			for (int i = index; i > 0; i--) {
				if (array[i].compareTo(array[i-1]) < 0) {
					String tempmin = array[i];
					array[i] = array[i - 1];
					array[i - 1] = tempmin;
				} else
					break;
			}

		}

	}

}
