package data;

import java.util.Date;
import java.util.List;

//Checkout for weekend
public class LeaveCheckout extends Checkout {

	protected LeaveCheckout(String location, Date estimatedCheckinDate, List<Student> students) {
		super(location, estimatedCheckinDate, students);
	}

}
