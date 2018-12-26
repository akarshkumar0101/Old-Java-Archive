package plot;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class ScatterPlotDisplay extends JFrame {

	private static final long serialVersionUID = 7422667067593962262L;

	private final ScatterPlotPainter painter;

	private boolean drawLines;

	private double minx = 0, maxx = 0, miny = 0, maxy = 0;
	private double dminx = -1, dmaxx = 1, dminy = -1, dmaxy = 1;
	private ScatterPlot plot = new ScatterPlot() {
		@Override
		public void add(double x, double y) {
			super.add(x, y);
			minx = Math.min(x, minx);
			maxx = Math.max(x, maxx);
			miny = Math.min(y, miny);
			maxy = Math.max(y, maxy);
			updateBoundry();
			painter.repaint();
		}

		public void updateBoundry() {
			double dx = maxx - minx, dy = maxy - miny;
			dminx = minx - dx / 10;
			dmaxx = maxx + dx / 10;
			dminy = miny - dy / 10;
			dmaxy = maxy + dy / 10;
		}
	};

	public ScatterPlotDisplay() {
		drawLines = false;

		painter = new ScatterPlotPainter();

		getContentPane().add(painter);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(900, 900);
	}

	public ScatterPlot getPlot() {
		return plot;
	}

	public void setDrawLines(boolean drawLines) {
		this.drawLines = drawLines;
	}

	public boolean getDrawLines() {
		return drawLines;
	}

	class ScatterPlotPainter extends JComponent {

		private static final long serialVersionUID = -554644453253975782L;

		@Override
		public void paintComponent(Graphics g) {
			g.setColor(Color.BLACK);
			g.drawLine(xToDispX(0), 0, xToDispX(0), getHeight());
			g.drawLine(0, yToDispY(0), getWidth(), yToDispY(0));

			for (int i = 0; i < plot.points.size(); i++) {
				double[] point = plot.points.get(i);
				g.fillOval(xToDispX(point[0]) - getWidth() / 200, yToDispY(point[1]) - getHeight() / 200,
						getWidth() / 100, getHeight() / 100);

				if (drawLines && i > 0) {
					double[] point2 = plot.points.get(i - 1);
					g.drawLine(xToDispX(point2[0]), yToDispY(point2[1]), xToDispX(point[0]), yToDispY(point[1]));
				}

			}
		}

		public int xToDispX(double x) {
			return (int) scale(x, dminx, dmaxx, 0, getWidth());
		}

		public int yToDispY(double y) {
			return (int) scale(y, dminy, dmaxy, getHeight(), 0);
		}

		public double scale(double num, double min1, double max1, double min2, double max2) {
			double ans = num - min1;
			ans *= (max2 - min2) / (max1 - min1);
			ans += min2;
			return ans;
		}
	}
}
