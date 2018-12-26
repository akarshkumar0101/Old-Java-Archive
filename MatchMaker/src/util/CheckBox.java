package util;

import javax.swing.JRadioButton;

import question.Choice;

public class CheckBox extends JRadioButton{

	private static final long serialVersionUID = -208196246737317678L;
	
	private Choice choice;
	
	public CheckBox(Choice c){
		super();
		choice = c;
	}
	
	public Choice getChoice(){
		return choice;
	}
	
	
}
