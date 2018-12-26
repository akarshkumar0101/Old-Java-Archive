package main;

import java.util.ArrayList;

public class Test {

	public static void main(String[] args) {
		ArrayList<Animal> list = new ArrayList<Animal>();
		
		for (int i = 0; i < 100; i++) {
			list.add(new Animal("Cat -" + i, (int) (Math.random() * 50)));
		}
		
		ArrayList<String> strings = new ArrayList<String>();
		strings.add("mary");
		strings.add("tom");
		strings.add("alice");
		strings.add("kim");
		strings.add("tina");
		strings.add("mark");
		
		ArrayList<Integer> ints = new ArrayList<Integer>();
		ints.add(5);
		ints.add(9);
		ints.add(3);
		ints.add(18);
		ints.add(-12);
		ints.add(21);
		
		Sorting.insertionSort(ints, Sorting.INCREASING);
		for (Object a : ints)
			System.out.println(a);

	}

}

class Animal implements Comparable<Animal> {

	private int weight;
	private String type;

	public Animal(String t, int w) {
		type = t;
		weight = w;
	}

	public String getType() {
		return type;
	}

	public int getWeight() {
		return weight;
	}

	@Override
	public int compareTo(Animal another) {
		return weight - another.weight;
	}

	@Override
	public String toString() {
		return type + " , weight= " + weight;
	}

}

class Sorting {
	public static final int INCREASING = 323;
	public static final int DECREASING = 932;

	public static <E extends Comparable<E>> void insertionSort(ArrayList<E> list, int sortingType) {
		if (sortingType == INCREASING) {
			for (int index = 0; index < list.size() - 1; index++) {

				for (int i = index + 1; i > 0; i--) {
					if (list.get(i).compareTo(list.get(i - 1)) < 0) {
						E temp = list.get(i - 1);
						list.set(i - 1, list.get(i));
						list.set(i, temp);
					} else
						break;
				}

			}

		} else if (sortingType == DECREASING) {
			for (int index = 0; index < list.size() - 1; index++) {

				for (int i = index + 1; i > 0; i--) {
					if (list.get(i).compareTo(list.get(i - 1)) > 0) {
						E temp = list.get(i - 1);
						list.set(i - 1, list.get(i));
						list.set(i, temp);
					} else
						break;
				}

			}

		}

	}
}