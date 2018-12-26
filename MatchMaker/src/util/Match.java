package util;

import question.Choice;
import question.Question;
import data.Student;


public abstract class Match {
	
	public static void matchStudents(Student a, Student b) {
		a.setMatch(b);
		b.setMatch(a);
	}
	
	public static double getScore(Student a, Student b) {
		Choice[] achoice = a.getAnswers().getChoices();
		Choice[] bchoice = b.getAnswers().getChoices();
		double score = 0;
		for(int i=0; i <Question.QUESTIONS.length; i++){
			if(achoice[i] == bchoice[i]){
				score += Question.QUESTIONS[i].getWeight();
			}
		}
		return score/Question.TOTALWEIGHT;
	}
}
