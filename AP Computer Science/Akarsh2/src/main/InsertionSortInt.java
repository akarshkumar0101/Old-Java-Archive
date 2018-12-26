package main;

public class InsertionSortInt {
	public static void sort(int[] array) {

		for (int index = 1; index < array.length; index++) {
			
			for(int i=index; i >0; i --){
				if(array[i] < array[i-1]){
					int tempmin = array[i];
					array[i] = array[i-1];
					array[i-1] = tempmin;
				}
				else break;
			}
			
		}

	}

}
