package main;

public class SelectionSortString {
	
	public static void sort(String[] array){
		for(int index=0; index<array.length-1; index++){
			int minindex =index;
			for(int i=index; i <array.length; i ++){
				if(array[i].compareTo(array[minindex]) < 0) minindex = i;
			}
			if(index == minindex) continue;
			
			String tempmin = array[minindex];
			array[minindex] = array[index];
			array[index] = tempmin;
			
		}
	
		
	}
	
}
