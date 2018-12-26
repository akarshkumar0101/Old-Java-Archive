package data;

import java.util.Date;
import java.util.List;

public class DownTownCheckout extends Checkout {

	protected DownTownCheckout(String location, Date estimatedCheckinDate, List<Student> students) {
		super(location, estimatedCheckinDate, students);
	}

}
