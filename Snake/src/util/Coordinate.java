package util;

public class Coordinate {
    
    public static final int XLENGTH = 70;
    public static final int YLENGTH = 40;
    
    private final int x, y;
    
    private static final Coordinate [][] coordinates  = new Coordinate[XLENGTH][YLENGTH];
    
    static{
    	 for(int x= 0 ; x<XLENGTH; x++){
             for(int y = 0 ; y<YLENGTH; y++){
                 coordinates[x][y]= new Coordinate(x,y);
             }
         }
    }
    
    private Coordinate(int inp1, int inp2){
        x = inp1;
        y = inp2;
    }
    
    public boolean isOut(){
    	if(y ==0 || y ==1 ||y == YLENGTH-1 || y ==YLENGTH-2 || x ==0 || x ==1 ||x ==XLENGTH-1 || x ==XLENGTH-2) return true;
    	return false;
    }
    
    public Coordinate getRealCoordinate(){
    	if(x == 1) return getCoordinate(XLENGTH -3,y);
    	if(x == XLENGTH -2) return getCoordinate(2,y);
    	if(y == 1) return getCoordinate(x, YLENGTH - 3);
    	if(y == YLENGTH -2) return getCoordinate(x, 2);
    	
    	return this;
    }
    
    public static Coordinate getCoordinate(int x, int y){
        if(x<0 || x>=XLENGTH || y <0 || y >=YLENGTH){
        	return null;
        }
        return coordinates[x][y];
    }
    @Override
    public String toString(){
        return "("+x+", " +y+ ")";
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    
    
}
