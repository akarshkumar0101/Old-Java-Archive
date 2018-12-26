
public class MyMatrix {
	
	int h;
	int w;
	int [][] shanu;
	
	public MyMatrix(int input1, int input2) {
		
		w = input1;
		h = input2;
		shanu = new int[w][h];
		make0();
		
		
		
		
		
		
	}
	
	
	public void make0() {
		
		for (int a=0; a<w; a++){
			for (int b=0; b<h; b++){
				shanu [a][b]=0;
			}
		}
		
		
		
	}
	
	public void makeRandom() {
		
		int random;
		
		
		for (int a=0; a<w; a++){
			for (int b=0; b<h; b++){
				
				random = (int) (Math.random()*10);
				shanu [a][b] = random;
				
				
				
			}
		}
		
	}
	
	public void displayThisMatrix(){
		
		for (int b=0; b<h; b++){
			for (int a=0; a<w; a++){	
				System.out.print(shanu [a][b] +" "  );
				
			}
			System.out.println();
		}
		
	}
	
}
