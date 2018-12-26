package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import main.Application;
import main.Main;
import util.Sorting;
import util.Util;

public class Student implements Serializable, Comparable<Student> {

	private static final long serialVersionUID = -9109708571030807440L;

	public static final int FRESHMAN = 9, SOPHOMORE = 10, JUNIOR = 11, SENIOR = 12;

	public static final List<Student> ALLSTUDENTS;
	static {
		ALLSTUDENTS = new ArrayList<Student>();
	}

	private String firstName, lastName;
	private String ID;
	private Date dateOfBirth;

	private String email;
	private String phoneNumber;

	private int graduationYear;
	// needs updating
	private int currentGrade;

	public static final boolean MALE = false;
	public static final boolean FEMALE = true;
	private boolean gender;

	// SHP is study hour privileges, DP is driving privileges
	private boolean hasSHP = false;
	private boolean hasDP = false;

	// per week
	private int maxSHP = 0, maxDP = 0;
	// num used this week
	private int numSHP = 0, numDP = 0;

	private List<Student> transporationList;

	private List<Checkout> checkouts;

	public Student(String firstName, String lastName, String email, Date dateOfBirth) {
		transporationList = new ArrayList<>();
		checkouts = new ArrayList<>();

		currentGrade = determineHighSchoolGrade();

		ALLSTUDENTS.add(this);
	}

	public boolean canRideWith(Student other) {
		return transporationList.contains(other) && other.transporationList.contains(this);
	}

	public void resetPrivileges() {
		numSHP = maxSHP;
		numDP = maxDP;
	}

	public void checkout(Checkout checkout) {
		checkouts.add(checkout);
	}

	public void checkin() {
		checkouts.get(checkouts.size() - 1).checkin();
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getName() {
		return firstName + " " + lastName;
	}

	public String getID() {
		return ID;
	}

	public String getEmail() {
		return email;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public int getGraduationYear() {
		return graduationYear;
	}

	public boolean hasSHP() {
		return hasSHP;
	}

	public boolean hasDP() {
		return hasDP;
	}

	public int getMaxSHP() {
		return maxSHP;
	}

	public int getMaxDP() {
		return maxDP;
	}

	public int getNumSHP() {
		return numSHP;
	}

	public int getNumDP() {
		return numDP;
	}

	public int getCurrentGrade() {
		return currentGrade;
	}

	public int determineHighSchoolGrade() {
		Date today = Main.today;
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		if (month > 6) {
			year++;
		}
		int yearsTillGraduation = graduationYear - year;
		int grade = 12 - yearsTillGraduation;
		return grade;
	}

	// volunteer stuff temp

	// add email, phone number, address, etc.

	private String id;
	private int age;

	private String address;

	private String password;

	private List<Session> sessions = new ArrayList<Session>();

	public void setName(String name) {
		String[] names = name.split(" ");
		firstName = names[0];
		lastName = names[1];
	}

	public void setID(String id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public List<Session> getSessions() {
		return sessions;
	}

	public int getTotalVolunteeringTime() {
		int totalTime = 0;
		for (Session s : sessions) {
			totalTime += s.getTotalTime();
		}
		return totalTime;
	}

	public boolean isSignedIn() {
		if (sessions.size() == 0)
			return false;
		Session current = sessions.get(sessions.size() - 1);
		if (current.getSigninTime() != null && current.getSignoutTime() == null)
			return true;
		return false;
	}

	public void signin() {
		Session newsession = new Session();
		newsession.signin();
		sessions.add(newsession);

		if (Application.logFileStream != null) {
			Application.logFileStream.println(
					Util.formatName(this) + ": Signed In at " + Util.normalFormat.format(newsession.getSigninTime()));
		}
	}

	public void signout() {
		if (sessions.get(sessions.size() - 1).isComplete())
			return;
		sessions.get(sessions.size() - 1).signout();

		if (Application.logFileStream != null) {
			Application.logFileStream.println(Util.formatName(this) + ": Signed Out at "
					+ Util.normalFormat.format(sessions.get(sessions.size() - 1).getSignoutTime()));
		}

		if (sessions.get(sessions.size() - 1).getTotalTime() < 1) {
			sessions.remove(sessions.size() - 1);
		}
		if (Application.logFileStream != null) {
			Application.logFileStream
					.println("* Last signout did was not recorded because the session was under a minutein length *");
		}

	}

	public boolean wasVolBetween(Date a, Date b) {
		for (Session sess : sessions) {
			if (Util.dateIsBetween(sess.getSigninTime(), a, b, true)
					|| Util.dateIsBetween(sess.getSignoutTime(), a, b, true)
					|| (sess.getSignoutTime().getTime() > b.getTime() && sess.getSigninTime().getTime() < a.getTime()))
				return true;
		}
		return false;
	}

	public boolean wasVolOnDay(Date date) {
		String dayString = Util.dayFormat.format(date);
		for (Session sess : sessions) {
			Date signin = sess.getSigninTime(), signout = sess.getSignoutTime();
			if (Util.dayFormat.format(signin).equals(dayString) || Util.dayFormat.format(signout).equals(dayString))
				return true;
		}
		return false;
	}

	public void sortSessions() {
		long[] intimes = new long[sessions.size()];
		for (int i = 0; i < sessions.size(); i++) {
			intimes[i] = sessions.get(i).getSigninTime().getTime();
		}

		long[] newintimes = Sorting.mergeSort(intimes, Sorting.INCREASING);

		ArrayList<Session> newTimeChart = new ArrayList<Session>();
		for (long newintime : newintimes) {
			for (int j = 0; j < sessions.size(); j++) {
				if (sessions.get(j).getSigninTime().getTime() == newintime) {
					newTimeChart.add(sessions.get(j));
					sessions.remove(j);
					break;
				}
			}
		}
		sessions = newTimeChart;

	}

	public Date lastAffiliation() {
		if (sessions.isEmpty())
			return null;
		Session last = getLastSession();
		if (last.isComplete())
			return last.getSignoutTime();
		else
			return last.getSigninTime();
	}

	public Session getLastSession() {
		if (sessions.isEmpty())
			return null;
		return sessions.get(sessions.size() - 1);
	}

	@Override
	public int compareTo(Student stu) {
		return lastName.compareTo(stu.lastName);
	}

	@Override
	public String toString() {
		return getName() + " " + id + " " + sessions.size();
	}

}
