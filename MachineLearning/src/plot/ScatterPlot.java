package plot;

import java.util.ArrayList;
import java.util.List;

public class ScatterPlot {

	protected final List<double[]> points;

	protected double currentX;
	protected double xInc;

	public ScatterPlot() {
		points = new ArrayList<double[]>();
		currentX = 0;
		xInc = 1;
	}

	public void add(double x, double y) {
		points.add(new double[] { x, y });
	}

	public void add(double y) {
		add(currentX, y);
		currentX += xInc;
	}

	public double getCurrentX() {
		return currentX;
	}

	public void setCurrentX(double x) {
		currentX = x;
	}

	public double getXInc() {
		return xInc;
	}

	public void setXInc(double xInc) {
		this.xInc = xInc;
	}
}
