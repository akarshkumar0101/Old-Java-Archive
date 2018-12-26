package data;

public enum Gender {
	MALE, FEMALE;
	public static Gender getOpposite(Gender inp) {
		if (inp == MALE)
			return FEMALE;
		return MALE;
	}
	public Gender getOpposite(){
		return getOpposite(this);
	}
}
