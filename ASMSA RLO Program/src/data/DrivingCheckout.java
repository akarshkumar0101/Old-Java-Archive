package data;

import java.util.Date;
import java.util.List;

//Car checkout
public class DrivingCheckout extends Checkout {

	private final Student driver;

	public DrivingCheckout(String location, Date estimatedCheckinDate, List<Student> students, Student driver) {
		super(location, estimatedCheckinDate, students);
		this.driver = driver;
	}

	public Student getDriver() {
		return driver;
	}

}
