import java.util.ArrayList;

public class SetOfCards {
	protected ArrayList<Card> array;
	
	
	public SetOfCards(){
		array = new ArrayList<Card>();
		array.clear();
	}
	
	public void add(Card card){
		if(array.size()<13){
			array.add(card);
		}
	}
	public int number(){
		return array.size();
	}
	public Card getCard(int index){
		return array.get(index);
	}
	public boolean isFull(){
		if(array.size()==13)return true;
		return false;
	}
	public void clear(){
		array.clear();
	}
	public int getSize(){
		return array.size();
	}
	public void remove(int i){
		array.remove(i);
	}
}
