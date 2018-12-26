import java.util.ArrayList;

public class Player {
	
	private int totalPoints;
	private int points;
	private int tricks;
	private String name;
	private SetOfCards cards;
	private ArrayList<Trick> SetOfTricks;
	
	public Player(String nameInp){
		points  = 0;
		tricks = 0;
		name = nameInp;
		cards = new SetOfCards();
		SetOfTricks = new ArrayList<Trick>();
	}
	
	public int getPoints(){
		return points;
	}
	public int getTotalPoints(){
		return totalPoints;
	}
	public void addTotalPoints(int inp){
		totalPoints += inp;
	}
	public int getTricks(){
		return tricks;
	}
	public String getName(){
		return name;
	}
	public SetOfCards getHand(){
		return cards;
	}
	
	public void winTrick(Trick trick){
		SetOfTricks.add(trick);
		points += trick.getPoints(); 
	}
	public void nextDeck(){
		points = 0;
		tricks = 0;
		SetOfTricks = new ArrayList<Trick>();
		cards = new SetOfCards();
	}
	public void addCard(Card card){
		cards.add(card);
	}
	
}
