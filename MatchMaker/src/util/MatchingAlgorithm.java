package util;

import java.util.Arrays;

import data.Gender;
import data.Student;
import data.StudentList;

public class MatchingAlgorithm {

	// Student stu variable will be temporary variable for access
	/**
	 * will change the address of the memory location of list with a list with
	 * proper matches
	 * 
	 * @param list
	 * @return boolean whether or not matching algorithm ran
	 */
	public static boolean findMatches(StudentList list) {
		boolean noneComplete = true;
		for (int i = 0; i < list.getSize(); i++) {
			// all previous matches are eliminated
			list.get(i).setMatch(null);
			if (list.get(i).getAnswers().isComplete()) {
				noneComplete = false;
			}
		}
		if (list == null || list.getSize() == 0 || noneComplete) {
			System.out.println("Not running matching algorthm - nothing to match");
			return false;
		}

		// calculate matches for all grades
		findMatches(list, 9);
		findMatches(list, 10);
		findMatches(list, 11);
		findMatches(list, 12);

		return true;
	}

	// method does not need return
	// value, because it is changing the content of memory
	private static void findMatches(StudentList fullList, int grade) {

		StudentList boylist = new StudentList();
		StudentList girllist = new StudentList();
		for (int i = 0; i < fullList.getSize(); i++) {
			Student stu = fullList.get(i);

			if (stu.getGrade() == grade && stu.getAnswers().isComplete() && stu.getGender() == Gender.MALE) {
				boylist.add(fullList.get(i));
				stu.setMatch(null);
			} else if (stu.getGrade() == grade && stu.getAnswers().isComplete() && stu.getGender() == Gender.FEMALE) {
				girllist.add(fullList.get(i));
				stu.setMatch(null);
			}
		}
		if (boylist.getSize() == 0 && girllist.getSize() == 0)
			return;
		// NO NEED TO WORRY ABOUT GRADE OR COMPLETION AFTER THIS (IN LIST)

		// deltaScore = min score for a "good" match
		// use algorithm to calculate
		double deltaScore = 0;

		int numBoys = 0, numGirls = 0;
		numBoys = boylist.getSize();
		numGirls = girllist.getSize();
		Gender base = (numBoys <= numGirls) ? Gender.MALE : Gender.FEMALE;

		StudentList baselist = (base == Gender.MALE) ? boylist : girllist;
		StudentList otherGenderList = (base == Gender.MALE) ? girllist : boylist;
		if (baselist.getSize() <= 0 || otherGenderList.getSize() <= 0) {
			System.out.println("Not running algorithm for " + grade + "th grade because "
					+ "one gender group has no matches - matching everyone with celebrities.");

			matchElseWithCeleb(boylist, girllist);
			return;
		}
		if (baselist.getSize() < 5 || otherGenderList.getSize() < 5) {
			int less = Math.min(baselist.getSize(), otherGenderList.getSize());
			deltaScore = getDeltaScore(baselist, otherGenderList, less);
		}

		else {
			deltaScore = getDeltaScore(baselist, otherGenderList, 5);
		}

		System.out.println("Running algorithm for " + grade + "th grade");
		System.out.println("Delta Score: " + deltaScore);

		for (int i = 0; i < baselist.getSize(); i++) {
			System.out.println("Comparing: ");
			System.out.println(baselist.get(i).toSimpleString());
			for (int j = 0; j < otherGenderList.getSize(); j++) {
				System.out.println(otherGenderList.get(j).toSimpleString());
				System.out.println("Score: " + Match.getScore(baselist.get(i), otherGenderList.get(j)));
			}
		}

		boolean run = true;

		updateNumGoodMatchesInList(baselist, otherGenderList, deltaScore);
		do {

			int leastNumGoodMatches = 100;
			for (int i = 0; i < baselist.getSize(); i++) {
				Student stu = baselist.get(i);
				if (stu.getMatch() != null) {
					continue;
				}
				if (stu.numGoodMatches < leastNumGoodMatches) {
					leastNumGoodMatches = stu.numGoodMatches;
				}
			}
			for (int i = 0; i < baselist.getSize(); i++) {
				Student stu = baselist.get(i);
				if (stu.getMatch() != null) {
					continue;
				}
				if (stu.numGoodMatches == leastNumGoodMatches) {
					// stu gets its first pick
					Student mat = getBestMatch(stu, otherGenderList);
					Match.matchStudents(stu, mat);
					break;
				}
			}
			updateNumGoodMatchesInList(baselist, otherGenderList, deltaScore);

			// determine whether to run again or not
			run = false;
			for (int i = 0; i < baselist.getSize(); i++) {
				if (baselist.get(i).numGoodMatches > 0) {
					run = true;
				}
			}

		} while (run);

		matchElseWithCeleb(boylist, girllist);

	}

	private static void matchElseWithCeleb(StudentList boylist, StudentList girllist) {
		for (int i = 0; i < boylist.getSize(); i++) {
			Student stu = boylist.get(i);
			if (stu.getMatch() != null) {
				continue;
			}
			Match.matchStudents(stu, getRandomFemaleCelebrity());
		}
		for (int i = 0; i < girllist.getSize(); i++) {
			Student stu = girllist.get(i);
			if (stu.getMatch() != null) {
				continue;
			}
			Match.matchStudents(stu, getRandomMaleCelebrity());
		}
	}

	private static void updateNumGoodMatchesInList(StudentList base1, StudentList otherGenderList1, double deltaScore) {
		StudentList base = new StudentList();

		for (int i = 0; i < base1.getSize(); i++) {
			if (base1.get(i).getMatch() != null) {
				base1.get(i).numGoodMatches = -1;
				continue;
			}
			base.add(base1.get(i));
		}

		StudentList otherGenderList = new StudentList();

		for (int i = 0; i < otherGenderList1.getSize(); i++) {
			if (otherGenderList1.get(i).getMatch() != null) {
				continue;
			}
			otherGenderList.add(otherGenderList1.get(i));
		}

		for (int i = 0; i < base.getSize(); i++) {
			Student main = base.get(i);
			main.numGoodMatches = -1;
			if (main.getMatch() != null) {
				continue;
			}
			main.numGoodMatches = 0;
			for (int j = 0; j < otherGenderList.getSize(); j++) {
				Student other = otherGenderList.get(j);
				if (other.getMatch() != null) {
					continue;
				}
				if (Match.getScore(main, other) >= deltaScore) {
					main.numGoodMatches++;
				}
			}
		}
	}

	private static Student getBestMatch(Student stu, StudentList otherGenderList) {
		StudentList usable = new StudentList();

		for (int i = 0; i < otherGenderList.getSize(); i++) {
			if (otherGenderList.get(i).getMatch() != null) {
				continue;
			}
			usable.add(otherGenderList.get(i));
		}
		double bestScore = 0;
		for (int i = 0; i < usable.getSize(); i++) {
			double score = Match.getScore(stu, usable.get(i));
			if (score > bestScore) {
				bestScore = score;
			}
		}

		for (int i = 0; i < usable.getSize(); i++) {
			double score = Match.getScore(stu, usable.get(i));
			if (score == bestScore)
				return usable.get(i);

		}

		return null;
	}

	/**
	 * Will return the match value of the place th best match of the student stu
	 * in the studentlist list <br>
	 * ex. <br>
	 * place = 1 means it will return the score of the best match<br>
	 * place = 2 means it will return the score of the second best match<br>
	 * place = 11 means it will return the score of the 11th best match<br>
	 * etc
	 */
	private static double getBestMatchScore(Student stu, StudentList otherGenderList, int place) {

		StudentList usable = new StudentList();

		for (int i = 0; i < otherGenderList.getSize(); i++) {
			if (otherGenderList.get(i).getMatch() != null) {
				continue;
			}
			usable.add(otherGenderList.get(i));
		}

		double[] scores = new double[usable.getSize()];

		for (int i = 0; i < usable.getSize(); i++) {

			scores[i] = Match.getScore(stu, usable.get(i));
		}
		Arrays.sort(scores);

		return scores[scores.length - place];
	}

	// deltaScore = min score for a "good" match
	private static double getDeltaScore(StudentList base, StudentList otherGenderList, int place) {
		// taking average of all place th best match scores
		double total = 0;
		for (int i = 0; i < base.getSize(); i++) {
			Student stu = base.get(i);
			total += getBestMatchScore(stu, otherGenderList, place);
		}

		return total / base.getSize();
	}

	private static Student getRandomMaleCelebrity() {
		int r;
		do {
			r = (int) (Math.random() * 3);
		} while (r < 0 || r > 2);
		return Student.MALECELEBRITIES[r];
	}

	private static Student getRandomFemaleCelebrity() {
		int r;
		do {
			r = (int) (Math.random() * 3);
		} while (r < 0 || r > 2);
		return Student.FEMALECELEBRITIES[r];
	}

}
