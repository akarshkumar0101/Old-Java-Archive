package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

import math.CalMath;

public class Main {
	public static final JFrame frame = new JFrame("Akarsh's Calculator 1.0!");

	public static void main(String[] args) throws Exception {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(1, 1));
		frame.setMinimumSize(new Dimension(625, 625));

		GraphPanel gp = new GraphPanel(frame);

		Function[] functs = new Function[] { new Function(CalMath.createPhrase("e^x")),
				new Function(CalMath.createPhrase("sin(cos(tanx))")), new Function(CalMath.createPhrase("x*cosx")),
				new Function(CalMath.createPhrase("x")) };

		// Function[] functs = new Function[] { new
		// Function(CalMath.createPhrase("tanx")) };

		for (Function f : functs) {
			// f.color = new Color((int) (Math.random() * 256), (int)
			// (Math.random() * 256), (int) (Math.random() * 256));
			gp.functs.add(f);
		}

		frame.add(gp);

		frame.pack();
		frame.setSize(1000, 1000);

		gp.setBorder(BorderFactory.createLineBorder(Color.red));

		frame.setVisible(true);

		gp.init();

	}

}
