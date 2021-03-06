
import java.util.Date;

public class Attendance {

	private Date signinDate;
	private Date signoutDate;

	public Attendance() {
		this(new Date(), null);
	}

	public Attendance(Date signinDate, Date signoutDate) {
		this.setSigninDate(signinDate);
		this.setSignoutDate(signoutDate);
	}

	public void signout() {
		setSignoutDate(new Date());
	}

	public Date getSigninDate() {
		return signinDate;
	}

	public Date getSignoutDate() {
		return signoutDate;
	}

	public void setSigninDate(Date signinDate) {
		this.signinDate = signinDate;
	}

	public void setSignoutDate(Date signoutDate) {
		this.signoutDate = signoutDate;
	}

	public boolean wasSignedInAt(Date date) {
		return date.after(signinDate) && date.before(signoutDate);
	}

}
