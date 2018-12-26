
import java.util.Scanner;

public class Game {
	private Player[] players;
	private Trick currentTrick;
	private Scanner scan = new Scanner(System.in);
	private int playerTurn;
	private int decks;
	private int win;
	private boolean heartsBroken;
	private boolean firstTurn;
	
	public Game(){
		
		players = new Player[4];
		
		System.out.print("Type in name of player 1: ");
		String name1 = scan.nextLine();
		System.out.println();
		System.out.print("Type in name of player 2: ");
		String name2 = scan.nextLine();
		System.out.println();
		System.out.print("Type in name of player 3: ");
		String name3 = scan.nextLine();
		System.out.println();
		System.out.print("Type in name of player 4: ");
		String name4 = scan.nextLine();
		System.out.println();
		
		
		
		players[0] = new Player(name1);
		players[1] = new Player(name2);
		players[2] = new Player(name3);
		players[3] = new Player(name4);
		
		playerTurn=-1;
		win = -1;
		firstTurn = true;
		currentTrick = new Trick();
		heartsBroken = false;
		System.out.println("clear");
		
		
	}
	
	public void start(){
		System.out.println("Type in 'start' to start turns.");
		dealDeck();
		showAll();
		
		for(int x = 0; x<4;x++){
			for(int y=0; y <12; y ++){
				if(players[x].getHand().getCard(y).getSuit()==1 && players[x].getHand().getCard(y).getValue()==2){
					playerTurn =x;
					break;
				}
				if(players[x].getHand().getCard(y).getSuit()>1)break;
			}
			if(playerTurn!=-1)break;
		}
		
		while(win==-1){
			currentTrick = new Trick();
			
			for(int i=0;i <4;i ++){
				turn(playerTurn);
				playerTurn++;
				if(playerTurn==4)playerTurn=0;
			}
			
			if(currentTrick.isFull())System.out.println("Trick is done");
			int currentSuit = currentTrick.getCard(0).getSuit();
			int maxVal = currentTrick.getCard(0).getValue();
			if(currentTrick.getCard(1).getValue() > maxVal && currentTrick.getCard(1).getSuit() == currentSuit) maxVal = currentTrick.getCard(1).getValue();
			if(currentTrick.getCard(2).getValue() > maxVal && currentTrick.getCard(2).getSuit() == currentSuit) maxVal = currentTrick.getCard(2).getValue();
			if(currentTrick.getCard(3).getValue() > maxVal && currentTrick.getCard(3).getSuit() == currentSuit) maxVal = currentTrick.getCard(3).getValue();
			int maxCard = 0;
			for(int i=3; i>-1;i--){
				if(currentTrick.getCard(i).getSuit()==currentSuit && currentTrick.getCard(i).getValue() == maxVal){
					maxCard = i;
					break;
				}
			}
			int turnsAgo = 4-maxCard;
			int winnerOfTrick = playerTurn-turnsAgo;
			if (winnerOfTrick < 0) winnerOfTrick += 4;
			
			players[winnerOfTrick].winTrick(currentTrick);
			
			System.out.println(players[winnerOfTrick].getName() + " won the trick!");
			playerTurn = winnerOfTrick;
			checkShootingTheMoon();
			if(players[0].getHand().getSize()==0){
				
				decks++;
				heartsBroken = false;
				firstTurn = true;
				for(int i=0;i <4;i++){
					players[i].addTotalPoints(players[i].getPoints());
					players[i].nextDeck();
				}
			}
			if(decks==4){
				win = won();
			}
			
		}
		
		
		
	}
	public void turn(int turn){
		inputStart();
		boolean first = (currentTrick.getSize()==0);
		
		System.out.println("It's " +players[turn].getName()+"'s turn. You have "+players[turn].getPoints()+" points.\n");
		
		if(first) System.out.println("First card in trick.");
		if(firstTurn) System.out.println("You have to play 2 of Clubs to start off.");
		else{
			System.out.println("Current Trick:");
			for(int i=0;i <currentTrick.getSize(); i++){
				System.out.println("\t\t\t"+currentTrick.getCard(i).getString());
			}
			System.out.println();
		}
		System.out.print("Your hand: ");
		for(int i=0;i < players[turn].getHand().getSize();i++){
			System.out.print(players[turn].getHand().getCard(i).getString()+" ");
		}
		
		int chosenCard=0;
		Card chosen = new Card();
		
		if(firstTurn){
			int not2Club = 0;
			do{
				if(not2Club> 0)System.out.println("You have to play the 2 of Clubs!");
				chosenCard = scan.nextInt()-1;
				chosen = players[turn].getHand().getCard(chosenCard);
				not2Club++;
			}while(chosen.getValue()!=2 || chosen.getSuit()!=1);
		}
		else if(!first){
			int currentSuit = currentTrick.getCard(0).getSuit();
			boolean hasSuit = false;
			for(int i=0; i < players[turn].getHand().getSize();i ++){
				if(players[turn].getHand().getCard(i).getSuit()== currentSuit) hasSuit = true;
			}
			int cantPlay = 0;
			
			do{
				if(cantPlay>0)System.out.println("Invalid card, there is a card in the same suit you can play.");
				chosenCard = scan.nextInt()-1;
				chosen = players[turn].getHand().getCard(chosenCard);
				cantPlay++;
			}while(hasSuit && chosen.getSuit() != currentSuit);
		}
		else{
			int cantPlay = 0;
			do{
				if(cantPlay>0)System.out.println("You cannot play hearts, it hasn't been broken yet.");
				chosenCard = scan.nextInt()-1;
				chosen = players[turn].getHand().getCard(chosenCard);
				cantPlay++;
			}while(!heartsBroken && chosen.getSuit()==4);
		}
		
		players[turn].getHand().remove(chosenCard);
		currentTrick.add(chosen);
		firstTurn =false;
		
	}
	
	public void nextDeck(){
		
	}
	
	public void dealDeck(){
		int random = 0;
		for(int suit = 1; suit<5; suit++){
			for(int value=2; value< 15; value++){
				do{
					random = (int)(Math.random()*4);
				}while(players[random].getHand().isFull());
				players[random].addCard(new Card(suit, value));
			}
		}
		
		
		
	}
	
	
	
	public void showAll(){
		for(int i=0;i <13; i ++){
			System.out.println(players[0].getHand().getCard(i).getString() + "\t" +players[1].getHand().getCard(i).getString() + "\t" +players[2].getHand().getCard(i).getString() + "\t" +players[3].getHand().getCard(i).getString());
		}
	}
	
	public void inputStart(){
		String input = "";
		do{
			input = scan.nextLine();
		}while(!input.equals("start"));
	}
	public void checkShootingTheMoon(){
		for(int i=0;i<4;i ++){
			if(players[i].getPoints() ==30){
				win=i;
				players[0].addTotalPoints(30);
				players[1].addTotalPoints(30);
				players[2].addTotalPoints(30);
				players[3].addTotalPoints(30);
				players[i].addTotalPoints(-30);
			}
		}
	}
	public int won(){
		int leastPoints = players[0].getTotalPoints();
		if(players[1].getTotalPoints() < leastPoints) leastPoints = players[1].getTotalPoints();
		if(players[2].getTotalPoints() < leastPoints) leastPoints = players[2].getTotalPoints();
		if(players[3].getTotalPoints() < leastPoints) leastPoints = players[3].getTotalPoints();
		
		for(int i = 0;i <4;i++){
			if(players[i].getPoints() == leastPoints) return i;
		}
		return -1;
		
	}
	
	
}
