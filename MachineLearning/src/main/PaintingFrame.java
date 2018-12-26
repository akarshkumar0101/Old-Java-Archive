package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class PaintingFrame extends JFrame {

	private static final long serialVersionUID = -6633876268899063418L;

	public Image img;

	private final Painter painter;
	private final JButton button;

	public PaintingFrame(int width, int height, ActionListener listener) {
		super("painting frame");
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < img.getWidth(null); x++) {
			for (int y = 0; y < img.getHeight(null); y++) {
				((BufferedImage) img).setRGB(x, y, new Color(255, 255, 255).getRGB());
			}
		}

		painter = new Painter();
		button = new JButton("Press when done");
		button.addActionListener(listener);

		getContentPane().setLayout(new GridLayout(1, 2));
		getContentPane().add(painter);
		getContentPane().add(button);

		this.setSize(1800, 900);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public Image getImage() {
		return img;
	}

	class Painter extends JComponent implements MouseListener, MouseMotionListener {

		private boolean mousePressed = false;
		private static final long serialVersionUID = -7622560859917119510L;

		public Painter() {
			super();
			setPreferredSize(new Dimension(900, 900));
			setBorder(BorderFactory.createLineBorder(Color.RED));
			addMouseListener(this);
			addMouseMotionListener(this);
		}

		@Override
		public void paintComponent(Graphics g) {
			g.drawImage(img, 0, 0, getWidth(), getHeight(), 0, 0, img.getWidth(null), img.getHeight(null), null);
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			mark(e.getX(), e.getY());
		}

		@Override
		public void mouseMoved(MouseEvent e) {
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
			mousePressed = true;
			mark(e.getX(), e.getY());
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			mousePressed = false;
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		public void mark(int x, int y) {
			double perx = (double) x / getWidth();
			double pery = (double) y / getHeight();
			int imgx = (int) (img.getWidth(null) * perx);
			int imgy = (int) (img.getHeight(null) * pery);
			try {
				((BufferedImage) img).setRGB(imgx, imgy, new Color(0, 0, 0).getRGB());
				repaint();
			} catch (ArrayIndexOutOfBoundsException e) {

			}
		}
	}
}
