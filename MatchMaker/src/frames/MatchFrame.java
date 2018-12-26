package frames;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import data.Student;
import data.StudentList;
import main.Program;
import util.DataController;
import util.MatchingAlgorithm;

public class MatchFrame extends Account{
	
	private static final Color matchColor = new Color(0, 0, 150);
	
	
	private final JLabel matchedWith = new JLabel();
	
	private final JPanel fullMatchPanel = new JPanel();
	private final JLabel actualMatch = new JLabel();
	
	private final JLabel celebPic = new JLabel();
	
	private final JLabel sorryNoMatchInSchool = new JLabel("<html><div style=\"text-align: center;\">(Sorry, we couldn't find a good match inside the school)<html>");
	
	private final JPanel matchSorry = new JPanel();
	
	private final JButton doneButton = new JButton("Done");
	
	private final ActionListener doneButtonListener = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			logout();
		}
	};
	
	public MatchFrame(){
		doneButton.addActionListener(doneButtonListener);
		
		matchedWith.setFont(questionFont);
		matchedWith.setHorizontalAlignment(SwingConstants.CENTER);
		
		actualMatch.setFont(buttonFont);
		actualMatch.setHorizontalAlignment(SwingConstants.CENTER);
		actualMatch.setForeground(matchColor);
		actualMatch.setVerticalAlignment(SwingConstants.TOP);
		
		sorryNoMatchInSchool.setFont(textFont);
		sorryNoMatchInSchool.setVerticalAlignment(SwingConstants.TOP);
		celebPic.setHorizontalAlignment(SwingConstants.CENTER);
		celebPic.setVerticalAlignment(SwingConstants.CENTER);
		
		matchSorry.setLayout(new GridLayout(2,1));
		matchSorry.add(actualMatch);
		matchSorry.add(sorryNoMatchInSchool);
		
		doneButton.setFont(buttonFont);
		doneButton.setBackground(buttonColor);
		
//		matchedWith.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
//		match.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		
		
		
		//LOAD MATCH LIST OR DO SOMETHING ELSE
		
		//if (Program.matchFile.exists()) {
		if (Program.matchFile.exists()) {
			System.out.println("Previous matches were already made - match algorithm will not run");
			
			//StudentList.matchList = DataController.loadStudentList(Program.matchFile);
			//StudentList.matchList = DataController.loadStudentListTest(Program.matchFilesTest);
			
		} else {
			System.out
					.println("Previous matches were not found - will run matching algorithm and will save only if matches are made");

			// RUN ALGORITHM TO FIND MATCHES
			
			//no need for these because matches will be saved in main file(already loaded)
			//StudentList.matchList = DataController.loadStudentList(Program.mainFile);
			//StudentList.matchList = DataController.loadStudentListTest(Program.mainFilesTest);
			
			System.out.println("Finding matches... Please wait...");
			boolean matched = MatchingAlgorithm.findMatches(StudentList.mainList);
			System.out.println("Done!");
			
			int [] gcom = new int[8];
			for(int i=0; i <gcom.length; i++){
				gcom[i] = 0;
			}
			
			for(int i=0; i <StudentList.mainList.getSize(); i ++){
				Student stu = StudentList.mainList.get(i);
				gcom[(stu.getGrade()-9)*2] ++;
				if(stu.getMatch() != null){
					gcom[(stu.getGrade()-9)*2+1]++;
					
					
					
				}
			}
			
			if(matched){
				//DataController.saveStudentList(StudentList.matchList, Program.matchFile);
				//DataController.saveStudentListTest(StudentList.matchList, Program.matchFilesTest);
				
				//SAVE MATCH FILE IN ORDER TO NOT RUN MATCHING ALGORITHM AGAIN
				try {
					Program.matchFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				DataController.saveStudentList(StudentList.mainList);
				
			}
		}
		
		
	}
	
	@Override
	public void login(Student stu){
		super.login(stu);
		
		setLabelContent();
		
		frame.setTitle("Here is your match - "+current.getName());
		
		frame.getContentPane().setLayout(new GridLayout(4, 1));
		frame.getContentPane().add(titlePanel);
		frame.getContentPane().add(matchedWith);
		frame.getContentPane().add(fullMatchPanel);
		frame.getContentPane().add(doneButton);
		
		frame.setVisible(true);
		
		Program.logWriter.append("login - Matches: "+current.getID()+" at "+dateFormat.format(new Date()));
		Program.logWriter.println();
	}
	public void setLabelContent(){
		boolean hasMatch = !(current.getMatch() == null);
		if (hasMatch) {
			boolean isMatchedWithCeleb = current.getMatch().isCelebrity();
			
			matchedWith.setText("<html><div style=\"text-align: center;\">You were matched with: <html>");
			
			actualMatch.setText("<html><div style=\"text-align: center;\">"+ (isMatchedWithCeleb?current.getMatch().getName():current.getMatch().getDisplayString()) + "<html>");
			
			if(isMatchedWithCeleb){
				celebPic.setIcon(getCelebrityPic(current.getMatch()));
				
				fullMatchPanel.setLayout(new GridLayout(1,4));
				fullMatchPanel.add(new JLabel());
				fullMatchPanel.add(celebPic);
				fullMatchPanel.add(matchSorry);
				fullMatchPanel.add(new JLabel());
				
				actualMatch.setHorizontalAlignment(SwingConstants.CENTER);
				actualMatch.setVerticalAlignment(SwingConstants.CENTER);
				sorryNoMatchInSchool.setHorizontalAlignment(SwingConstants.CENTER);
				sorryNoMatchInSchool.setVerticalAlignment(SwingConstants.CENTER);
			}
			else{
				fullMatchPanel.setLayout(new GridLayout(1,1));
				fullMatchPanel.add(actualMatch);
				
				actualMatch.setHorizontalAlignment(SwingConstants.CENTER);
				actualMatch.setVerticalAlignment(SwingConstants.CENTER);
			}
			
		}
		else{
			matchedWith.setText("<html><div style=\"text-align: center;\">Sorry there is no match for you because you did not answer the questions before the deadline!<html>");

			actualMatch.setText("<html><div style=\"text-align: center;\">"+ "<html>");
			
			fullMatchPanel.setLayout(new GridLayout(1,1));
			fullMatchPanel.add(new JLabel());
		}
	}
	private static ImageIcon getCelebrityPic(Student celeb){
		boolean realFound = false;
		for(Student c: Student.MALECELEBRITIES){
			if(celeb==c){
				realFound = true;
			}
		}
		for(Student c: Student.FEMALECELEBRITIES){
			if(celeb==c){
				realFound = true;
			}
		}
		if(!realFound) return null;
		
		ImageIcon pic = new ImageIcon(Program.class.getResource("/images/celebs/"+celeb.getName()+".png"));
		Image i = pic.getImage();
		i = i.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		pic = new ImageIcon(i);
		
		return pic;
	}
	
	@Override
	public void logout(){
		//no need to save again, matches already made 
		//and ready after algorithm was complete
		//DataController.saveStudentList(StudentList.matchList, Program.matchFile);
		
		Program.logWriter.append("logout - Matches: "+current.getID()+" at "+dateFormat.format(new Date()));
		Program.logWriter.println();
		
		fullMatchPanel.removeAll();
		super.logout();
	}
	
	
	
	
}
//720310000
