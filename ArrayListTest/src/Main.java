
public class Main {

	public static void main(String[] args) {
		MyArray array = new MyArray();
	
		array.add(3);
		array.printList();
		array.remove(0);
		
		array.printList();
		
		
		System.out.println("\n"+array.getSize());
		
		//System.out.println(array.get(4)+"\n");
		
		array.printList();
		
	}

}
