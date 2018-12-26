package phys;

public class Vector {
	
	public double x;
	public double y;
	
	public Vector(){
		x=0;
		y=0;
	}
	public Vector(double x, double y){
		this.x=x;
		this.y=y;
	}
	public double getRad(){
		if(x==0) return Double.NaN;
		return Math.atan(y/x) + (x>0? 0:Math.PI);
	}
}
