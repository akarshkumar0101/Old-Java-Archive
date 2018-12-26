package game;

import java.util.List;

public class ClueAI extends Player {

	public static final String AI_NAME = "Akarsh's AI";
	public static final double CHANCES_NEEDED_TO_ACCUSE = 0.80;

	private Set prevGuess = null;
	// private Player prevGuessPlayer = null;

	public ClueAI(int id) {
		super(id);
	}

	public void handle(String input) {
		System.err.println(input);
		if (input.equals("Name")) {
			handleName();
		} else if (input.startsWith("Roomname")) {
			handleRoomname(input);
		} else if (input.startsWith("Weaponname")) {
			handleWeaponname(input);
		} else if (input.startsWith("Personname")) {
			handlePersonname(input);
		} else if (input.startsWith("Card From")) {
			handleCardFrom(input);
		} else if (input.startsWith("Card")) {
			handleCard(input);
		} else if (input.startsWith("Move")) {
			handleMove(input);
		} else if (input.startsWith("Opponent")) {
			handleOpponent(input);
		} else if (input.startsWith("Guess")) {
			handleGuess(input);
		} else if (input.startsWith("Disproved")) {
			handleDisproved(input);
		} else if (input.startsWith("Disprove")) {
			handleDisprove(input);
		} else if (input.startsWith("Accusation")) {
			handleAccusation(input);
		} else if (input.startsWith("Win")) {
			handleWin(input);
		}

	}

	private void handleName() {
		System.out.println(AI_NAME);
	}

	private void handleRoomname(String input) {
		String[] split = input.split(" ");
		int roomid = Integer.parseInt(split[1]);
		String roomname = "";
		for (int i = 2; i < split.length; i++) {
			roomname += split[i] + (i != split.length - 1 ? " " : "");
		}
		new Unit(Type.ROOM, roomid, roomname);
	}

	private void handleWeaponname(String input) {
		String[] split = input.split(" ");
		// int weaponcount = Integer.parseInt(split[1]);
		int weaponid = Integer.parseInt(split[2]);
		String weaponname = "";
		for (int i = 3; i < split.length; i++) {
			weaponname += split[i] + (i != split.length - 1 ? " " : "");
		}
		new Unit(Type.WEAPON, weaponid, weaponname);
	}

	private void handlePersonname(String input) {
		String[] split = input.split(" ");
		// int personcount = Integer.parseInt(split[1]);
		int personid = Integer.parseInt(split[2]);
		String personname = "";
		for (int i = 3; i < split.length; i++) {
			personname += split[i] + (i != split.length - 1 ? " " : "");
		}
		new Unit(Type.PERSON, personid, personname);
	}

	private void handleCard(String input) {
		String[] split = input.split(" ");

		int cardtype = Integer.parseInt(split[1]);
		int carddata = Integer.parseInt(split[2]);

		Unit card = Unit.getUnit(cardtype, carddata);
		super.addCard(card);
	}

	private Set madeCorrectGuess = null;

	private void handleMove(String input) {
		String[] data = input.split(" ");
		// long time = Long.parseLong(data[1]);
		int range = Integer.parseInt(data[2]);

		System.err.println("internal: " + getCards().size() + " / " + Knowledge.EXISTINGCARDS.size() + " / "
				+ Unit.ALLUNITS.size());

		if (madeCorrectGuess != null) {
			accuse(madeCorrectGuess);
		}

		int[] currentLocation = Knowledge.BOARD.locationOf(this);

		Knowledge.updateKnowledge();

		if (Knowledge.idealAccusation.getChances() >= CHANCES_NEEDED_TO_ACCUSE) {
			accuse(Knowledge.idealAccusation);
		} else {
			int x = -1, y = -1;
			try {

				Set idealGuess = Knowledge.idealGuess;
				Unit targetRoom = idealGuess.getRoom();

				if (targetRoom == null) {
					targetRoom = Knowledge.idealAccusation.getRoom();
				}

				int[] newcoor = getClosestLocation(currentLocation[0], currentLocation[1], range,
						Knowledge.BOARD.locationOfRoom(targetRoom.getID()));
				x = newcoor[0];
				y = newcoor[1];

				if (Knowledge.BOARD.getRoomID(x, y) != -1 && !Knowledge.EXISTINGCARDS.contains(idealGuess.getPerson())
						&& !Knowledge.EXISTINGCARDS.contains(idealGuess.getWeapon()) && !Knowledge.EXISTINGCARDS
								.contains(Unit.getUnit(Type.ROOM, Knowledge.BOARD.getRoomID(x, y)))) {
					System.out.println("Move Guess " + y + " " + x + " " + idealGuess.getPerson().getID() + " "
							+ idealGuess.getWeapon().getID() + " " + Knowledge.BOARD.getRoomID(x, y));
					madeCorrectGuess = new Set(Unit.getUnit(Type.ROOM, Knowledge.BOARD.getRoomID(x, y)),
							idealGuess.getWeapon(), idealGuess.getPerson());
				} else {
					System.out.println("Move " + y + " " + x);
				}
			} catch (Exception e) {
				do {
					x = (int) (Math.random() * Knowledge.BOARD.getGrid().length);
					y = (int) (Math.random() * Knowledge.BOARD.getGrid().length);
				} while (!Knowledge.BOARD.isInBoard(x, y)
						|| Board.dist(x, y, currentLocation[0], currentLocation[1]) > range);
				System.out.println("Move " + y + " " + x);
			}
			Knowledge.BOARD.putPlayerAt(this, x, y);

		}

	}

	private void handleOpponent(String input) {
		String[] data = input.split(" ");
		Player player = getPlayer(Integer.parseInt(data[1]));

		int y = Integer.parseInt(data[2]), x = Integer.parseInt(data[3]);

		Knowledge.BOARD.putPlayerAt(player, x, y);
	}

	private void handleGuess(String input) {
		String[] data = input.split(" ");

		Player player = Player.getPlayer(Integer.parseInt(data[1]));
		Unit room = Unit.getUnit(2, Integer.parseInt(data[4]));
		Unit weapon = Unit.getUnit(1, Integer.parseInt(data[3]));
		Unit person = Unit.getUnit(0, Integer.parseInt(data[2]));

		Set guessSet = new Set(room, weapon, person);

		player.doesNotHave(guessSet);
		prevGuess = guessSet;
	}

	private void handleDisprove(String input) {
		String[] data = input.split(" ");

		Unit room = Unit.getUnit(Type.ROOM, Integer.parseInt(data[4]));
		Unit weapon = Unit.getUnit(Type.WEAPON, Integer.parseInt(data[3]));
		Unit person = Unit.getUnit(Type.PERSON, Integer.parseInt(data[2]));

		List<Unit> cards = getCards();

		int ans = -1;
		for (Unit card : cards) {
			if (card.equals(person)) {
				ans = 0;
				break;
			}
			if (card.equals(weapon)) {
				ans = 1;
				break;
			}
			if (card.equals(room)) {
				ans = 2;
				break;
			}
		}

		// System.out.println("Disprove " + ans);
		System.out.println(ans);
	}

	private void handleDisproved(String input) {
		String[] data = input.split(" ");

		Player player = Player.getPlayer(Integer.parseInt(data[1]));

		player.refuteSet(prevGuess);

	}

	private void handleCardFrom(String input) {
		String[] data = input.split(" ");

		Player player = Player.getPlayer(Integer.parseInt(data[2]));

		Unit card = Unit.getUnit(Integer.parseInt(data[3]), Integer.parseInt(data[4]));

		player.addCard(card);

		madeCorrectGuess = null;
	}

	private void handleAccusation(String input) {
		String[] data = input.split(" ");

		Player player = Player.getPlayer(Integer.parseInt(data[1]));
		// Knowledge.cardExists(Unit.getUnit(0, Integer.parseInt(data[2])));
		// Knowledge.cardExists(Unit.getUnit(1, Integer.parseInt(data[3])));
		// Knowledge.cardExists(Unit.getUnit(2, Integer.parseInt(data[4])));

		Player.ALLPLAYERS.remove(player);
		Knowledge.BOARD.permanentlyRemovePlayer(player);

	}

	private void handleWin(String input) {
		System.out.println("YEEEEEET");
	}

	private void accuse(Set set) {
		System.out.println(
				"Accusation " + set.getPerson().getID() + " " + set.getWeapon().getID() + " " + set.getRoom().getID());
	}

	private static int[] getClosestLocation(int x, int y, int range, int... target) {

		int tx = target[0], ty = target[1];
		int newx = -1, newy = -1;

		if (Board.dist(x, y, tx, ty) > range) {
			if (Board.dist(x, y, tx, y) > range) {
				newy = y;
				if (tx > x) {
					newx = x + range;
				} else if (tx < x) {
					newx = x - range;
				}
			} else {
				range -= Board.dist(x, y, tx, y);
				newx = tx;
				if (ty > y) {
					newy = y + range;
				} else if (ty < y) {
					newy = y - range;
				}
			}
		} else {
			newx = tx;
			newy = ty;
		}

		return new int[] { newx, newy };
	}

}
