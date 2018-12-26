package game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Knowledge {

	public static Board BOARD;

	public static final List<Unit> EXISTINGCARDS = new ArrayList<>(100);

	public static Set idealAccusation;
	public static Set idealGuess;

	public static void establishBoard(int[][] board) {
		BOARD = new Board(board);
	}

	public static void cardExists(Unit unit) {
		if (!EXISTINGCARDS.contains(unit)) {
			EXISTINGCARDS.add(unit);
		}
	}

	@SuppressWarnings("unchecked")
	public static void updateKnowledge() {
		updateRefutedKnowledge();

		List<Unit> possibleRooms = (List<Unit>) ((ArrayList<Unit>) Unit.ROOMS).clone();
		List<Unit> possibleWeapons = (List<Unit>) ((ArrayList<Unit>) Unit.WEAPONS).clone();
		List<Unit> possiblePeople = (List<Unit>) ((ArrayList<Unit>) Unit.PEOPLE).clone();

		for (Unit card : EXISTINGCARDS) {
			card.setChance(0);
			if (card.getType() == Type.ROOM) {
				possibleRooms.remove(card);
			} else if (card.getType() == Type.WEAPON) {
				possibleWeapons.remove(card);
			} else if (card.getType() == Type.PERSON) {
				possiblePeople.remove(card);
			}
		}

		factorInChances(possibleRooms);
		factorInChances(possibleWeapons);
		factorInChances(possiblePeople);

		determineIdealAccusation(possibleRooms, possibleWeapons, possiblePeople);
		determineIdealGuess(possibleRooms, possibleWeapons, possiblePeople);
	}

	private static void updateRefutedKnowledge() {
		// if a player refuted a set with card A, but it is known that the
		// player definitely doesn't have A or that another player definitely
		// does
		// have A, then will remove A from the refuted Set, because it was not
		// the reason for refute
		for (Player player : Player.ALLPLAYERS) {
			Iterator<Set> it = player.getRefuted().iterator();
			while (it.hasNext()) {
				Set set = it.next();
				Unit room = set.getRoom(), weapon = set.getWeapon(), person = set.getPerson();

				if (player.getDoesNotHave().contains(room)
						|| (EXISTINGCARDS.contains(room) && player != Player.getPlayerThatHas(room))) {
					set.getUnitArray()[0] = null;
				}
				if (player.getDoesNotHave().contains(weapon)
						|| (EXISTINGCARDS.contains(weapon) && player != Player.getPlayerThatHas(weapon))) {
					set.getUnitArray()[1] = null;
				}
				if (player.getDoesNotHave().contains(person)
						|| (EXISTINGCARDS.contains(person) && player != Player.getPlayerThatHas(person))) {
					set.getUnitArray()[2] = null;
				}

				if (set.numUnits() == 1) {
					// then definitely does have that card.
					Unit card = set.getRoom();
					if (card == null) {
						card = set.getWeapon();
					}
					if (card == null) {
						card = set.getPerson();
					}

					if (!player.getCards().contains(card)) {
						player.addCard(card);
					}

					it.remove();
				}

			}
		}

	}

	private static void determineIdealAccusation(List<Unit> possibleRooms, List<Unit> possibleWeapons,
			List<Unit> possiblePeople) {
		Unit room = null, weapon = null, person = null;

		room = getBestChances(possibleRooms);
		weapon = getBestChances(possibleWeapons);
		person = getBestChances(possiblePeople);

		idealAccusation = new Set(room, weapon, person);
	}

	private static void determineIdealGuess(List<Unit> possibleRooms, List<Unit> possibleWeapons,
			List<Unit> possiblePeople) {
		Unit room = null, weapon = null, person = null;

		room = getWorstChances(possibleRooms);
		weapon = getWorstChances(possibleWeapons);
		person = getWorstChances(possiblePeople);

		idealGuess = new Set(room, weapon, person);
	}

	private static Unit getWorstChances(List<Unit> units) {
		Unit worstUnit = units.get(0);
		double worstChance = 1;
		for (Unit unit : units) {
			if (unit.getChance() < worstChance) {
				worstChance = unit.getChance();
				worstUnit = unit;
			}
		}
		return worstUnit;
	}

	private static Unit getBestChances(List<Unit> units) {
		Unit bestUnit = units.get(0);
		double bestChance = -1;
		for (Unit unit : units) {
			if (unit.getChance() > bestChance) {
				bestChance = unit.getChance();
				bestUnit = unit;
			}
		}
		return bestUnit;
	}

	private static void factorInChances(List<Unit> possibleUnits) {
		ArrayList<Object> splitPercentages = new ArrayList<>(possibleUnits.size());

		double universalChance = (double) 1 / possibleUnits.size();

		for (Unit unit : possibleUnits) {
			unit.setChance(universalChance);

			double chancesExists = maxChancesCardExists(unit);
			if (chancesExists == -1) {
				continue;
			}
			double chancesRight = 1 - chancesExists;
			double newChance = chancesRight * universalChance;
			double splitAmongRest = universalChance - newChance;

			unit.setChance(newChance);
			splitPercentages.add(unit);
			splitPercentages.add(splitAmongRest);
		}

		for (int i = 0; i < splitPercentages.size(); i += 2) {
			Unit selected = (Unit) splitPercentages.get(i);
			double split = (double) splitPercentages.get(i + 1);

			double ration = split / (possibleUnits.size() - 1);
			for (Unit unit : possibleUnits) {
				if (unit == selected) {
					continue;
				}
				unit.setChance(unit.getChance() + ration);
			}
		}

	}

	private static double maxChancesCardExists(Unit unit) {
		double maxChance = -1;
		for (Player player : Player.ALLPLAYERS) {
			double chance = chancesCardExists(unit, player.getRefuted());
			if (chance > maxChance) {
				maxChance = chance;
			}
		}

		return maxChance;
	}

	// determines the chances the unit is a card based on how many refuted sets
	// it's found in.
	private static double chancesCardExists(Unit unit, List<Set> refuted) {
		double chances = 1;
		boolean ran = false;
		for (Set set : refuted) {
			if (set.contains(unit)) {
				chances *= (double) (set.numUnits() - 1) / (set.numUnits());
				ran = true;
			}
		}
		if (ran)
			return 1 - chances;
		return -1;
	}

}
