package frames;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import main.Program;
import data.Student;

public abstract class Account {
	
	protected static final Font questionFont = new Font("MV Boli", Font.BOLD, 30);
	protected static final Font textFont = new Font("MV Boli", Font.BOLD, 22);
	protected static final Font buttonFont = new Font("MV Boli", Font.BOLD, 40);
	
	protected static final Color buttonColor = new Color(220, 220, 220);
	
	protected JPanel titlePanel = new JPanel();
	protected JLabel studentInfoLabel = new JLabel();
	
	protected JFrame frame;
	protected Student current;
	
	
	
	protected static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	protected WindowListener windowListener = new WindowListener(){

		@Override
		public void windowActivated(WindowEvent e) {}
		@Override
		public void windowClosed(WindowEvent e) {}
		@Override
		public void windowClosing(WindowEvent e) { //ON CLOSE
			logout();
		}
		@Override
		public void windowDeactivated(WindowEvent e) {
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		}
		@Override
		public void windowDeiconified(WindowEvent e) {}
		@Override
		public void windowIconified(WindowEvent e) {}
		@Override
		public void windowOpened(WindowEvent arg0) {}
		
	};
	{
		ImageIcon heartpic = new ImageIcon(Program.class.getResource("/images/heart.png"));
		Image i = heartpic.getImage();
		i = i.getScaledInstance(130, 130, Image.SCALE_SMOOTH);
		heartpic = new ImageIcon(i);
		
		JLabel picLabel1 = new JLabel(heartpic);
		picLabel1.setVerticalAlignment(SwingConstants.BOTTOM);
		JLabel picLabel2 = new JLabel(heartpic);
		picLabel2.setVerticalAlignment(SwingConstants.BOTTOM);
		titlePanel.setLayout(new GridLayout(1,3));
		
		studentInfoLabel.setFont(textFont);
		studentInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		titlePanel.add(picLabel1);
		titlePanel.add(studentInfoLabel);
		titlePanel.add(picLabel2);
		
		
	}
	/**
	 * when overriding, should definitely call super.login();
	 * @param stu
	 */
	public void login(Student stu){
		
		current = stu;
		frame = new JFrame();
		frame.addWindowListener(windowListener);
		
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setResizable(false);

		frame.setUndecorated(true);
		
		studentInfoLabel.setText("<html><div style=\"text-align: center;\">"+ current.getDisplayString() +"<html>");
		
		
	}
	
	/**
	 * when overriding, should call super.logout() AFTER 
	 * other things are done
	 */
	public void logout(){
		
		frame.dispose();
		Program.backToMain();
	}
}
