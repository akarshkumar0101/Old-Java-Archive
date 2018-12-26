package main;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Main {

	public static void main(String[] args) {
		JButton normalStartC = new JButton("Normal Start");
		normalStartC.setToolTipText("This is the normal start "
				+ "to let students login and answer questions");
		
		normalStartC.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.getWindowAncestor(normalStartC).dispose();
			    Program.start(Startup.NORMAL_START);
			}
		});
		
		JButton matchStartC = new JButton("Match Start");
		matchStartC.setToolTipText("This is matching start that "
				+ "will run the algorithm to match students "
				+ "and will let students login and see their matches");
		
		matchStartC.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.getWindowAncestor(matchStartC).dispose();
			    Program.start(Startup.MATCH_START);
			}
		});
		
		ImageIcon icon = new ImageIcon(Program.class.getResource("/images/startupheart.png"));
		Image img = icon.getImage();
		img = img.getScaledInstance(100, 80, Image.SCALE_SMOOTH);
		icon = new ImageIcon(img);
		
		JOptionPane.showOptionDialog(
				null, "How would you like to start the program?",
				"Choose Start Type", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				icon, new Object[]{normalStartC, matchStartC}, null);

	}
	
}
