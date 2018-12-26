package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import clumpfind.ClumpFind;

public class Display {

	static double[][] points = {};

	public static void main(String[] args) {

		points = new double[100][2];

		for (int i = 0; i < points.length; i++) {
			int[] gen = genCoor();
			// System.out.println(gen[0] + " " + gen[1]);
			points[i] = new double[] { gen[0], gen[1] };
		}

		GridDisplay frame = new GridDisplay(points);

		ClumpFind clumpFind = new ClumpFind(points);
		double avgdisttoclosest = clumpFind.avgDistToClosest();
		frame.highlight(clumpFind.findBiggestClump(avgdisttoclosest));

		frame.setCircleRad(avgdisttoclosest);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	private static int[] genCoor() {
		double[] coors = generateCoordinate(-100, 100, -100, 100);
		return new int[] { (int) coors[0], (int) coors[1] };
	}

	private static double[] generateCoordinate(double xmin, double xmax, double ymin, double ymax) {
		if (xmax < xmin || ymax < ymin)
			throw new RuntimeException("wat");

		double x = Math.random() * (xmax - xmin) + xmin;
		double y = Math.random() * (ymax - ymin) + ymin;

		return new double[] { x, y };
	}
}

class GridDisplay extends JFrame {

	private static final long serialVersionUID = 1061404726935235959L;

	private final double[][] coors;

	private double xmin, xmax, ymin, ymax;

	private final ArrayList<double[]> highlightedPoints;

	private GridPanel contentPanel;

	private double circleRad = 0;

	public void setCircleRad(double rad) {
		circleRad = rad;
	}

	public GridDisplay(double[][] coors) {
		this.coors = coors;
		contentPanel = new GridPanel();
		setContentPane(contentPanel);
		determineParam(coors);

		highlightedPoints = new ArrayList<>();
	}

	private void determineParam(double[][] points) {
		double minx = Integer.MAX_VALUE, miny = Integer.MAX_VALUE, maxx = Integer.MIN_VALUE, maxy = Integer.MIN_VALUE;

		for (double[] coor : points) {
			if (coor[0] < minx) {
				minx = coor[0];
			}
			if (coor[0] > maxx) {
				maxx = coor[0];
			}
			if (coor[1] < miny) {
				miny = coor[1];
			}
			if (coor[1] > maxy) {
				maxy = coor[1];
			}

		}
		double rangex = (maxx - minx) / 10, rangey = (maxy - miny) / 10;
		xmin = minx - rangex;
		xmax = maxx + rangex;
		ymin = miny - rangey;
		ymax = maxy + rangey;
	}

	private int[] toPixel(double pointx, double pointy) {
		return new int[] { toPixelX(pointx), toPixelY(pointy) };
	}

	private int toPixelX(double pointx) {
		return (int) Math.round(getScaledNumber(xmin, xmax, 0, getWidth(), pointx));
	}

	private int toPixelY(double pointy) {
		return (int) Math.round(getScaledNumber(ymin, ymax, getHeight(), 0, pointy));
	}

	private static double getScaledNumber(double ori1, double ori2, double scaleto1, double scaleto2, double number) {
		// move original scale down to zero
		number -= ori1;

		// get scale factor
		double scalefactor = (scaleto2 - scaleto1) / (ori2 - ori1);

		// scale to factor
		number *= scalefactor;

		// move up to required scale;
		number += scaleto1;

		return number;
	}

	public void highlight(double[] coor) {
		highlightedPoints.add(coor);
	}

	private class GridPanel extends JPanel {
		private static final long serialVersionUID = -1388529719827064824L;

		public GridPanel() {
			setPreferredSize(new Dimension(900, 900));
		}

		@Override
		public void paintComponent(Graphics g) {
			int pixorix = toPixelX(0), pixoriy = toPixelY(0);

			int circleRadx = (int) getScaledNumber(xmin, xmax, 0, getWidth(), xmin + circleRad);
			int circleRady = (int) getScaledNumber(ymin, ymax, 0, getHeight(), ymin + circleRad);

			g.drawLine(pixorix, 0, pixorix, getHeight());
			g.drawLine(0, pixoriy, getWidth(), pixoriy);

			g.setColor(Color.black);
			for (double[] coor : coors) {
				int[] pixcoor = toPixel(coor[0], coor[1]);
				g.fillRect(pixcoor[0] - 1, pixcoor[1], 3, 1);
				g.fillRect(pixcoor[0], pixcoor[1] - 1, 1, 3);
				g.fillOval(pixcoor[0] - 5, pixcoor[1] - 5, 10, 10);
				g.drawOval(pixcoor[0] - circleRadx, pixcoor[1] - circleRady, circleRadx * 2, circleRady * 2);
			}
			g.setColor(Color.red);
			for (double[] coor : highlightedPoints) {
				int[] pixcoor = toPixel(coor[0], coor[1]);
				g.fillOval(pixcoor[0] - 3, pixcoor[1] - 3, 6, 6);
				g.drawOval(pixcoor[0] - circleRadx, pixcoor[1] - circleRady, circleRadx * 2, circleRady * 2);
			}
		}
	}

}