package main;

public class SelectionSortInt {
	public static void sort(int[] array){
		
		for(int index=0; index<array.length-1; index++){
			int minindex =index;
			for(int i=index; i <array.length; i ++){
				if(array[i] < array[minindex]) minindex = i;
			}
			if(index == minindex) continue;
			
			int tempmin = array[minindex];
			array[minindex] = array[index];
			array[index] = tempmin;
			
		}
		
		
	}
}
