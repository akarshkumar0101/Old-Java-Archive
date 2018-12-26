package game;

import java.util.ArrayList;

public class Square {

	private final ArrayList<Player> players;

	private final int roomID;

	public Square(int roomID) {
		this.roomID = roomID;
		players = new ArrayList<>(roomID==0? 10:3);
	}
	public int getRoomID(){
		return roomID;
	}
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
}
