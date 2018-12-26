package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;

import main.TicTacToe;

public class TFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private final GridLayout gridLayout;

	private final JButton[][] labels;

	private final TicTacToe game;

	public TFrame(TicTacToe game) {
		super("TicTacToe by Akarsh Kumar");

		this.game = game;

		labels = new JButton[3][3];
		gridLayout = new GridLayout(3, 3);

		setLayout(gridLayout);

		for (int y = 2; y >= 0; y--) {
			for (int x = 0; x < labels.length; x++) {
				labels[x][y] = new JButton(" ");
				labels[x][y].setBorder(BorderFactory.createLineBorder(Color.black, 10));
				labels[x][y].setFont(new Font("Times New Roman", Font.BOLD, 100));
				labels[x][y].setBackground(Color.WHITE);
				int xx = x, yy = y;
				labels[x][y].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if (game.board.get(xx, yy) == 0) {
							game.takeInp(xx, yy);
						}
					}

				});
				this.add(labels[x][y]);
			}
		}

		this.setSize(900, 900);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void updateUI() {
		for (int y = 2; y >= 0; y--) {
			for (int x = 0; x < 3; x++) {
				int piece = game.board.get(x, y);
				char c = (piece == TicTacToe.PLAYX) ? 'X' : (piece == TicTacToe.PLAYO ? 'O' : ' ');
				labels[x][y].setText(c + "");
			}
		}
		this.repaint();
	}

}
