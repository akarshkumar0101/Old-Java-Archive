package frames;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import main.Program;
import question.Choice;
import question.Question;
import util.CheckBox;
import util.DataController;
import data.Student;

public class QuestionFrame extends Account{ 
	
	//these cannot be static because they will contain buttons,
	//non-static content depending on the instance
	private final JLabel questionLabel = new JLabel();
	private final JPanel answerPanel = new JPanel();
	private final JPanel middleAnswerPanel = new JPanel();
	private final JPanel buttonPanel = new JPanel();
	
	//buttons
	private final JButton backButton = new JButton("Back");
	private final JButton middleButton = new JButton();
	private final JButton nextButton = new JButton("Next");
	
	//question stuff
	private int questionIndex;
	
	
	private final ActionListener checkBoxListener = new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			CheckBox temp = (CheckBox) e.getSource();
			current.getAnswers().getChoices()[questionIndex] = temp.getChoice();
			if(current.getAnswers().isComplete()) middleButton.setText("Done");
			DataController.saveStudent(current);
		}
		
	};
	
	private final ActionListener backButtonListener = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			questionIndex--;
			setupForQuestion();
		}
	};
	private final ActionListener nextButtonListener = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			questionIndex++;
			setupForQuestion();
		}
	};
	private final ActionListener middleButtonListener = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			logout();
		}
	};
	
	public QuestionFrame() {
		// student info
		

		// question
		questionLabel.setFont(questionFont);
		questionLabel.setHorizontalAlignment(SwingConstants.CENTER);

		// buttons
		backButton.addActionListener(backButtonListener);
		backButton.setFont(buttonFont);
		backButton.setBackground(buttonColor);
		nextButton.addActionListener(nextButtonListener);
		nextButton.setFont(buttonFont);
		nextButton.setBackground(buttonColor);
		middleButton.addActionListener(middleButtonListener);
		middleButton.setFont(buttonFont);
		middleButton.setBackground(buttonColor);

		answerPanel.setLayout(new GridLayout(1, 3));
		answerPanel.add(new JPanel());
		answerPanel.add(middleAnswerPanel);
		answerPanel.add(new JPanel());

		buttonPanel.setLayout(new GridLayout(1, 3));
		buttonPanel.add(backButton);
		buttonPanel.add(middleButton);
		buttonPanel.add(nextButton);

		// temp border
		// studentInfoLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,
		// 2));
		// questionLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,
		// 2));
		// answerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,
		// 2));
		// buttonPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,
		// 2));
	}
	
	@Override
	public void login(Student stu){ //////INITIALIZES TEMPORARY MOVING STUFF OR STUFF FOR EACH USER
		super.login(stu);
		
		//reset stuff to default
		questionIndex = 0; 
		
		frame.setTitle("Answer the Questions to Find Your Match! - " + current.getName());
		
		frame.getContentPane().setLayout(new GridLayout(4, 1));
		frame.getContentPane().add(titlePanel);
		frame.getContentPane().add(questionLabel);
		frame.getContentPane().add(answerPanel);
		frame.getContentPane().add(buttonPanel);
		
		setupForQuestion();
		
		frame.setVisible(true);
		
		Program.logWriter.append("login - Questions: "+current.getID()+" at "+dateFormat.format(new Date()));
		Program.logWriter.println();
	}
	@Override
	public void logout(){
		//DataController.saveStudentList(StudentList.mainList, Program.mainFile);
		
		Program.logWriter.append("logout - Questions: "+current.getID()+" at "+dateFormat.format(new Date()));
		Program.logWriter.println();
		
		DataController.saveStudent(current);
		super.logout();
	}
	
	private void setupForQuestion(){
		//question setup
		questionLabel.setText("<html><div style=\"text-align: center;\">"+ Question.QUESTIONS[questionIndex].getQuestion() +"<html>");
		
		
		//button setup
		if(questionIndex == 0){
			backButton.setEnabled(false);
			nextButton.setEnabled(true);
		}
		else if(questionIndex == Question.QUESTIONS.length-1){
			backButton.setEnabled(true);
			nextButton.setEnabled(false);
		}
		else{
			backButton.setEnabled(true);
			nextButton.setEnabled(true);
		}
		
		if(current.getAnswers().isComplete()) middleButton.setText("Done");
		else middleButton.setText("Finish Later");
		
		//answer setup
		middleAnswerPanel.removeAll();
		CheckBox [] choices = getCheckBoxes(Question.QUESTIONS[questionIndex]);
		ButtonGroup group = new ButtonGroup();
		middleAnswerPanel.setLayout(new GridLayout(choices.length, 1));
		for(CheckBox c:choices){
			group.add(c);
			if(current.getAnswers().getChoices()[questionIndex] == c.getChoice()) c.doClick();
			c.addActionListener(checkBoxListener);
			middleAnswerPanel.add(c);
			c.setFont(textFont);
			c.setHorizontalAlignment(SwingConstants.LEFT);
		}
		
		frame.repaint();
	}
	private static CheckBox[] getCheckBoxes(Question q){
		Choice[] choices = q.getAvailableResponses();
		CheckBox[] result = new CheckBox[choices.length];
		for(int i =0 ; i <choices.length; i ++){
			result[i] = new CheckBox(choices[i]);
			result[i].setText(q.getDisplayAnswers()[i]);
		}
		return result;
	}

	
}
