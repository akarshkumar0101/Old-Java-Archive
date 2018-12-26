
import java.util.Date;

public class Employee {

	// save and load up current id issue
	private static long currentIDissue = 0;
	private final long ID;

	private String firstName;
	private String lastName;

	private Date dateOfBirth;

	private String titleOfJob;

	public Employee(String firstName, String lastName, Date dateOfBirth, String titleOfJob) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		ID = currentIDissue++;
		this.titleOfJob = titleOfJob;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getTitleOfJob() {
		return titleOfJob;
	}

	public void setTitleOfJob(String titleOfJob) {
		this.titleOfJob = titleOfJob;
	}

}
