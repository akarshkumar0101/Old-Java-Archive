package util;

public enum PlayerType {

	HUMAN, COMPUTER;
	
	public static PlayerType myValueOf(String inp){
		if(inp.contains("h") || inp.contains("H")) return HUMAN;
		if(inp.contains("c") || inp.contains("C")) return COMPUTER;
		
		try{
			return valueOf(inp.toUpperCase());
		}catch(IllegalArgumentException e){
			return null;
		}
	}
	
}
