
public class Card {
	private int suit;
	private int value;
	private boolean valid = true;
	
	public Card(int a, int b){
		setSuit(a);
		setValue(b);
		checkValid();
	}
	public Card(){
		this(0,0);
	}
	
	public int getSuit(){
		return suit;
	}
	public int getValue(){
		return value;
	}
	public boolean getValid(){
		return valid;
	}
	
	public void setSuit(int inp){
		suit = inp;
	}
	public void setValue(int inp){
		value = inp;
	}
	public void checkValid(){
		if(suit>0 && suit<5 && value >1 && value < 15) valid = true;
		else valid = false;
	}
	
	public String getString(){
		String result = "";
		
		switch (suit){
		case 1: result = result + "C";
				break;
		case 2: result = result + "D";
				break;
		case 3: result = result + "S";
				break;
		case 4: result = result + "H";
				break;
		}
		if(value<11)result = result + value;
		else if(value==11) result = result+"J";
		else if(value==12) result = result+"Q";
		else if(value==13) result = result+"K";
		else if(value==14) result = result+"A";
		return result;
		
	}
	
}


/* 			club diamond spade heart
	 		  1	    2	  3		4
	 

2 3 4 5 6 7 8 9 10 11 12 13 14
					J  Q  K  A








*/