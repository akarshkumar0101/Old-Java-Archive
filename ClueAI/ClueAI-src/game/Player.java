package game;

import java.util.ArrayList;
import java.util.List;

public class Player {

	public static final List<Player> ALLPLAYERS = new ArrayList<>(10);

	protected final int id;

	private final List<Unit> cards;
	private final List<Unit> doesNotHave;

	private final List<Set> refuted;

	public Player(int id) {
		this.id = id;
		cards = new ArrayList<>(30);
		doesNotHave = new ArrayList<>(50);

		refuted = new ArrayList<>(10);

		ALLPLAYERS.add(this);
		Knowledge.BOARD.putPlayerAt(this, Knowledge.BOARD.locationOfRoom(0));
	}

	public int[] getLocation() {
		return Knowledge.BOARD.locationOf(this);
	}

	public int getID() {
		return id;
	}

	public void addCard(Unit card) {
		cards.add(card);
		Knowledge.cardExists(card);
	}

	public void doesNotHave(Set set) {
		doesNotHave(set.getRoom());
		doesNotHave(set.getWeapon());
		doesNotHave(set.getPerson());

	}

	public void doesNotHave(Unit card) {
		doesNotHave.add(card);
	}

	public List<Unit> getCards() {
		return cards;
	}

	public List<Unit> getDoesNotHave() {
		return doesNotHave;
	}

	public List<Set> getRefuted() {
		return refuted;
	}

	public void refuteSet(Set set) {
		refuted.add(set);
	}

	public static Player getPlayerThatHas(Unit card) {
		for (Player player : ALLPLAYERS) {
			if (player.cards.contains(card))
				return player;
		}
		return null;
	}

	public static Player getPlayer(int id) {
		for (Player player : ALLPLAYERS) {
			if (player.id == id)
				return player;
		}
		return null;
	}
}
