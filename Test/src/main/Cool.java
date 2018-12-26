package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class Cool {
	// first one added is painted last(most priority)

	public static void main(String[] args) {
		JFrame frame = new JFrame();

		JComponent jc1 = new JComponent() {
			private static final long serialVersionUID = -8736303012013623167L;

			@Override
			public void paint(Graphics g) {
				g.setColor(Color.red);
				for (int i = 0; i < 1000; i++) {
					g.drawLine(genRandCoor(getWidth()), genRandCoor(getHeight()), genRandCoor(getWidth()),
							genRandCoor(getHeight()));
				}
			}
		};
		JComponent jc2 = new JComponent() {
			private static final long serialVersionUID = -4832904893028490328L;

			@Override
			public void paint(Graphics g) {
				g.setColor(Color.blue);
				g.fillRect(0, 0, getWidth(), getHeight());
			}
		};
		jc1.setOpaque(false);
		jc2.setOpaque(false);

		frame.getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;

		frame.getContentPane().add(jc1, gbc);
		frame.getContentPane().add(jc2, gbc);

		frame.setSize(900, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		new Thread() {
			@Override
			public void run() {
				while (true) {
					jc1.repaint();
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();

	}

	private static int genRandCoor(int range) {
		return (int) (Math.random() * range);
	}
}
