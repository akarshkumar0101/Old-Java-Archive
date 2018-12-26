package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class ImageArray {
	private static final JFrame frame = new JFrame("frame");
	static {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
	}

	public static Image fractionArrayToImage(double[][] arr) {
		BufferedImage img = new BufferedImage(arr.length, arr[0].length, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < arr.length; x++) {
			for (int y = 0; y < arr[0].length; y++) {
				int col = 255 - (int) (arr[x][y] * 255);
				img.setRGB(x, y, new Color(col, col, col).getRGB());
			}
		}
		return img;
	}

	public static double[][] imageToFractionalArray(Image img) {
		double[][] arr = new double[img.getWidth(null)][img.getHeight(null)];
		for (int x = 0; x < arr.length; x++) {
			for (int y = 0; y < arr[x].length; y++) {
				int col = new Color(((BufferedImage) img).getRGB(x, y)).getRed();
				col = 255 - col;
				double val = (double) col / 255;
				arr[x][y] = val;
			}
		}
		return arr;
	}

	public static Image weightArrayToImage(double[][] arr) {
		BufferedImage img = new BufferedImage(arr.length, arr[0].length, BufferedImage.TYPE_INT_RGB);
		double maxabs = maxabs(arr);
		for (int x = 0; x < arr.length; x++) {
			for (int y = 0; y < arr[0].length; y++) {
				boolean pos = arr[x][y] > 0;
				int col = 255 - (int) (Math.abs(arr[x][y]) / maxabs * 255);
				// System.out.println((int) (Math.abs(arr[x][y]) / maxabs * 255) + " " +
				// arr[x][y] + " " + maxabs);
				img.setRGB(x, y, new Color(pos ? 0 : col, pos ? col : 0, 0).getRGB());
			}
		}
		return img;
	}

	public static double maxabs(double[][] arr) {
		double ans = 0;
		for (double[] col : arr) {
			for (double data : col) {

				if (Math.abs(data) > ans) {
					ans = Math.abs(data);
				}
			}
		}
		return ans;
	}

	public static double sum(double[][] arr) {
		double ans = 0;
		for (double[] col : arr) {
			for (double data : col) {
				ans += data;
			}
		}
		return ans;
	}

	public static double[] to1DArray(double[][] arr) {
		double[] result = new double[arr.length * arr[0].length];
		for (int x = 0; x < arr.length; x++) {
			for (int y = 0; y < arr[0].length; y++) {
				result[x * arr[0].length + y] = arr[x][y];
			}
		}

		return result;
	}

	public static double[][] to2DArray(double[] arr, int width, int height) {
		double[][] result = new double[width][height];
		for (int i = 0; i < arr.length; i++) {
			result[i / height][i % height] = arr[i];
		}
		return result;
	}

	public static void showImg(double[][] fractionalarr) {
		showImg(fractionArrayToImage(fractionalarr));
	}

	public static void showImg(Image img) {

		JComponent label = new JComponent() {
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				g.drawImage(img, 0, 0, getWidth(), getHeight(), 0, 0, img.getWidth(null), img.getHeight(null), null);
			}
		};
		label.setBorder(BorderFactory.createLineBorder(Color.red));
		frame.getContentPane().removeAll();
		frame.getContentPane().add(label);
		frame.setVisible(true);
	}

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}
