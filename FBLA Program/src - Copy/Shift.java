
import java.util.ArrayList;
import java.util.List;

public class Shift {

	private final List<Attendance> shifts;

	public Shift() {

		shifts = new ArrayList<>();
	}

	// when using attendance and dates for shifts, only use time part of date,
	// not actual day of year, etc.
	public List<Attendance> getShifts() {
		return shifts;
	}

	public void addShift(Attendance shift) {
		shifts.add(shift);
	}

}
