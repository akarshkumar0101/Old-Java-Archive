package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import math.CalMath;

@SuppressWarnings("serial")
public class GraphPanel extends JComponent
		implements MouseListener, MouseMotionListener, MouseWheelListener, ComponentListener {

	public static final Font numFont = new Font("TimesRoman", Font.PLAIN, 25);
	public static final Font subFont = new Font("TimesRoman", Font.PLAIN, 17);

	public static final int MAX_NUM_FUNCTIONS = 8;
	// 25,14
	public static final int yoffset = 25, xoffset = 14;

	private final JFrame parentFrame;

	public GraphParameters param;
	public ArrayList<Function> functs;
	private ArrayList<Function> editfuncts;
	private ArrayList<JTextField> functfields;
	private final ArrayList<JTextField> paramfields;
	private final JRadioButton paramShowAxisButton;

	// USED JUST FOR TRACKING MOUSE DRAGS(moving graph)
	private int mousemovex = 0, mousemovey = 0;

	private class PointBox {
		// stored with pixels not units b/c avoiding too much calculations, and
		// mouse box only paints when cursor is active on the function(not when
		// moving graph or zooming in,etc)
		private int pixx = 0, pixy = 0;
		private double valx = 0, valy = 0;
		private Function func;

		private static final int CIRCLERAD = 6;
		private static final int CIRCLEDIA = 2 * CIRCLERAD;

		public PointBox() {
		}

		public PointBox(PointBox another) {
			pixx = another.pixx;
			pixy = another.pixy;
			valx = another.valx;
			valy = another.valy;
			func = another.func;
		}

		/**
		 * pixx coordinates should be updated through the
		 * PointBox.updateBixCoordinate method prior to calling this method
		 */
		private void paint(Graphics g, boolean clicked, boolean highlight) {
			if (!(pixx > 0 && pixx < getWidth() && pixy > 0 && pixy < getHeight()))
				return;
			int raddif = clicked ? 10 : (highlight ? CIRCLERAD * 2 / 3 : 0);
			int diadif = raddif * 2;
			g.fillOval(pixx - CIRCLERAD - raddif, pixy - CIRCLERAD - raddif, CIRCLEDIA + diadif, CIRCLEDIA + diadif);

			// drawing the box
			String toDraw = valx + ", " + valy;
			int boxw = toDraw.length() * (xoffset - 1) + 8;
			int boxh = yoffset + 6;
			g.clearRect(pixx + 7, pixy - 10 - yoffset, boxw, boxh);
			g.drawRect(pixx + 7, pixy - 10 - yoffset, boxw, boxh);
			g.drawString(toDraw, pixx + 10, pixy - 10);
		}

		public void updatePixCoordinate() {
			pixx = (int) getScaledNumber(param.minx, param.maxx, 0, getWidth(), valx);
			pixy = (int) getScaledNumber(param.miny, param.maxy, getHeight(), 0, valy);
		}

		@Override
		public boolean equals(Object another) {
			if (another.getClass() != PointBox.class)
				return false;
			PointBox an = (PointBox) another;
			updateUnitRangeCheck();
			if (func == an.func && pixx <= an.pixx + CIRCLEDIA && pixx >= an.pixx - CIRCLEDIA
					&& pixy <= an.pixy + CIRCLEDIA && pixy >= an.pixy - CIRCLEDIA && isSameXUnit(valx, an.valx)
					&& isSameYUnit(valy, an.valy))
				return true;
			else
				return false;
		}

	}

	private PointBox mBox = new PointBox();
	private boolean showmBox = false;
	private boolean showmBoxPrev = false;
	private boolean clickingmBox = false;

	private ArrayList<PointBox> savedBoxes = new ArrayList<PointBox>();

	private final AddFunctionButton addFunctionButton;

	private final JButton functApplyButton;
	private final JButton settingsApplyButton;

	private final JButton functionsButton;
	private final JDialog functionsWindow;

	private final JButton settingsButton;
	private final JDialog settingsWindow;

	private final JPanel optionsPanel;

	private class AddFunctionButton extends JButton implements ActionListener {

		public AddFunctionButton() {
			super(getImage("add", 50, 50));
			addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				editfuncts.add(new Function(CalMath.createPhrase("")));
			} catch (Exception e1) {
			}
			cfgFunctionsWindow(editfuncts);
			functionsWindow.revalidate();
			functionsWindow.repaint();
		}

	}

	private class DialogDisplayer implements ActionListener {
		JDialog toOpen;

		public DialogDisplayer(JDialog toOpen) {
			super();
			this.toOpen = toOpen;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (toOpen == functionsWindow) {
				cfgFunctionsWindow(functs);
			} else if (toOpen == settingsWindow) {
				cfgSettingsWindow();
			}
			toOpen.setVisible(true);
		}
	}

	private class FunctionRemoveButton extends JButton implements ActionListener {

		Function functToRemove;

		public FunctionRemoveButton(Function f) {
			super(getImage("delete", 50, 50));
			functToRemove = f;
			setFont(numFont);
			addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			editfuncts.remove(functToRemove);
			cfgFunctionsWindow(editfuncts);
			functionsWindow.revalidate();
			functionsWindow.repaint();
		}
	}

	public GraphPanel(JFrame frame) {
		super();
		parentFrame = frame;
		param = GraphParameters.getDefaultParam();
		paramfields = new ArrayList<JTextField>();
		paramShowAxisButton = new JRadioButton("Show Axis");

		functs = new ArrayList<Function>();
		addMouseMotionListener(this);
		addMouseListener(this);
		addMouseWheelListener(this);
		addComponentListener(this);

		functionsWindow = new JDialog(frame, "Functions");
		functionsWindow.setModal(true);
		functionsWindow.setMinimumSize(new Dimension(900, 900));
		functionsWindow.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		functionsWindow.setResizable(false);
		functionsWindow.setLocationRelativeTo(frame);

		settingsWindow = new JDialog(frame, "Settings");
		settingsWindow.setModal(true);
		settingsWindow.setMinimumSize(new Dimension(900, 900));
		settingsWindow.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		settingsWindow.setResizable(false);
		settingsWindow.setLocationRelativeTo(frame);

		functionsButton = new JButton("Functions");
		functionsButton.addActionListener(new DialogDisplayer(functionsWindow));
		functionsButton.setFont(numFont);
		settingsButton = new JButton("Settings");
		settingsButton.addActionListener(new DialogDisplayer(settingsWindow));
		settingsButton.setFont(numFont);

		optionsPanel = new JPanel();
		optionsPanel.setLayout(new GridLayout(1, 2));

		optionsPanel.add(functionsButton);
		optionsPanel.add(settingsButton);

		functionsWindow.setLayout(new GridLayout(MAX_NUM_FUNCTIONS + 1, 1));
		settingsWindow.setLayout(new GridLayout(6, 1));

		functApplyButton = new JButton("Apply");
		functApplyButton.setFont(numFont);
		functApplyButton.addActionListener(e -> {
			functs.clear();
			for (int i = 0; i < functfields.size(); i++) {
				String funct = functfields.get(i).getText();
				if (funct.equals("")) {
					continue;
				}
				try {
					functs.add(new Function(CalMath.createPhrase(funct)));
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(functionsWindow,
							"<html>y<sub>" + i + "</sub> is an invalid function</html>", "Error",
							JOptionPane.ERROR_MESSAGE, null);
				}
			}
			editfuncts = functs;
			functionsWindow.revalidate();
			functionsWindow.repaint();
			graphAllFunctions(true);
		});
		settingsApplyButton = new JButton("Apply");
		settingsApplyButton.setFont(numFont);
		settingsApplyButton.addActionListener(e -> {
			double[] params = new double[4];
			try {
				params[0] = Double.parseDouble(paramfields.get(0).getText());
				params[1] = Double.parseDouble(paramfields.get(1).getText());
				params[2] = Double.parseDouble(paramfields.get(2).getText());
				params[3] = Double.parseDouble(paramfields.get(3).getText());

				if (params[1] <= params[0] || params[3] <= params[2])
					throw new Exception();

				param.minx = params[0];
				param.maxx = params[1];
				param.miny = params[2];
				param.maxy = params[3];
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(functionsWindow, "Invalid parameters", "Error", JOptionPane.ERROR_MESSAGE,
						null);

			}
			param.showaxis = paramShowAxisButton.isSelected();

			cfgSettingsWindow();
			settingsWindow.revalidate();
			settingsWindow.repaint();
			graphAllFunctions(true);
		});

		addFunctionButton = new AddFunctionButton();
		addFunctionButton.setContentAreaFilled(false);
		addFunctionButton.setBorderPainted(false);

		functfields = new ArrayList<JTextField>();
		for (

		int i = 0; i < 4; i++) {
			paramfields.add(new JTextField());
			paramfields.get(i).setFont(numFont);
		}
		paramShowAxisButton.setFont(numFont);
		paramShowAxisButton.setHorizontalAlignment(SwingConstants.CENTER);
		paramShowAxisButton.setHorizontalTextPosition(SwingConstants.LEFT);

		setLayout(null);
		this.add(optionsPanel);

		cfgFunctionsWindow(functs);
		cfgSettingsWindow();
	}

	public void init() {
		functionsWindow.setLocation((parentFrame.getWidth() - functionsWindow.getWidth()) / 2,
				(parentFrame.getHeight() - functionsWindow.getHeight()) / 2);
		settingsWindow.setLocation((parentFrame.getWidth() - functionsWindow.getWidth()) / 2,
				(parentFrame.getHeight() - functionsWindow.getHeight()) / 2);

		optionsPanel.setBounds(getWidth() - 300, 0, 300, 50);

		graphAllFunctions(true);
	}

	private void cfgFunctionsWindow(ArrayList<Function> toDisplay) {
		functionsWindow.getContentPane().removeAll();

		editfuncts = new ArrayList<Function>(toDisplay);

		functfields.clear();

		for (int i = 0; i < toDisplay.size(); i++) {
			Function f = toDisplay.get(i);

			JPanel panel = new JPanel();
			panel.setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();

			JLabel y = new JLabel("<html>y<sub>" + i + "</sub> = </html>");
			y.setFont(numFont);
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.ipadx = 20;
			gbc.gridx = 0;
			gbc.gridy = 0;
			panel.add(y, gbc);

			JPanel temppanel = new JPanel();
			JTextField function = new JTextField(f.phrase.toString());
			function.setPreferredSize(new Dimension(600, 35));
			function.setFont(numFont);
			function.setBorder(BorderFactory.createLineBorder(Color.red));
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.ipadx = 600;
			gbc.gridx = 1;
			gbc.gridy = 0;
			functfields.add(function);
			temppanel.add(function);
			panel.add(temppanel, gbc);

			FunctionRemoveButton removeb = new FunctionRemoveButton(f);
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.ipady = 0;
			gbc.ipadx = 0;
			gbc.gridx = 3;
			gbc.gridy = 0;
			removeb.setPreferredSize(new Dimension(50, 50));
			removeb.setContentAreaFilled(false);
			removeb.setBorderPainted(false);
			panel.add(removeb, gbc);

			functionsWindow.add(panel);
		}
		int addedbutton = 0;
		if (toDisplay.size() < MAX_NUM_FUNCTIONS) {
			addFunctionButton.setBorder(BorderFactory.createLineBorder(Color.red));
			functionsWindow.add(addFunctionButton);
			addedbutton++;
		}
		for (int i = toDisplay.size() + addedbutton; i < MAX_NUM_FUNCTIONS; i++) {
			functionsWindow.add(new JLabel());
		}
		functionsWindow.add(functApplyButton);

	}

	private void cfgSettingsWindow() {
		settingsWindow.getContentPane().removeAll();

		paramfields.get(0).setText(param.minx + "");
		paramfields.get(1).setText(param.maxx + "");
		paramfields.get(2).setText(param.miny + "");
		paramfields.get(3).setText(param.maxy + "");

		GridBagConstraints gbc = new GridBagConstraints();
		for (int i = 0; i < 4; i++) {
			JPanel panel = new JPanel();
			panel.setLayout(new GridBagLayout());

			JLabel paramname = new JLabel();
			paramname.setFont(numFont);
			if (i == 0) {
				paramname.setText("Min x: ");
			}
			if (i == 1) {
				paramname.setText("Max x: ");
			}
			if (i == 2) {
				paramname.setText("Min y: ");
			}
			if (i == 3) {
				paramname.setText("Max y: ");
			}

			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.ipadx = 10;
			gbc.gridx = 0;
			gbc.gridy = 0;
			panel.add(paramname, gbc);

			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.ipadx = 200;
			gbc.gridx = 1;
			gbc.gridy = 0;
			paramfields.get(i).setFont(numFont);
			panel.add(paramfields.get(i), gbc);
			settingsWindow.add(panel);
		}
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipadx = 200;
		gbc.gridx = 1;
		gbc.gridy = 0;
		paramShowAxisButton.setSelected(param.showaxis);
		settingsWindow.add(paramShowAxisButton);
		settingsWindow.add(settingsApplyButton);
	}

	@Override
	public void paintComponent(Graphics g) {
		// TODO comment next line out see what happens, move it and zoom in/out
		super.paintComponent(g);

		g.setColor(Color.BLACK);

		// pixel center

		// System.out.println(param.minx + " " + param.maxx);
		// System.out.println(param.miny + " " + param.maxy);
		// System.out.println(actualcenterx + " " + actualcentery);
		// System.out.println(getScaledNumber(0, 10, 0, 100, 5));

		g.setFont(numFont);

		if (param.showaxis) {
			// pixels
			int actualcenterx = (int) getScaledNumber(param.minx, param.maxx, 0, getWidth(), 0);
			int actualcentery = (int) getScaledNumber(param.miny, param.maxy, getHeight(), 0, 0);

			g.drawLine(actualcenterx, 0, actualcenterx, getHeight());
			g.drawLine(0, actualcentery, getWidth(), actualcentery);
			g.drawString(0 + "", actualcenterx - xoffset, actualcentery + yoffset);

			g.drawString(param.maxx + "", getWidth() - xoffset * (param.maxx + "").length(), actualcentery + yoffset);
			g.drawString(param.minx + "", 0, actualcentery + yoffset);
			g.drawString(param.maxy + "", actualcenterx - xoffset * (param.maxy + "").length(), yoffset);
			g.drawString(param.miny + "", actualcenterx - xoffset * (param.miny + "").length(), getHeight() - yoffset);
		}

		for (Function f : functs) {

			g.setColor(f.color);

			for (int i = 0; i < f.points.size() - 3; i = i + 2) {

				// pixels
				int x = (int) getScaledNumber(param.minx, param.maxx, 0, getWidth(), f.points.get(i));
				int y = (int) getScaledNumber(param.miny, param.maxy, getHeight(), 0, f.points.get(i + 1));
				int x2 = (int) getScaledNumber(param.minx, param.maxx, 0, getWidth(), f.points.get(i + 2));
				int y2 = (int) getScaledNumber(param.miny, param.maxy, getHeight(), 0, f.points.get(i + 3));

				if (true) {// if (Math.abs(y2 - y) < (getHeight())) {
					g.drawLine(x, y, x2, y2);
					for (int ii = 1; ii < 2; ii++) {
						g.drawLine(x, y - ii, x2, y2 - ii);
						g.drawLine(x, y + ii, x2, y2 + ii);
					}
				}
				// g.drawLine(0, 0, x2, y2);
				// g.drawLine(f.points.get(i), f.points.get(i + 1),
				// f.points.get(i + 2), f.points.get(i + 3));
			}
		}

		for (PointBox bx : savedBoxes) {
			bx.updatePixCoordinate();
			g.setColor(bx.func.color);
			bx.paint(g, clickingmBox && mBox.equals(bx), false);
		}
		if (showmBox) {
			int index = savedBoxes.indexOf(mBox);
			g.setColor(mBox.func.color);
			if (index < 0) {
				mBox.paint(g, clickingmBox, false);
			} else {
				savedBoxes.get(index).paint(g, clickingmBox, true);
			}
			// now draw the box (top left)
			int corneroffset = 10;
			String eqS = "y  = " + mBox.func.phrase.toString();
			String fnumS = functs.indexOf(mBox.func) + "";

			int boxx = corneroffset + 10;
			int boxy = corneroffset;
			int boxw = eqS.length() * (xoffset - 4) + 8 + corneroffset;
			int boxh = yoffset + corneroffset;

			g.clearRect(boxx, boxy, boxw, boxh);

			g.drawString(eqS, xoffset + corneroffset, yoffset + corneroffset);

			g.setFont(subFont);
			g.drawString(fnumS, xoffset * 2 + corneroffset, (yoffset * 4 / 3) + corneroffset);

			g.drawRect(boxx, boxy, boxw, boxh);

		}
	}

	private static ImageIcon getImage(String img, int x, int y) {
		ImageIcon icon = new ImageIcon(GraphPanel.class.getResource("/img/" + img + ".png"));
		Image image = icon.getImage();
		image = image.getScaledInstance(x, y, Image.SCALE_SMOOTH);
		icon = new ImageIcon(image);
		return icon;
	}

	public void graphFunction(Function f) {
		// System.out.println("Calculating");

		// distance per pixel
		final double xres = (param.maxx - param.minx) / getWidth();
		final double yres = (param.maxy - param.miny) / getHeight();

		final int pixwidth = getWidth();
		final int pixheight = getHeight();

		// scale lengths
		final double xSCL = param.maxx - param.minx;
		final double ySCL = param.maxy - param.miny;

		double prevx = param.minx, prevy = f.phrase.getValue(param.minx);
		f.points.clear();

		double maxx = param.maxx;
		for (double x = param.minx; x <= maxx;) {

			// adding unit values, not pixel values
			f.points.add(x);

			double y = f.phrase.getValue(x);
			numCal++;
			f.points.add(y);

			double dx = x - prevx;
			double dy = y - prevy;

			// The following lines are used to make points between x pixels
			// to support points at a high slope location. It works by making a
			// line of points between two far away points.

			// if (Math.abs(dy) > yres) {
			// int numpix = (int) (Math.abs(dy) / yres);
			// // got prevx and x and prevy and y
			// double xinc = dx / numpix, yinc = dy / numpix;
			//
			// double xx = prevx + xinc, yy = prevy + yinc;
			// for (int pix = 1; pix < numpix; pix++) {
			// f.points.add(xx);
			// f.points.add(yy);
			// xx += xinc;
			// yy += yinc;
			// }
			// }

			x += xres;

			prevy = y;
			prevx = x;
		}
	}

	// public void graphFunction(Function f) {
	// // System.out.println("Calculating");
	//
	// // x distance per pixel
	// final double xres = (param.maxx - param.minx) / getWidth();
	// double prevxresuse = 0, xresuse = xres;
	// double prevy = 0, dy = 0, dypix = 0;
	//
	// f.points.clear();
	//
	// for (double x = param.minx; x <= param.maxx;) {
	//
	// // adding unit values, not pixel values
	// f.points.add(x);
	//
	// double y = f.phrase.getValue(x);
	// numCal++;
	// f.points.add(y);
	//
	// // System.out.println(x+ +f.phrase.getValue(x));
	//
	// dy = (y - prevy) / prevxresuse;
	// dypix = getScaledNumber(0, param.maxy - param.miny, 0, getHeight(), dy);
	//
	// // x += xres;
	// if (Math.abs(dy) > .5) {
	// x += xres / Math.abs(dy);
	// } else {
	// x += xres;
	// }
	//
	// prevy = y;
	// prevxresuse = xresuse;
	// }
	// }
	int numCal;

	public void graphAllFunctions(boolean shouldRepaint) {
		numCal = 0;
		for (Function f : functs) {
			graphFunction(f);
		}
		// System.out.println("Calculated " + numCal + " values");
		if (shouldRepaint) {
			repaint();
		}
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

	double rangex = 0, rangey = 0;

	private void updateUnitRangeCheck() {
		rangex = (param.maxx - param.minx) / 200;
		rangey = (param.maxy - param.miny) / 200;
	}

	private boolean isSameXUnit(double x1, double x2) {
		if (Math.abs(x1 - x2) <= rangex)
			return true;
		else
			return false;
	}

	private boolean isSameYUnit(double y1, double y2) {
		if (Math.abs(y1 - y2) <= rangey)
			return true;
		else
			return false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// dont show coor box when dragging mouse
		showmBox = false;

		int newx = e.getX(), newy = getHeight() - e.getY();
		int changex = newx - mousemovex, changey = newy - mousemovey;

		double changewindowx = changex * (param.maxx - param.minx) / (getWidth());
		double changewindowy = changey * (param.maxy - param.miny) / (-getHeight());

		param.maxx -= changewindowx;
		param.minx -= changewindowx;
		param.maxy += changewindowy;
		param.miny += changewindowy;

		mousemovex = newx;
		mousemovey = newy;

		graphAllFunctions(true);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent event) {
		// dont show coor box when dragging mouse
		showmBox = false;

		int moved = event.getWheelRotation();

		int mousex = event.getX();
		int mousey = event.getY();

		double graphmousex = (double) mousex / getWidth() * (param.maxx - param.minx) + param.minx;
		double graphmousey = ((1 - (double) mousey / getHeight()) * (param.maxy - param.miny) + param.miny);

		double maxx = param.maxx, minx = param.minx, maxy = param.maxy, miny = param.miny;

		param.maxx += moved * (maxx - graphmousex) / 10;
		param.minx -= moved * (graphmousex - minx) / 10;
		param.maxy += moved * (maxy - graphmousey) / 10;
		param.miny -= moved * (graphmousey - miny) / 10;

		graphAllFunctions(true);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		clickingmBox = true;
		repaint();

		mousemovex = e.getX();
		mousemovey = getHeight() - e.getY();

		// System.out.println(mousemovex + " " + mousemovey);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		clickingmBox = false;
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		double unitx = getScaledNumber(0, getWidth(), param.minx, param.maxx, e.getX());
		double unity = getScaledNumber(getHeight(), 0, param.miny, param.maxy, e.getY());

		updateUnitRangeCheck();
		for (Function f : functs) {
			for (int i = 0; i < f.points.size() - 1; i = i + 2) {
				// if (unitx == f.points.get(i) && unity == f.points.get(i +1){
				if (isSameXUnit(f.points.get(i), unitx) && isSameYUnit(f.points.get(i + 1), unity)) {
					mBox.valx = f.points.get(i);
					mBox.valy = f.points.get(i + 1);
					// cant just use e.getx/y b/c it moves the with the cursor,
					// try it
					mBox.pixx = (int) getScaledNumber(param.minx, param.maxx, 0, getWidth(), mBox.valx);
					mBox.pixy = (int) getScaledNumber(param.miny, param.maxy, getHeight(), 0, mBox.valy);
					mBox.func = f;
					showmBox = true;
					showmBoxPrev = true;
					repaint();
					return;
				}
			}
		}
		showmBox = false;
		if (showmBox != showmBoxPrev) {
			repaint();
		}
		showmBoxPrev = showmBox;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (showmBox) {
			int i = savedBoxes.indexOf(mBox);

			if (i < 0) {
				savedBoxes.add(new PointBox(mBox));
			} else {
				savedBoxes.remove(i);
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void componentResized(ComponentEvent e) {
		optionsPanel.setBounds(getWidth() - 300, 0, 300, 50);
	}

	@Override
	public void componentMoved(ComponentEvent e) {
	}

	@Override
	public void componentShown(ComponentEvent e) {
	}

	@Override
	public void componentHidden(ComponentEvent e) {
	}

}

class GraphParameters {
	public boolean showaxis;

	public double minx;
	public double maxx;
	public double miny;
	public double maxy;

	public GraphParameters(GraphParameters another) {
		showaxis = another.showaxis;

		minx = another.minx;
		maxx = another.maxx;
		miny = another.miny;
		maxy = another.maxy;
	}

	public GraphParameters() {
	}

	public static GraphParameters getDefaultParam() {
		GraphParameters param = new GraphParameters();

		param.minx = -10;
		param.maxx = 10;
		param.miny = -10;
		param.maxy = 10;

		param.showaxis = true;

		return param;
	}

	public boolean equals(GraphParameters another) {
		if (minx != another.minx)
			return false;
		if (maxx != another.maxx)
			return false;
		if (miny != another.miny)
			return false;
		if (maxy != another.maxy)
			return false;

		if (showaxis != another.showaxis)
			return false;

		return true;
	}

}