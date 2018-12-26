
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class EmployeeView extends JPanel {

	private static final long serialVersionUID = -1412293001015253732L;

	private final JScrollPane scrollPane;

	public EmployeeView() {
		super();

		scrollPane = new JScrollPane();

		JPanel panel = new JPanel();
		int numlabel = 50;
		panel.setLayout(new GridLayout(numlabel, 1));

		for (int i = 0; i < 50; i++) {
			JLabel label = new JLabel(" hey: " + i);
			label.setFont(new Font("Times New Roman", Font.BOLD, 40));
			panel.add(label);
		}

		scrollPane.setViewportView(panel);

		this.setLayout(new GridLayout(1, 1));
		this.add(scrollPane);
	}

}
