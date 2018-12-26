
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Data {

	public static final List<Employee> employeeList;

	public static final List<Ticket> ticketHistory;

	static {
		employeeList = new ArrayList<>(10);
		ticketHistory = new ArrayList<>(1000);

		try {
			ObjectOutputStream oos = new ObjectOutputStream(System.out);

			oos.writeObject(ticketHistory);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
