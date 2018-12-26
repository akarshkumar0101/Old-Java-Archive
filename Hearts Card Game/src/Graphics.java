import javax.swing.*;
import java.awt.*;


public class Graphics {
	
	JFrame frame;
	
	public Graphics(){
		frame = new JFrame("Hearts Card Game");
		
		frame.setVisible(true);
		
		GridLayout layout = new GridLayout(2,6);
		
		frame.setLayout(layout);
		
		frame.setSize(1000, 500);
		
		frame.setVisible(true);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}
	
}
