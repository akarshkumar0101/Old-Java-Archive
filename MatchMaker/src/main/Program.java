package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import data.Student;
import data.StudentList;
import frames.Account;
import frames.MatchFrame;
import frames.QuestionFrame;
import question.Question;
import util.DataController;

public abstract class Program {
	
	private static boolean testingProgram = false;
	
	public static final File mainDirectory = new File(
			System.getenv("appdata")+ "/MatchMaker/data/");
	
	public static final File matchFile = new File(System.getenv("appdata")+ "/MatchMaker/completematch.match");
	
	public static final File logFile = new File(System.getenv("appdata")+"/MatchMaker/log.txt");
	public static PrintWriter logWriter;
	
	
	private static final String passwordToClose = "kumarfhs123";
	
	private static final Color backgroundColor = new Color(238,238,238);
	
	private static final JTextField IDField = new JTextField();
	
	private static JPanel titlePanel;
	private static JPanel byAkarshPanel;
	private static JLabel itisLabel;
	
	private static Font titleFont = new Font("Segoe Script Bold", Font.ITALIC ,110);
	private static Font textFont = new Font("MV Boli", Font.BOLD, 25);
	private static Font standoutTextFont = new Font("MV Boli", Font.BOLD, 40);
	private static Font IDfont= new Font("MV Boli", Font.BOLD, 35);
	
	private static Account account;
	
	private static Startup startupType;
	
	private static final DocumentListener fieldListener = new DocumentListener(){

		@Override public void changedUpdate(DocumentEvent e) {}

		@SuppressWarnings("deprecation")
		@Override
		public void insertUpdate(DocumentEvent e) {
			tryLogin(IDField.getText());
			if(IDField.getText().length() >10){
				clearIDField();
			}
			IDFieldClearer.stop();
			IDFieldClearer.suspend();
			IDFieldClearer = new Thread(clearerRunnable);
			IDFieldClearer.start();
			
			System.gc();
			
		}

		@Override public void removeUpdate(DocumentEvent e) {}
		
	};
	
	private static final Runnable clearerRunnable = new Runnable(){
		@Override
		public void run() {
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Program.clearIDField();
		}
	};
	
	private static Thread IDFieldClearer = new Thread(clearerRunnable);
	
	private static final JFrame startscreen = new JFrame("Match Maker Program 1.0!");

	private static final WindowListener windowListener = new WindowListener() {

		@Override public void windowActivated(WindowEvent e) {}

		@Override public void windowClosed(WindowEvent e) {}

		@Override
		public void windowClosing(WindowEvent we) {
			
			String pass = "";
			if(!testingProgram){
				pass = JOptionPane.showInputDialog(startscreen,
						"Please enter the password to close the program.",
						"Close Program", JOptionPane.INFORMATION_MESSAGE);
				if (pass == null) return;
			}
			else{
				pass = passwordToClose;
			}
			if (pass.equals(passwordToClose)) {
				logWriter.close();
				System.exit(0);
			}
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
			startscreen.setExtendedState(JFrame.MAXIMIZED_BOTH);
		}

		@Override public void windowDeiconified(WindowEvent e) {}

		@Override public void windowIconified(WindowEvent e) {}

		@Override public void windowOpened(WindowEvent e) {}

	};
	
	
	static {
		// load MAIN studentlist file
		
		mainDirectory.mkdirs();
		
		
		
		int numFiles = mainDirectory.listFiles().length;
		
		//if files inside exist
		if (numFiles >1) {
			
			//StudentList.mainList = DataController.loadStudentList(mainFile);
			StudentList.mainList = DataController.loadStudentList(mainDirectory);

		} else {
			System.out
					.println("Previous data files were not found - will create new "
							+ "data files at the end of the program");
			
			StudentList.mainList = DataController.loadStudentList();
			
			if(testingProgram){
				loadRandomAnswers();/////////////////
				DataController.saveStudentList(StudentList.mainList);/////////////////
			}
		}
		try {
			logFile.createNewFile();
			logWriter = new PrintWriter(new FileWriter(Program.logFile,true));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// get picture and title
		loadPanels();

	}
	private static void loadRandomAnswers(){
		for(int i=0; i <StudentList.mainList.getSize(); i++){
			Student stu = StudentList.mainList.get(i);
			for(int x=0; x<Question.QUESTIONS.length; x++){
				Question q = Question.QUESTIONS[x];
				
				int num = q.getAvailableResponses().length;
				
				int random = (int) (Math.random()*num);
				
				stu.getAnswers().getChoices()[x] = q.getAvailableResponses()[random];
				
			}
			
		}
	}
	private static void loadPanels(){
		titlePanel = new JPanel();
		JLabel title = new JLabel("Match Maker");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(titleFont);
		title.setForeground(Color.RED);
		titlePanel.add(title);
		
		byAkarshPanel = new JPanel();
		byAkarshPanel.setLayout(new GridLayout(1,3));
		JLabel byAkarsh = new JLabel("Made by Akarsh Kumar");
		byAkarsh.setHorizontalAlignment(SwingConstants.CENTER);
		byAkarsh.setVerticalAlignment(SwingConstants.TOP);
		byAkarsh.setFont(textFont);
		byAkarsh.setHorizontalAlignment(SwingConstants.CENTER);
		
		ImageIcon heartpic = new ImageIcon(Program.class.getResource("/images/heart.png"));
		Image i = heartpic.getImage();
		i = i.getScaledInstance(130, 130, Image.SCALE_SMOOTH);
		heartpic = new ImageIcon(i);
		
		JLabel picLabel1 = new JLabel(heartpic);
		picLabel1.setVerticalAlignment(SwingConstants.TOP);
		JLabel picLabel2 = new JLabel(heartpic);
		picLabel2.setVerticalAlignment(SwingConstants.TOP);
		
		byAkarshPanel.add(picLabel1);
		byAkarshPanel.add(byAkarsh);
		byAkarshPanel.add(picLabel2);
		
		
		itisLabel = new JLabel(
				"<html><div style=\"text-align: center;\">Match Maker is a entertaining program that "
						+ "lets FHS students answer questions about themselves and, based "
						+ "on the results, it matches couples on Valentine's Day!<html>");
		itisLabel.setFont(textFont);
		itisLabel.setVerticalAlignment(SwingConstants.CENTER);
		itisLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
	}
        
	// LOCK CONSTRUCTOR
	private Program(){}
	
	public static void start(Startup start) {
		startupType = start;
		
		startscreen.getContentPane().add(titlePanel);
		startscreen.getContentPane().add(byAkarshPanel);
		startscreen.getContentPane().add(itisLabel);
		
		if(startupType == Startup.NORMAL_START) startNormal();
		if(startupType == Startup.MATCH_START) startMatch();
		
		startscreen.getContentPane().add(IDField);
		
		startscreen.setExtendedState(JFrame.MAXIMIZED_BOTH);
		startscreen.setResizable(false);
		
		startscreen.setUndecorated(true);
		
		startscreen.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		startscreen.addWindowListener(windowListener);
		startscreen.getContentPane().setBackground(backgroundColor);

		startscreen.setVisible(true);
		startscreen.requestFocus();
		
		IDFieldClearer.start();
		IDField.requestFocus();
		
	}

	private static void startNormal() {
		// SETS UP JFRAME
		account = new QuestionFrame();

		JLabel timeline = new JLabel(
				"<html><div style=\"text-align: center;\">This program will run from January 20 to Febuary 12. "
						+ "Anyone can stop by and answer questions during this time. "
						+ "On Febuary 12, this program will close and matches will "
						+ "be made and posted here.<html>");
		timeline.setVerticalAlignment(SwingConstants.CENTER);
		timeline.setFont(textFont);
		timeline.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel toBegin = new JLabel(
				"<html><div style=\"text-align: center;\">To begin answering your questions, <br>type in your student ID or scan your ID card below!"
						+ "<html>");
		toBegin.setHorizontalAlignment(SwingConstants.CENTER);
		toBegin.setVerticalAlignment(SwingConstants.CENTER);
		toBegin.setFont(standoutTextFont);
		toBegin.setForeground(new Color(150, 0, 0));
		
		IDField.getDocument().addDocumentListener(fieldListener);
		IDField.setHorizontalAlignment(SwingConstants.CENTER);
		IDField.setFont(IDfont);
		IDField.setBackground(backgroundColor);
		
		startscreen.getContentPane().setLayout(new GridLayout(6, 1, 0, 20));
		
		startscreen.getContentPane().add(timeline);
		startscreen.getContentPane().add(toBegin);
		

		// temp border
//		 title.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
//		 byAkarsh.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
//		 itis.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
//		 toBegin.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
//		 timeline.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

	}
	
	private static void startMatch(){
		
		account = new MatchFrame();

		JLabel deadline = new JLabel(
				"<html><div style=\"text-align: center;\">The deadline for answering questions is up "
						+ "and the matches have been made!<html>");
		deadline.setVerticalAlignment(SwingConstants.CENTER);
		deadline.setHorizontalAlignment(SwingConstants.CENTER);
		deadline.setFont(textFont);
		//deadline.setFont(standoutTextFont);
		//deadline.setForeground(new Color(0, 0, 150));
		
		
		JLabel toSee = new JLabel(
				"<html><div style=\"text-align: center;\">To see who you were matched up with, <br>type in your student ID or scan your ID card below!"
						+ "<html>");
		toSee.setHorizontalAlignment(SwingConstants.CENTER);
		toSee.setVerticalAlignment(SwingConstants.CENTER);
		toSee.setFont(standoutTextFont);
		toSee.setForeground(new Color(0, 0, 150));
		
		IDField.getDocument().addDocumentListener(fieldListener);
		IDField.setHorizontalAlignment(SwingConstants.CENTER);
		IDField.setFont(IDfont);
		IDField.setBackground(backgroundColor);
		
		startscreen.getContentPane().setLayout(new GridLayout(6, 1, 0, 20));
		
		
		startscreen.getContentPane().add(deadline);
		startscreen.getContentPane().add(toSee);
		
		

		// temp border
//		 titlePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
//		 byAkarshPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
//		 itis.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
//		 toSee.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
//		 timeline.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		
	}
	
	public static void tryLogin(String inp){
		if(inp == null || inp.equals("") || inp.length() < 2) return;
		
		Student student = null;
		
//		if(startupType == Startup.NORMAL_START)student = StudentList.mainList.get(inp);
//		if(startupType == Startup.MATCH_START)student = StudentList.matchList.get(inp);
		
		student = StudentList.mainList.get(inp);
		
		if(student ==null) return;
		
		IDField.setEnabled(false);
		
		startscreen.setVisible(false);
		
		account.login(student);
		
		
	}
	
	
	public static void backToMain(){
		startscreen.setVisible(true);
		
		clearIDField();
		
		IDField.setEnabled(true);
	}
	
	public static void clearIDField(){
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				IDField.setText(null);
			}
		});
	}
}
enum Startup{
	/**
	 * Normal program start, for students to answer questions
	 */
	NORMAL_START, 
	/**
	 * Matching program start, starts matching and matches students
	 */
	MATCH_START
}
