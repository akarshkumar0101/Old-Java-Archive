package main;

import java.awt.Color;
import java.util.ArrayList;

import math.Phrase;

public class Function {
	public Color color;
	public Phrase phrase;

	ArrayList<Double> points = new ArrayList<Double>();

	public Function(Phrase phrase) {
		this.phrase = phrase;
	}

	public void setColor(Color col) {
		color = col;
	}

}
