package question;

import java.io.Serializable;

public class Answers implements Serializable{

	private static final long serialVersionUID = -397387508848192929L;
	
	private final Choice[] choices= new Choice[Question.QUESTIONS.length];
	
	public Answers(){
	}
	
	//copy const
	public Answers(Answers another){
		for(int i = 0; i <choices.length; i++){
			choices[i] = another.choices[i];
		}
	}
	
	public boolean isComplete(){
		for(Choice c: choices){
			if(c ==null) return false;
		}
		
		return true;
	}
	public Choice[] getChoices(){
		return choices;
	}
	
	
	
}
