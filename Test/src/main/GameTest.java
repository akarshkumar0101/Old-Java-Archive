package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class GameTest {

	static double ax = 0, ay = 2000;
	static double vx = -500, vy = 0;
	static double x = 0, y = 0;
	static double rad = 50;
	static boolean inbounce = false;
	static double bullety = 0;

	static long prevtime = 0;
	static Thread gameThread = null;

	public static void main(String[] args) throws Exception {

		Image ballImage = new ImageIcon(
				new URL("http://orig00.deviantart.net/3c84/f/2011/151/7/a/shiny_glass_sphere_by_shadowthrust-d3hp8at.png"))
						.getImage();

		@SuppressWarnings("serial")
		JLabel label = new JLabel(
		// new ImageIcon(new
		// URL("https://backgroundss.files.wordpress.com/2013/06/sdcfvbghnmk.jpg"))
		) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g = g.create();
				g.setColor(Color.green);
				g.drawString("HELLO", 600, 600);
				g.drawImage(ballImage, (int) (x - rad), (int) (y - rad), (int) (rad * 2), (int) (rad * 2), null);
				g.drawOval((int) (x - rad), (int) (y - rad), (int) (rad * 2), (int) (rad * 2));
				/*
				 * if (x > getWidth() - rad && x < getWidth()) {
				 * g.drawImage(ballImage, (int) (x - rad) - getWidth(), (int) (y
				 * - rad), (int) (rad * 2), (int) (rad * 2), null); } else if (x
				 * < rad && x > 0) { g.drawImage(ballImage, (int) (x - rad) +
				 * getWidth(), (int) (y - rad), (int) (rad * 2), (int) (rad *
				 * 2), null); }
				 */

				// g.fillOval((int) (x - rad), (int) (y - rad), (int) (rad * 2),
				// (int) (rad * 2));
				// g.fillOval((int) (x - rad) - this.getWidth(), (int) (y -
				// rad), (int) (rad * 2), (int) (rad * 2));
				// g.fillOval((int) (x - rad) + this.getWidth(), (int) (y -
				// rad), (int) (rad * 2), (int) (rad * 2));

				g.drawLine(500, getHeight(), 500, getHeight() - (int) bullety);
				g.drawLine(499, 0, 499, (int) bullety);
			}
		};
		label.setBackground(Color.black);
		label.setOpaque(true);

		JFrame frame = new JFrame();
		frame.setSize(900, 900);
		frame.add(label);

		frame.addKeyListener(new KeyListener() {
			int pressed = 0;
			boolean paused = false;

			@Override
			public void keyTyped(KeyEvent e) {
				if (!paused && e.getKeyChar() == 'p') {
					try {
						// gameThread.wait();
						gameThread.suspend();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					paused = true;
					pressed = 0;
					ax = 0;
				} else if (paused && e.getKeyChar() == 'p') {
					prevtime = System.nanoTime();
					// gameThread.notify();
					gameThread.resume();
					paused = false;
				}
				if (!paused) {
					if (e.getKeyChar() == 'q' && rad > 1) {
						rad--;
					} else if (e.getKeyChar() == 'w') {
						rad++;
					}
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (paused)
					return;
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					System.out.println(vy);
					vy -= 300;
				}

				if (pressed != 0)
					return;
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					ax = 1000;
					pressed = KeyEvent.VK_RIGHT;
				}

				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					ax = -1000;
					pressed = KeyEvent.VK_LEFT;
				}

			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (paused)
					return;
				if (e.getKeyCode() == pressed) {
					ax = 0;
					pressed = 0;
				}

			}
		});

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setResizable(true);
		frame.setVisible(true);
		frame.setSize(900, 900);

		prevtime = System.nanoTime();
		ScheduledExecutorService service = Executors
				.newScheduledThreadPool(1 /* single-threaded executor service */, new ThreadFactory() {
					@Override
					public Thread newThread(Runnable runnable) {
						Thread thread = new Thread(runnable, "Game thread");
						gameThread = thread;
						return thread;
					}
				});
		service.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				long current = System.nanoTime();
				double dif = (double) (current - prevtime) / 1000000000L;

				bullety += 50 * dif;

				vx += ax * dif;
				x += vx * dif;

				// if(y!= label.getHeight()-rad)
				vy += ay * dif;
				y += vy * dif;

				if (x > label.getWidth()) {
					x = 0;
				}
				if (x < 0) {
					x = label.getWidth();
				}

				if (y + rad > label.getHeight()) {
					y = label.getHeight() - rad;
					if (!inbounce) {
						inbounce = true;
						vy *= -1.0;
					}
				}
				if (inbounce && y + rad < label.getHeight()) {
					inbounce = false;
				}

				label.repaint();

				prevtime = System.nanoTime();

			}

		}, 0, 20, TimeUnit.MILLISECONDS);

	}

}
