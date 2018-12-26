package game;

public class Set {

	private final Unit[] units;

	public Set(Unit room, Unit weapon, Unit person) {
		units = new Unit[3];
		this.units[0] = room;
		this.units[1] = weapon;
		this.units[2] = person;
		
	}

	public Unit[] getUnitArray() {
		return units;
	}

	public Unit getRoom() {
		return units[0];
	}

	public Unit getWeapon() {
		return units[1];
	}

	public Unit getPerson() {
		return units[2];
	}

	public double getChances() {
		return units[0].getChance() * units[1].getChance() * units[2].getChance();
	}

	public int numUnits() {
		int num = 0;
		for (int i = 0; i < units.length; i++) {
			if (units[i] != null)
				num++;
		}
		return num;
	}

	public boolean contains(Unit unit) {
		for (int i = 0; i < units.length; i++) {
			if (units[i] == unit)
				return true;
		}
		return false;
	}
}
