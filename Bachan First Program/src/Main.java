
public class Main {

	public static void main(String[] args) {
		
		
		
		
		MyMatrix a = new MyMatrix(15,3);
		a.displayThisMatrix();

		System.out.println("\n\n\n");
		
		
		a.make0();
		a.displayThisMatrix();
		
		System.out.println("\n\n\n");
		
		a.makeRandom();
		a.displayThisMatrix();
	}

}
