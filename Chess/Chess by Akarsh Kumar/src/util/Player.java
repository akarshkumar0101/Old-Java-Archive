package util;

public abstract class Player {

	private Side side;
	private PlayerType playerType;
	
	private Player(Side s, PlayerType t){
		side = s;
		playerType = t;
	}
	public static Player getPlayer(Side s, PlayerType t){
		return new Player(s,t){};
	}
	public Side getSide(){
		return side;
	}
	public PlayerType getPlayerType(){
		return playerType;
	}
	
}
