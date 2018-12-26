
public class Main {

	public static void main(String[] args) {
		
		int x=0, y=0, z=0;
		do{
			x = (int) (Math.random() * 15);
		}while(x<=0 || x>15);
		do{
			y = (int) (Math.random() * 15);
		}while(y<=0 || y>15);
		do{
			z = (int) (Math.random() * 15);
		}while(z<=0 || z>15);
		
		Matrix a = new Matrix(x,y);
		
		
		a.randomize();
		System.out.println("Matrix A = ");
		a.display();
		
		System.out.println("\n");
		
		Matrix b = new Matrix(y,z);
		
		b.randomize();
		System.out.println("Matrix B = ");
		b.display();
		
		
		System.out.println("\n");
		
		Matrix result = multiply(a,b);
		
		System.out.println("Matrix A * B = ");
		result.display();
		
	}
	
	
	public static Matrix multiply(Matrix a, Matrix b){
		
		boolean even = (a.width%2==0);
		
		boolean alternate = true;
		
		Matrix result = new Matrix(a.height, b.width);
		
		for(int h=0;h< a.height; h ++){
			for(int w = 0; w< b.width;w++){
				result.add(findNum(a,h,b,w,alternate));
				
				if(!even) alternate = !alternate;
			}
		}
		
		
		
		
		
		
		return result;
	}
	
	public static int findNum(Matrix a, int rNum, Matrix b, int cNum, boolean alternate){
		int result = 0;
		
		for(int i = 0; i < a.width; i ++){
			if(alternate){
				result = result + a.get(rNum, i) * b.get(i, cNum);
			}
			else{
				result = result - a.get(rNum, i) * b.get(i, cNum);
			}
			alternate = !alternate;
		}
		
		
		return result;
	}

}
