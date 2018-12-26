

public class Matrix {
	
	int addh;
	int addw;
	
	int height;
	int width;
	private int [][] matrix;
	
	public Matrix(int inp1, int inp2){
		
		addh = 0;
		addw = 0;
		
		height = inp1;
		width=inp2;
		
		matrix = new int[height][width];
		resetMatrix();
		
		
	}
	
	public void resetMatrix(){
		for(int x = 0; x<width; x++){
			for(int y = 0; y<height; y++){
				matrix[y][x] = 0;
			}
		}
	}
	
	public void randomize(){
		int random = 0;
		
		for(int x = 0; x<width; x++){
			for(int y = 0; y<height; y++){
				random = (int) (Math.random() * 10);
				
				matrix[y][x] = random;
				
			}
		}
		
	}
	
	public void display(){
		
		
		
		for(int y = 0; y<height; y++){
			for(int x = 0; x<width; x++){
				System.out.print(matrix[y][x]+" ");
			}
			System.out.println();
		}
	}
	
	public int [][] getMatrix(){
		return matrix;
	}
	public int get(int h, int w){
		return matrix[h][w];
	}
	public void set(int h, int w, int value){
		matrix[h][w] = value;
	}

	public void add(int value){
		
		
		if(addw == width){
			addh++;
			addw = 0;
		}
		
		matrix[addh][addw] = value;
		
		
		addw++;
		
		
	}
	
}
