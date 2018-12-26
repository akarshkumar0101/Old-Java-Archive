package main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {

	public static double rad = .1;
	public static double vel = 0;
	public static double acc = 0;
	// moment of inertia
	public static final double MOI = 1;
	public static double torque = 0;

	public static final double RADIUS = 10;

	public static final PointCharge[] motorCharges = new PointCharge[4];
	public static final List<PointCharge> groundCharges = new ArrayList<>();

	public static JFrame frame = new JFrame();
	public static JPanel panel = new JPanel() {
		private static final long serialVersionUID = 8499764229216881906L;

		static final double minx = -15, maxx = 15, miny = -15, maxy = 15;

		int centerx = 0, centery = 0;

		@Override
		public void paintComponent(Graphics g) {
			int width = getWidth(), height = getHeight();
			centerx = (int) getScaledNumber(minx, maxx, 0, width, 0);
			centery = (int) getScaledNumber(miny, maxy, height, 0, 0);

			int paintradx = (int) (getScaledNumber(minx, maxx, 0, width, RADIUS) - centerx);
			int paintrady = (int) (getScaledNumber(miny, maxy, height, 0, RADIUS) - centery);

			g.setColor(Color.darkGray);
			g.drawOval(centerx - paintradx, centery - paintrady, paintradx * 2, paintrady * 2);

			int chargePaintrad = 25;
			for (PointCharge q : motorCharges) {
				int x = (int) getScaledNumber(minx, maxx, 0, width, q.coor.x);
				int y = (int) getScaledNumber(miny, maxy, height, 0, q.coor.y);

				g.setColor(Color.darkGray);

				g.setColor(q.charge > 0 ? Color.gray : Color.darkGray);
				g.fillOval(x - chargePaintrad, y - chargePaintrad, chargePaintrad * 2, chargePaintrad * 2);

				for (PointCharge gq : groundCharges) {
					Vector force = q.forceFrom(gq);
					g.setColor(Color.red);
					drawVector(x, y, force, g);

					Vector radvect = q.coor;
					Vector normalvect = radvect.rotate(Math.PI / 2);

					Vector torqueVector = force.projectionOn(normalvect);
					int dir = force.crossDir(radvect);

					g.setColor(Color.blue);
					drawVector(x, y, torqueVector, g);
				}
			}

			for (PointCharge q : groundCharges) {
				int x = (int) getScaledNumber(minx, maxx, 0, width, q.coor.x);
				int y = (int) getScaledNumber(miny, maxy, height, 0, q.coor.y);

				g.setColor(q.charge > 0 ? Color.gray : Color.darkGray);
				g.fillOval(x - chargePaintrad, y - chargePaintrad, chargePaintrad * 2, chargePaintrad * 2);
			}

		}

		// orivect is in minx-maxx coordinate system
		public void drawVector(int x, int y, Vector orivect, Graphics g) {
			int vectx = (int) getScaledNumber(0, maxx, 0, centerx, orivect.x);
			int vecty = (int) getScaledNumber(0, maxy, 0, centery, orivect.y);
			g.drawLine(x, y, x + vectx, y - vecty);
		}
	};

	public static void main(String[] args) {
		double motorChargeMag = 1;
		motorCharges[0] = new PointCharge(new Vector(), motorChargeMag);
		motorCharges[1] = new PointCharge(new Vector(), motorChargeMag);
		motorCharges[2] = new PointCharge(new Vector(), motorChargeMag);
		motorCharges[3] = new PointCharge(new Vector(), motorChargeMag);

		double groundChargeMag = -1;
		// placeGroundCharge(Math.PI / 3, groundChargeMag);
		placeGroundCharge(Math.PI / 3, groundChargeMag);
		placeGroundCharge(5 * Math.PI / 4, groundChargeMag);

		// groundCharges.add(new PointCharge(new Vector(10, 9), -50));
		// groundCharges.add(new PointCharge(new Vector(5, 11),
		// -groundChargeMag));
		// groundCharges.add(new PointCharge(new Vector(6, 10),
		// groundChargeMag));

		updateMotorChargeLocations();

		frame.setContentPane(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(900, 900);
		frame.setVisible(true);

		long prevNanoTime = System.nanoTime();
		while (true) {
			long nanoTime = System.nanoTime();
			double dt = (nanoTime - prevNanoTime) / Math.pow(10, 9);
			// System.out.println(dt);
			double torque = determineTorque();
			acc = torque / MOI;
			vel += acc * dt;
			rad += vel * dt;

			updateMotorChargeLocations();

			prevNanoTime = nanoTime;
			frame.repaint();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			rad = rad % (Math.PI * 2);
		}
	}

	public static void placeGroundCharge(double angle, double charge) {
		groundCharges.add(new PointCharge(new Vector(Math.cos(angle), Math.sin(angle)).scale(RADIUS + 1), charge));
	}

	public static double getScaledNumber(double ori1, double ori2, double scaleto1, double scaleto2, double number) {
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

	public static double determineTorque() {
		double torque = 0;
		for (PointCharge mq : motorCharges) {
			for (PointCharge gq : groundCharges) {
				Vector force = mq.forceFrom(gq);
				Vector radvect = mq.coor;
				Vector normalvect = radvect.rotate(Math.PI / 2);
				Vector torqueVector = force.projectionOn(normalvect);
				int dir = force.crossDir(radvect);
				double torquei = torqueVector.mag();
				if (dir < 0) {
					torque += torquei;
				} else if (dir > 0) {
					torque -= torquei;
				}
			}
		}
		// return torque;
		// return 100 * (Math.random() * 2 - 1);
		return torque;
	}

	public static void updateMotorChargeLocations() {
		double theta = rad;
		motorCharges[0].coor.set(RADIUS * Math.cos(theta), RADIUS * Math.sin(theta));
		theta += Math.PI / 2;
		motorCharges[1].coor.set(RADIUS * Math.cos(theta), RADIUS * Math.sin(theta));
		theta += Math.PI / 2;
		motorCharges[2].coor.set(RADIUS * Math.cos(theta), RADIUS * Math.sin(theta));
		theta += Math.PI / 2;
		motorCharges[3].coor.set(RADIUS * Math.cos(theta), RADIUS * Math.sin(theta));
	}
}

class PointCharge {
	public static final double K = 100;

	public Vector coor;
	public double charge;

	public PointCharge(Vector coor, double charge) {
		this.coor = coor;
		this.charge = charge;
	}

	public Vector forceFrom(PointCharge b) {
		Vector a_b = b.coor.sub(coor);
		double dist = a_b.mag();
		Vector axis = a_b.unitVector();
		Vector force = axis.scale(-K * charge * b.charge / dist / dist);
		return force;
	}
}

class Vector {

	public double x, y;

	public Vector() {
		this(0, 0);
	}

	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void set(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double mag() {
		return Math.sqrt(x * x + y * y);
	}

	public double angle() {
		return Math.atan2(y, x);
	}

	public double dist(Vector coor) {
		return sub(coor).mag();
	}

	public double angleBetween(Vector another) {
		return another.angle() - angle();
	}

	public Vector rotate(double ang) {
		double mag = mag();
		double angle = angle();
		double newangle = angle + ang;
		return new Vector(Math.cos(newangle), Math.sin(newangle)).scale(mag);
	}

	public Vector projectionOn(Vector another) {
		double angle = angleBetween(another);
		double mag = mag();
		double newmag = mag * Math.cos(angle);
		return another.unitVector().scale(newmag);
	}

	public Vector unitVector() {
		return scale(1 / mag());
	}

	public Vector scale(double factor) {
		return new Vector(x * factor, y * factor);
	}

	public Vector add(Vector a) {
		return new Vector(x + a.x, y + a.y);
	}

	public Vector sub(Vector a) {
		return new Vector(x - a.x, y - a.y);
	}

	public double dot(Vector a) {
		return x * a.x + y * a.y;
	}

	public double crossMag(Vector a) {
		return x * a.y - y * a.x;
	}

	// 1 or -1
	public int crossDir(Vector a) {
		double mag = crossMag(a);
		if (mag > 0)
			return 1;
		else if (mag < 0)
			return -1;
		else
			return 0;

	}
}