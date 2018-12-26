package main;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import data.tuple.Tuple2D;
import machinelearning.neuralnet.NeuralNetworkTrainer;
import machinelearning.neuralnet.TrackableNeuralNetwork;
import machinelearning.neuralnet.VisualNetwork;
import plot.ScatterPlotDisplay;
import ui.FrameWrapper;
import ui.GraphPanel;
import ui.UIPointSet;

public class DigitRecognition {

	public static final File trainImagesfile = new File("mnist/mnist_train.csv");
	public static final File networkFile = new File("neural net data/digits.nn");

	public static TrackableNeuralNetwork network;

	public static PaintingFrame painter;

	public static List<double[]> points = new ArrayList<double[]>();
	public static UIPointSet pointSet = new UIPointSet(new double[0][]);

	public static FrameWrapper<GraphPanel> graphFrame;

	public static ScatterPlotDisplay scatterPlotDisplay;
	public static DigitTrainer trainer = new DigitTrainer();

	public static void main(String[] args) throws IOException {
		// network = new TrackableNeuralNetwork(Serialization.loadObject(networkFile));
		network = new TrackableNeuralNetwork(784, 16, 16, 10);
		network.setTrainer(trainer);
		network.randomizeWeightsAndBiases();

		VisualNetwork visual = new VisualNetwork(network);
		visual.setVisible(true);

		graphFrame = new FrameWrapper<>("graph", 900, 900, true, true);

		graphFrame.setComponent(new GraphPanel(graphFrame));

		graphFrame.getComponent().pointSets.add(pointSet);

		pointSet.setPointColor(Color.green);

		double[][][] testingExamples = DigitImageLoadingService.loadDigitImages(DigitImageLoadingService.testImagesfile,
				DigitImageLoadingService.testLabelsfile);
		for (double[][] example : testingExamples) {
			int label = (int) example[1][0];
			example[1] = new double[10];
			example[1][label] = 1;
		}

		double[] performance = network.calculateAveragePerformance(testingExamples);
		System.out.println(performance[0]);
		System.out.println(performance[1]);

		network.trainWithBatches(10000, 100);

	}

}

class DigitTrainer implements NeuralNetworkTrainer<double[], Integer> {
	private double[][][] trainingData;
	private int currentIndex = 0;

	public DigitTrainer() {
		try {
			trainingData = DigitImageLoadingService.loadDigitImages(DigitImageLoadingService.trainImagesfile,
					DigitImageLoadingService.trainLabelsfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isCorrect(double[] output, double[] expectedOutput) {
		for (int i = 0; i < output.length; i++) {
			if (((int) Math.floor(output[i] + .5)) != ((int) Math.floor(expectedOutput[i] + .5)))
				return false;
		}
		return true;
	}

	@Override
	public Tuple2D<double[], Integer> getRandomRawData() {
		double[][] data = trainingData[currentIndex++];
		if (currentIndex >= trainingData.length) {
			currentIndex = 0;
		}
		double[] d = data[0];
		int num = (int) data[1][0];
		return new Tuple2D<double[], Integer>(d, num);
	}

	@Override
	public double getWeightLearningRate() {
		return 10;
	}

	@Override
	public double[] rawToInputLayer(double[] d) {
		ImageArray.showImg(ImageArray.to2DArray(d, 28, 28));
		return d;
	}

	@Override
	public double[] rawToOutputLayer(Integer a) {
		double[] expectedOutput = new double[10];
		expectedOutput[a] = 1;
		return expectedOutput;
	}

	@Override
	public int getN() {
		return 10;
	}

	int it = 0;

	@Override
	public void runEveryNIterations(double[] beforePerformance, double[] afterPerformance) {
		DigitRecognition.points.add(new double[] { it / 10, afterPerformance[0] });
		// DigitRecognition.points.add(new double[] { it / 10, afterPerformance[1] });

		DigitRecognition.pointSet.setPoints(DigitRecognition.points.toArray(new double[1][1]));
		DigitRecognition.graphFrame.repaint();

		System.out.println("Cost: " + afterPerformance[0]);
		System.out.println("Accuracy: " + afterPerformance[1]);
		System.out.println();

		if (it % 100 == 0) {

		}

		it += getN();
	}

}

class DigitImageLoadingService {
	public static final File trainLabelsfile = new File("mnist/train-labels.idx1-ubyte");
	public static final File trainImagesfile = new File("mnist/train-images.idx3-ubyte");

	public static final File testLabelsfile = new File("mnist/t10k-labels.idx1-ubyte");
	public static final File testImagesfile = new File("mnist/t10k-images.idx3-ubyte");

	/**
	 * the following constants are defined as per the values described at
	 * http://yann.lecun.com/exdb/mnist/
	 **/

	// private static final int MAGIC_OFFSET = 0;
	private static final int OFFSET_SIZE = 4; // in bytes

	private static final int LABEL_MAGIC = 2049;
	private static final int IMAGE_MAGIC = 2051;

	private static final int NUMBER_ITEMS_OFFSET = 4;
	private static final int ITEMS_SIZE = 4;

	private static final int NUMBER_OF_ROWS_OFFSET = 8;
	private static final int ROWS_SIZE = 4;
	public static final int ROWS = 28;

	private static final int NUMBER_OF_COLUMNS_OFFSET = 12;
	private static final int COLUMNS_SIZE = 4;
	public static final int COLUMNS = 28;

	private static final int IMAGE_OFFSET = 16;
	private static final int IMAGE_SIZE = ROWS * COLUMNS;

	public static double[][][] loadDigitImages(File imagefile, File labelFile) throws IOException {
		ByteArrayOutputStream labelBuffer = new ByteArrayOutputStream();
		ByteArrayOutputStream imageBuffer = new ByteArrayOutputStream();

		InputStream labelInputStream = new FileInputStream(labelFile);
		InputStream imageInputStream = new FileInputStream(imagefile);

		int read;
		byte[] buffer = new byte[16384];

		while ((read = labelInputStream.read(buffer, 0, buffer.length)) != -1) {
			labelBuffer.write(buffer, 0, read);
		}

		labelBuffer.flush();

		while ((read = imageInputStream.read(buffer, 0, buffer.length)) != -1) {
			imageBuffer.write(buffer, 0, read);
		}

		imageBuffer.flush();

		byte[] labelBytes = labelBuffer.toByteArray();
		byte[] imageBytes = imageBuffer.toByteArray();

		byte[] labelMagic = Arrays.copyOfRange(labelBytes, 0, OFFSET_SIZE);
		byte[] imageMagic = Arrays.copyOfRange(imageBytes, 0, OFFSET_SIZE);

		labelInputStream.close();
		imageInputStream.close();

		if (ByteBuffer.wrap(labelMagic).getInt() != LABEL_MAGIC)
			throw new IOException("Bad magic number in label file!");

		if (ByteBuffer.wrap(imageMagic).getInt() != IMAGE_MAGIC)
			throw new IOException("Bad magic number in image file!");

		int numberOfLabels = ByteBuffer
				.wrap(Arrays.copyOfRange(labelBytes, NUMBER_ITEMS_OFFSET, NUMBER_ITEMS_OFFSET + ITEMS_SIZE)).getInt();
		int numberOfImages = ByteBuffer
				.wrap(Arrays.copyOfRange(imageBytes, NUMBER_ITEMS_OFFSET, NUMBER_ITEMS_OFFSET + ITEMS_SIZE)).getInt();

		if (numberOfImages != numberOfLabels)
			throw new IOException("The number of labels and images do not match!");

		int numRows = ByteBuffer
				.wrap(Arrays.copyOfRange(imageBytes, NUMBER_OF_ROWS_OFFSET, NUMBER_OF_ROWS_OFFSET + ROWS_SIZE))
				.getInt();
		int numCols = ByteBuffer
				.wrap(Arrays.copyOfRange(imageBytes, NUMBER_OF_COLUMNS_OFFSET, NUMBER_OF_COLUMNS_OFFSET + COLUMNS_SIZE))
				.getInt();

		if (numRows != ROWS && numRows != COLUMNS)
			throw new IOException("Bad image. Rows and columns do not equal " + ROWS + "x" + COLUMNS);

		double[][][] data = new double[numberOfLabels][][];
		for (int i = 0; i < numberOfLabels; i++) {
			int label = labelBytes[OFFSET_SIZE + ITEMS_SIZE + i];
			byte[] imageData = Arrays.copyOfRange(imageBytes, (i * IMAGE_SIZE) + IMAGE_OFFSET,
					(i * IMAGE_SIZE) + IMAGE_OFFSET + IMAGE_SIZE);
			double[] img = getImageFrom(imageData);
			data[i] = new double[][] { img, { label } };
		}

		return data;

	}

	private static double[] getImageFrom(byte[] imageData) {
		double[] fimgData = new double[imageData.length];
		for (int y = 0; y < 28; y++) {
			for (int x = 0; x < 28; x++) {
				byte data = imageData[28 * y + x];
				int d = data < 0 ? (256 + data) : data;
				fimgData[28 * x + y] = (double) d / 255;
			}
		}

		return fimgData;
	}
}
