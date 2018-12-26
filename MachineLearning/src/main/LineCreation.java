package main;

public class LineCreation {
	public static double[][] createLineImage(int width, int height, int numVert, int numHor) {
		double[][] result = new double[width][height];
		for (int i = 0; i < numVert; i++) {
			double[][] arr = new double[width / numVert][height];
			arr = createVerticalLineImg(arr);
			result = add(arr, result, (int) ((double) i * width / numVert), 0);
		}
		for (int i = 0; i < numHor; i++) {
			double[][] arr = new double[width][height / numHor];
			arr = createHorizontalLineImg(arr);
			result = add(arr, result, 0, (int) ((double) i * height / numHor));
		}
		return result;
	}

	private static double[][] createVerticalLineImg(double[][] img) {
		try {
			int width = img.length, height = img[0].length;

			int starty = (int) (Math.random() * height / 3);
			int endy = (int) (height * 2 / 3 + Math.random() * height / 3);

			int x = width / 3 + (int) (Math.random() * width / 3);
			double leftpercent = .1, rightpercent = .1;
			for (int y = starty; y <= endy; y++) {
				int pensize = 1 + (int) (Math.random() * width / 8);
				for (int dx = 0; dx < pensize; dx++) {
					double maxpen = 1 / Math.pow((dx + 1), 2);
					img[x + dx][y] = Math.max(maxpen, img[x + dx][y]);
					img[x - dx][y] = Math.max(maxpen, img[x - dx][y]);
				}
				if (x > pensize + 1 && Math.random() < leftpercent) {
					x--;
					leftpercent = .05;
					rightpercent = .05;
					// rightpercent = .333;
				} else if (x < width - pensize - 1 && Math.random() < rightpercent) {
					x++;
					leftpercent = .05;
					rightpercent = .05;
					// leftpercent = .333;
				} else {
					rightpercent = .333;
					leftpercent = .333;
				}

			}
			return img;
		} catch (Exception e) {
			return createVerticalLineImg(img);
		}
	}

	private static double[][] createHorizontalLineImg(double[][] img) {
		try {
			int width = img.length, height = img[0].length;

			int startx = (int) (Math.random() * width / 3);
			int endx = (int) (width * 2 / 3 + Math.random() * width / 3);

			int y = height / 3 + (int) (Math.random() * height / 3);
			double leftpercent = .1, rightpercent = .1;
			for (int x = startx; x <= endx; x++) {
				int pensize = 1 + (int) (Math.random() * height / 8);
				for (int dy = 0; dy < pensize; dy++) {
					double maxpen = 1 / Math.pow((dy + 1), 2);
					img[x][y + dy] = Math.max(maxpen, img[x][y + dy]);
					img[x][y - dy] = Math.max(maxpen, img[x][y - dy]);
				}
				if (y > pensize + 1 && Math.random() < leftpercent) {
					y--;
					leftpercent = .05;
					rightpercent = .05;
					// rightpercent = .333;
				} else if (y < height - pensize - 1 && Math.random() < rightpercent) {
					y++;
					leftpercent = .05;
					rightpercent = .05;
					// leftpercent = .333;
				} else {
					rightpercent = .333;
					leftpercent = .333;
				}

			}
			return img;
		} catch (Exception e) {
			return createVerticalLineImg(img);
		}
	}

	public static double[][] add(double[][] smallarr, double[][] bigarr, int offsetx, int offsety) {
		for (int i = 0; i < smallarr.length; i++) {
			for (int j = 0; j < smallarr[0].length; j++) {
				bigarr[i + offsetx][j + offsety] = Math.max(bigarr[i + offsetx][j + offsety], smallarr[i][j]);
			}
		}
		return bigarr;
	}

	public static double[] getNeuralNetExpectedOutcome(int numVert, int numHor) {
		double[] expectedout = new double[8];
		expectedout[numVert] = 1;
		expectedout[numHor + 4] = 1;
		return expectedout;
	}

}
