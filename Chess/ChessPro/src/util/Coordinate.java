package util;

public abstract class Coordinate {
    
    private static final int XLENGTH = 8;
    private static final int YLENGTH = 8;
    
    private final int x, y;
    
    private static final Coordinate coordinates[][] = new Coordinate[XLENGTH][YLENGTH];
    
    private Coordinate(int inp1, int inp2){
        x = inp1;
        y = inp2;
    }
    
    public static void initialize(){
        
        for(int x= 0 ; x<XLENGTH; x++){
            for(int y = 0 ; y<YLENGTH; y++){
                coordinates[x][y]= new Coordinate(x,y){};
            }
        }
    }
    
    public static Coordinate getCoordinate(int x, int y){
        if(x<0 || x>=XLENGTH || y <0 || y >=YLENGTH){
        	System.out.println("WARNING: GETCOORDINATE WAS CALLED AND RETURNED A NULL COORDINATE");
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
