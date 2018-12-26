package main;

public class Main {
	public static void main(String[] args){
		int[] ints = {100,-99,0,2,5,2,1,88,34,7};
		String[] strings = {"tammy", "shawn", "sue", "camel", "laura"};
		
		InsertionSortString.sort(strings);
		
		for(int i: ints) System.out.print(i+" ");
		System.out.println();
		for(String i: strings) System.out.print(i+" ");
	}
}
