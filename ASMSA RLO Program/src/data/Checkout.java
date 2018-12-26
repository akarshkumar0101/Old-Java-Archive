package data;

import java.util.Date;
import java.util.List;

public abstract class Checkout {

	private final String location;

	private final Date checkoutDate;
	private final Date estimatedCheckinDate;
	private Date checkinDate;

	private final List<Student> students;

	protected Checkout(String location, Date estimatedCheckinDate, List<Student> students) {
		this.location = location;
		checkoutDate = new Date();
		this.estimatedCheckinDate = estimatedCheckinDate;

		this.students = students;
	}

	public String getLocation() {
		return location;
	}

	public Date getCheckoutDate() {
		return checkoutDate;
	}

	public Date getEstimatedCheckinDate() {
		return estimatedCheckinDate;
	}

	public Date getCheckinDate() {
		return checkinDate;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void checkin() {
		checkinDate = new Date();
	}

}
