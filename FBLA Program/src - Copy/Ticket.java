
public class Ticket {

	private final Attendance attendance;

	private final int numberOfAdults;
	private final int numberOfChildren;

	private int totalCost;

	public Ticket(int numberOfAdults, int numberOfChildren) {
		this(numberOfAdults, numberOfChildren, new Attendance());
	}

	public Ticket(int numberOfAdults, int numberOfChildren, Attendance attendance) {
		this.attendance = attendance;
		this.numberOfAdults = numberOfAdults;
		this.numberOfChildren = numberOfChildren;

		totalCost = -1;
	}

	public Attendance getAttendance() {
		return attendance;
	}

	public int getNumberOfAdults() {
		return numberOfAdults;
	}

	public int getNumberOfChildren() {
		return numberOfChildren;
	}

	public void calculateTotalCost() {
		long timeBetween = attendance.getSignoutDate().getTime() - attendance.getSigninDate().getTime();
		final int MILLI_TO_HOUR = 1000 * 60 * 60;
		int totalHours = (int) ((timeBetween) / MILLI_TO_HOUR);
		int totalCost = Cost.getChildCostPerHour() * numberOfChildren * totalHours
				+ Cost.getAdultCostPerHour() * numberOfAdults * totalHours;
		this.totalCost = totalCost;
	}

	public int getTotalCost() {
		if (totalCost == -1) {
			calculateTotalCost();
		}
		return totalCost;
	}

}
