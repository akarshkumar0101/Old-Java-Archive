package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class Graph {

	public final JFrame frame;
	public final GraphUI graphui;

	private int startRange;
	private int range;
	private int currentindex;

	private final List<Integer> primes;
	private final List<Integer> composites;

	public Graph() {
		frame = new JFrame();
		graphui = new GraphUI(this);

		frame.getContentPane().add(graphui);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1500, 500);
		graphui.setFocusable(true);
		graphui.requestFocus();

		primes = new ArrayList<>();
		composites = new ArrayList<>();
	}

	public void start() {
		startRange = 0;
		range = 100;
		currentindex = -1;
		frame.setVisible(true);

		increment();
	}

	public void increment() {
		currentindex++;
		frame.repaint();

		if (currentindex < 2 || isComposite(currentindex))
			return;
		int numcomposite = 0;
		for (int k = 2; k < 100; k++) {
			if (!composites.contains(k * currentindex)) {
				composites.add(k * currentindex);
				if (k * currentindex < 100) {
					numcomposite++;
				}
			}
		}
		System.out.println(numcomposite);
		for (int i = currentindex; i < currentindex * currentindex; i++) {
			if (!isComposite(i)) {
				primes.add(i);
			}
		}
		frame.repaint();

	}

	public void increaseRange() {
		range *= 2;
		frame.repaint();
	}

	public void decreaseRange() {
		range /= 2;
		frame.repaint();
	}

	public void shiftLeft() {
		startRange -= (range / 4);
		frame.repaint();
	}

	public void shiftRight() {
		startRange += (range / 4);
		frame.repaint();
	}

	public boolean isUnknown(int a) {
		return !primes.contains(a) && !composites.contains(a);
	}

	public boolean isPrime(int a) {
		return primes.contains(a);
	}

	public boolean isComposite(int a) {
		return composites.contains(a);
	}

	public int getStartRange() {
		return startRange;
	}

	public int getRange() {
		return range;
	}

	public int getCurrentIndex() {
		return currentindex;
	}

}

class GraphUI extends JComponent implements KeyListener {

	private static final long serialVersionUID = 6092961682736373284L;

	private final Graph graph;

	public GraphUI(Graph graph) {
		this.graph = graph;
		addKeyListener(this);
	}

	@Override
	public void paintComponent(Graphics g) {
		int y = getHeight() / 2;
		g.setColor(Color.black);
		g.drawLine(0, y, getWidth(), y);

		int labeldist = graph.getRange() / 20;
		if (labeldist == 0) {
			labeldist = 1;
		}

		int circledia = (int) scale(graph.getStartRange(), graph.getRange() + graph.getStartRange(), 0, getWidth(),
				graph.getStartRange() + .7);
		int tickLength = (int) scale(graph.getStartRange(), graph.getRange() + graph.getStartRange(), 0, getWidth(),
				graph.getStartRange() + 1);
		int longTickLength = (int) scale(graph.getStartRange(), graph.getRange() + graph.getStartRange(), 0, getWidth(),
				graph.getStartRange() + 2);

		for (int i = graph.getStartRange(); i <= graph.getRange() + graph.getStartRange(); i++) {
			int x = (int) scale(graph.getStartRange(), graph.getRange() + graph.getStartRange(), 0, getWidth(), i);
			g.setColor(Color.black);
			g.drawLine(x, y - tickLength / 2, x, y + tickLength / 2);
			if (i == graph.getCurrentIndex()) {
				g.drawLine(x, 0, x, y);
			}

			if (graph.isUnknown(i)) {
				g.setColor(Color.black);
				g.drawOval(x - circledia / 2, y - circledia / 2, circledia, circledia);
			} else if (graph.isPrime(i)) {
				g.setColor(Color.blue);
				g.fillOval(x - circledia / 2, y - circledia / 2, circledia, circledia);
			} else if (graph.isComposite(i)) {
				g.setColor(Color.red);
				g.fillOval(x - circledia / 2, y - circledia / 2, circledia, circledia);
			}

			g.setColor(Color.black);
			if (i % labeldist == 0) {
				g.drawLine(x, y - longTickLength / 2, x, y + longTickLength / 2);
				drawCenteredString(g, "" + i, x, y + longTickLength + 10, g.getFont());
			}
		}

	}

	private double scale(double a1, double a2, double b1, double b2, double num) {
		double scale = (b1 - b2) / (a1 - a2);
		double dist = num - a1;
		dist *= scale;
		double newnum = dist + b1;
		return newnum;
	}

	private void drawCenteredString(Graphics g, String text, int x, int y, Font font) {
		// Get the FontMetrics
		FontMetrics metrics = g.getFontMetrics(font);
		int width = metrics.stringWidth(text);
		int height = metrics.getHeight();
		int textx = x - width / 2;
		int texty = y - height / 2;
		// Set the font
		g.setFont(font);
		// Draw the String
		g.drawString(text, textx, texty);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {

		case (KeyEvent.VK_SPACE): {
			graph.increment();
			break;
		}
		case (KeyEvent.VK_UP): {
			graph.increaseRange();
			break;
		}
		case (KeyEvent.VK_DOWN): {
			graph.decreaseRange();
			break;
		}
		case (KeyEvent.VK_RIGHT): {
			graph.shiftRight();
			break;
		}
		case (KeyEvent.VK_LEFT): {
			graph.shiftLeft();
			break;
		}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}
