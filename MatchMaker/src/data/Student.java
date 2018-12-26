package data;

import java.io.File;
import java.io.Serializable;

import main.Program;
import question.Answers;

public class Student implements Serializable {

	private static final long serialVersionUID = -6509572764402270693L;
	
	public final File path;

	// id name grade gender advisor
	private final String ID;
	private final String name;
	private final int grade;
	private final Gender gender;
	private final String advisor;
	
	private Answers answers;

	private Student match;
	
	public static final Student[] MALECELEBRITIES = new Student[]{
		new Student("MC1", "Tom Cruise", -1, Gender.MALE, null),
		new Student("MC2", "Will Smith", -1, Gender.MALE, null),
		new Student("MC3", "Steph Curry", -1, Gender.MALE, null)
	};
	public static final Student[] FEMALECELEBRITIES = new Student[]{
		new Student("FC1", "Scarlett Johansson", -1, Gender.FEMALE, null),
		new Student("FC2", "Megan Fox", -1, Gender.FEMALE, null),
		new Student("FC3", "Beyoncé", -1, Gender.FEMALE, null)
	};
	public boolean isCelebrity(){
		
		for(Student c: MALECELEBRITIES){
			if(this ==c) return true;
		}
		for(Student c: FEMALECELEBRITIES){
			if(this ==c) return true;
		}
		
		return false;
	}
	
	/**
	 * TEMPORARY VARIABLE
	 * SIMPLY FOR CALCULATING MATCHES
	 * NO OTHER USE
	 * DO NOT USE FOR ANYTHING ELSE
	 */
	public int numGoodMatches = -1;
	
	public Student(String ID, String name, int grade, Gender gender,
			String advisor) {

		this.ID = ID;
		this.name = name;
		this.grade = grade;
		this.gender = gender;
		this.advisor = advisor;

		answers = new Answers();
		match = null;
		
		 path = new File(Program.mainDirectory.getAbsolutePath() +"/"+ID+".student");
	}

	// copy const unneeded
//	public Student(Student another) {
//		ID = another.ID;
//		name = another.name;
//		grade = another.grade;
//		gender = another.gender;
//		advisor = another.advisor;
//
//		answers = new Answers(another.answers);
//		match = another.match;
//	}

	public String getID() {
		return ID;
	}

	public String getName() {
		return name;
	}

	public int getGrade() {
		return grade;
	}

	public Gender getGender() {
		return gender;
	}

	public String getAdvisor() {
		return advisor;
	}

	public Answers getAnswers() {
		return answers;
	}

	public void setMatch(Student inp) {
		match = inp;
	}

	public Student getMatch() {
		return match;
	}

	@Override
	public String toString() {
		return ("Student: ID: " + ID + " Name: " + name + " Gender: " + gender
				+ " Grade: " + grade + "th Advisor: " + advisor
				+ "\n\t\tCompletion: "+answers.isComplete()+" Match: " + match.toSimpleString());
	}
	public String toSimpleString() {
		return ("Student: ID: " + ID + " Name: " + name + " Gender: " + gender
				+ " Grade: " + grade + "th Advisor: " + advisor
				+ "\n\t\tCompletion: "+answers.isComplete());
	}
	
	/**
	 * JUST LOOKS AT ID'S TO SEE IF THEY'RE THE SAME
	 * @param another
	 * @return
	 */
	public boolean equals(Student another){
		if(ID.equals(another.ID))return true;
		return false;
	}
	public String getDisplayString() {
		return (name + "<br>" + gender + ", " + grade
				+ "th grade, Advisor: " + advisor);
		//<html><div style=\"text-align: center;\">To begin answering your questions, scan your ID card below!"
		//+ "<html>");
	}

}
