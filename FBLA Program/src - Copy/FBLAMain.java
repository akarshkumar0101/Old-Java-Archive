
import java.awt.Color;

import javax.swing.BorderFactory;

public class FBLAMain {

	public static void main(String[] args) {
		MainFrame mainFrame = new MainFrame();

		EmployeeView employeeView = new EmployeeView();
		mainFrame.getContentPane().add(employeeView);

		employeeView.setBorder(BorderFactory.createLineBorder(Color.red));

		mainFrame.setVisible(true);

	}

}
