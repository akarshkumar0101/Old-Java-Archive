
public class Trick extends SetOfCards{
	private int points;
	
	
	public Trick(){
		super();
		points = 0;
	}
	
	@Override
	public void add(Card card){
		if(array.size()<4){
			array.add(card);
			if(card.getSuit()==4){
				if(card.getValue()==14) points += 5;
				else points+=1;
			}
			if(card.getSuit()==3 && card.getValue()==12) points += 13;
		}
	}
	@Override
	public boolean isFull(){
		if(array.size()==4){
			return true;
		}
		return false;
	}
	public int getPoints(){
		return points;
	}
	
	
}
