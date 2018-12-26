package game;

import java.util.ArrayList;
import java.util.List;

/**
 * Either a person, weapon, or room. A "unit" or part of the murder.
 * 
 * @author Akarsh
 *
 */
public class Unit {

	public static final List<Unit> ALLUNITS = new ArrayList<>(100);

	public static final List<Unit> ROOMS = new ArrayList<>(20);
	public static final List<Unit> WEAPONS = new ArrayList<>(20);
	public static final List<Unit> PEOPLE = new ArrayList<>(60);

	private final Type type;
	private final int id;
	private final String name;

	// chances of being part of the murder
	private double chance;

	public Unit(Type type, int id, String name) {
		this.type = type;
		this.id = id;
		this.name = name;

		chance = .05;

		ALLUNITS.add(this);
		if (type == Type.ROOM) {
			ROOMS.add(this);
		} else if (type == Type.WEAPON) {
			WEAPONS.add(this);
		} else if (type == Type.PERSON) {
			PEOPLE.add(this);
		}
	}

	public Type getType() {
		return type;
	}

	public int getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public double getChance() {
		return chance;
	}

	public void setChance(double chance) {
		this.chance = chance;
	}

	public static Unit getUnit(int unitType, int id) {
		for (Unit unit : ALLUNITS) {
			if (unit.equals(unitType, id))
				return unit;
		}
		throw new RuntimeException();
	}

	public static Unit getUnit(Type type, int id) {
		for (Unit unit : ALLUNITS) {
			if (unit.equals(type, id))
				return unit;
		}
		throw new RuntimeException();
	}

	@Override
	public boolean equals(Object another) {
		Unit unit = (Unit) another;
		return type == unit.type && id == unit.id;
	}

	public boolean equals(int type, int id) {
		return equals(Type.values()[2 - type], id);
	}

	public boolean equals(Type type, int id) {
		return type == this.type && id == this.id;
	}
}
