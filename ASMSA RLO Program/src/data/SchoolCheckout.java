package data;

import java.util.Date;
import java.util.List;

public class SchoolCheckout extends Checkout {

	protected SchoolCheckout(String location, Date estimatedCheckinDate, List<Student> students) {
		super(location, estimatedCheckinDate, students);
	}

}
