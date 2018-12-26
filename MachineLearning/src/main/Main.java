package main;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import data.tuple.Tuple2D;
import machinelearning.neuralnet.NeuralNetwork;
import machinelearning.neuralnet.NeuralNetworkTrainer;
import machinelearning.neuralnet.TrackableNeuralNetwork;
import machinelearning.neuralnet.VisualNetwork;

public class Main {

	public static TrackableNeuralNetwork network = new TrackableNeuralNetwork(784, 16, 16, 8);
	// static final NeuralNetwork network = new NeuralNetwork(2, 2, 1);

	public static VisualNetwork visual;
	public static PaintingFrame painting;

	public static void main(String[] args) {

		network = new TrackableNeuralNetwork(31, 16, 10, 1);
		network.setTrainer(Variables.trainingSin);

		visual = new VisualNetwork(network);
		visual.setVisible(true);

		// double[] inp = ImageArray.to1DArray(LineCreation.createLineImage(28, 28, 3,
		// 2));
		// network.feed(inp);
		// network.calculateFully();
		// double[] out = network.getOutput();
		// System.out.println(Util.toString(out));

		// showWeightPictures(network);

		// network.randomizeWeightsAndBiases(-2, 2, -2, 2);

		// showGeneratedLines();

		network.randomizeWeightsAndBiases();
		network.trainWithBatches(10000, 1000);

		painting = new PaintingFrame(28, 28, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int[] ans = Main.getResult(painting.getImage());
				System.out.println(ans[0] + " vertical lines, " + ans[1] + " horizontal lines");
			}
		});
		// painting.setVisible(true);

		// showGeneratedLines();
		// test();

		testPrimes();
	}

	public static int[] getResult(Image img) {
		double[] arr1D = ImageArray.to1DArray(ImageArray.imageToFractionalArray(img));
		network.feed(arr1D);
		network.calculateFully();
		double[] out = network.getOutput();

		double maxVert = Double.MIN_VALUE, maxHor = Double.MIN_VALUE;
		int indVert = -1, indHor = -1;
		for (int i = 0; i < 4; i++) {
			if (out[i] > maxVert) {
				maxVert = out[i];
				indVert = i;
			}
			if (out[i + 4] > maxHor) {
				maxHor = out[i + 4];
				indHor = i + 4;
			}
		}
		for (double d : out) {
			// System.out.println(d);
		}
		// System.out.println();
		return new int[] { indVert, indHor - 4 };
	}

	public static void redraw() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		visual.repaint();
	}

	public static void test() {
		double[][][] examples = new double[1000][][];
		for (int j = 0; j < 1000; j++) {
			int numVert = (int) (Math.random() * 4), numHor = (int) (Math.random() * 4);
			double[][] binimg = LineCreation.createLineImage(28, 28, numVert, numHor);
			double[] binimg1d = ImageArray.to1DArray(binimg);
			ImageArray.showImg(binimg);

			network.feed(binimg1d);
			network.calculateFully();
			double[] out = network.getOutput();

			examples[j] = new double[][] { binimg1d, LineCreation.getNeuralNetExpectedOutcome(numVert, numHor) };

			Object[] performance = network.calculatePerformance(examples[j]);

			double maxVert = Double.MIN_VALUE, maxHor = Double.MIN_VALUE;
			int indVert = -1, indHor = -1;
			for (int i = 0; i < 4; i++) {
				if (out[i] > maxVert) {
					maxVert = out[i];
					indVert = i;
				}
				if (out[i + 4] > maxHor) {
					maxHor = out[i + 4];
					indHor = i + 4;
				}
			}
			indHor -= 4;

			System.out.println(indVert + " vertical lines, " + indHor + " horizontal lines");
			System.out.println("it: " + j + ", cost: " + performance[0] + ", result: " + performance[1]);
			System.out.println();

			try {
				Thread.sleep((boolean) performance[1] ? 10 : 10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		double[] performance = network.calculateAveragePerformance(examples);
		System.out.println("cost: " + performance[0] + ", accuracy: " + performance[1]);
	}

	public static void showGeneratedLines() {
		for (int i = 0; i < 100; i++) {
			double[][] binimg = LineCreation.createLineImage(28, 28, (int) (Math.random() * 4),
					(int) (Math.random() * 4));
			double[] binimg1d = ImageArray.to1DArray(binimg);
			ImageArray.showImg(binimg);

			network.feed(binimg1d);
			network.calculateFully();
			network.getOutput();

			// System.out.println(network);
			for (double d : network.getOutput()) {
				// System.out.println(d);
			}
			// System.out.println();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void testEvenOdd() {
		double[] per = network.calculateAveragePerformance(network.getTrainer().getRandomTrainingExamples(10000));
		System.out.println("Average cost: " + per[0]);
		System.out.println("Average accuracy: " + per[1]);
	}

	public static void testPrimes() {
		double[] per = network.calculateAveragePerformance(network.getTrainer().getRandomTrainingExamples(10000));
		System.out.println("Average cost: " + per[0]);
		System.out.println("Average accuracy: " + per[1]);
	}

	public static void showWeightPictures(NeuralNetwork network) {
		for (int x = 0; x < 16; x++) {
			double[] weightimg1d = network.weights[0][x];
			double[][] weightimg = ImageArray.to2DArray(weightimg1d, 28, 28);

			ImageArray.showImg(ImageArray.weightArrayToImage(weightimg));
			// System.out.println(ImageArray.maxabs(weightimg));
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}

class Variables {
	static final NeuralNetworkTrainer trainingLines = new NeuralNetworkTrainer() {
		@Override
		public double[][] getRandomTrainingExample() {
			int numVert = (int) (Math.random() * 4), numHor = (int) (Math.random() * 4);
			double[][] binimg = LineCreation.createLineImage(28, 28, numVert, numHor);
			double[] binimg1d = ImageArray.to1DArray(binimg);
			double[] expectedOutput = LineCreation.getNeuralNetExpectedOutcome(numVert, numHor);
			return new double[][] { binimg1d, expectedOutput };
		}

		@Override
		public boolean isCorrect(double[] output, double[] expectedOutput) {
			double highVertOut = Double.MIN_VALUE, highVertExp = Double.MIN_VALUE, highHorOut = Double.MIN_VALUE,
					highHorExp = Double.MIN_VALUE;
			int indVertOut = -1, indVertExp = -1, indHorOut = -1, indHorExp = -1;
			for (int i = 0; i < 4; i++) {
				if (output[i] > highVertOut) {
					highVertOut = output[i];
					indVertOut = i;
				}
				if (expectedOutput[i] > highVertExp) {
					highVertExp = output[i];
					indVertExp = i;
				}
				if (output[i + 4] > highHorOut) {
					highHorOut = output[i + 4];
					indHorOut = i + 4;
				}
				if (expectedOutput[i + 4] > highHorExp) {
					highHorExp = output[i + 4];
					indHorExp = i + 4;
				}
			}
			boolean isCorrect = indVertOut == indVertExp && indHorOut == indHorExp;
			return isCorrect;
		}

		@Override
		public double getWeightLearningRate() {
			return 10;
		}

		@Override
		public double[] rawToInputLayer(Object d) {
			return null;
		}

		@Override
		public double[] rawToOutputLayer(Object a) {
			return null;
		}

		@Override
		public Tuple2D getRandomRawData() {
			return null;
		}

	};
	static final NeuralNetworkTrainer trainingAnd = new NeuralNetworkTrainer() {
		@Override
		public double[][] getRandomTrainingExample() {
			int rand1 = (int) (Math.random() * 2), rand2 = (int) (Math.random() * 2);
			// int rand1 = 1, rand2 = 1;
			int output = (rand1 == 1 && rand2 == 1) ? 1 : 0;
			return new double[][] { { rand1, rand2 }, { output } };
		}

		@Override
		public boolean isCorrect(double[] output, double[] expectedOutput) {
			return ((int) Math.floor(output[0] + .5)) == ((int) Math.floor(expectedOutput[0] + .5));
		}

		@Override
		public double getWeightLearningRate() {
			return 10;
		}

		@Override
		public double[] rawToInputLayer(Object d) {
			return null;
		}

		@Override
		public double[] rawToOutputLayer(Object a) {
			return null;
		}

		@Override
		public Tuple2D getRandomRawData() {
			return null;
		}

	};
	static final NeuralNetworkTrainer trainingEvenOdd = new NeuralNetworkTrainer() {
		@Override
		public double[][] getRandomTrainingExample() {
			int random = (int) (Math.random() * Integer.MAX_VALUE);
			String str = Integer.toBinaryString(random);
			double[] inp = new double[31];
			for (int i = 0; i < str.length(); i++) {
				if (str.charAt(str.length() - 1 - i) == '1') {
					inp[i] = 1;
				}
			}
			int output = (random % 2 == 0) ? 1 : 0;
			return new double[][] { inp, { output } };
		}

		@Override
		public boolean isCorrect(double[] output, double[] expectedOutput) {
			return ((int) Math.floor(output[0] + .5)) == ((int) Math.floor(expectedOutput[0] + .5));
		}

		@Override
		public double getWeightLearningRate() {
			return 1;
		}

		@Override
		public double[] rawToInputLayer(Object d) {
			return null;
		}

		@Override
		public double[] rawToOutputLayer(Object a) {
			return null;
		}

		@Override
		public Tuple2D getRandomRawData() {
			return null;
		}

	};
	static final NeuralNetworkTrainer trainingPrimes = new NeuralNetworkTrainer() {
		@Override
		public double[][] getRandomTrainingExample() {
			int random = (int) (Math.random() * Integer.MAX_VALUE / 4);
			String str = Integer.toBinaryString(random);
			double[] inp = new double[31];
			for (int i = 0; i < str.length(); i++) {
				if (str.charAt(str.length() - 1 - i) == '1') {
					inp[i] = 1;
				}
			}
			int output = (isPrime(random)) ? 1 : 0;
			return new double[][] { inp, { output } };
		}

		public boolean isPrime(int inp) {
			for (int i = 2; i <= Math.sqrt(inp); i++) {
				if (inp % i == 0)
					return false;
			}
			return true;
		}

		@Override
		public boolean isCorrect(double[] output, double[] expectedOutput) {
			return ((int) Math.floor(output[0] + .5)) == ((int) Math.floor(expectedOutput[0] + .5));
		}

		@Override
		public double getWeightLearningRate() {
			return 1;
		}

		@Override
		public double[] rawToInputLayer(Object d) {
			return null;
		}

		@Override
		public double[] rawToOutputLayer(Object a) {
			return null;
		}

		@Override
		public Tuple2D getRandomRawData() {
			return null;
		}

	};
	static final NeuralNetworkTrainer trainingSin = new NeuralNetworkTrainer() {
		@Override
		public double[][] getRandomTrainingExample() {
			int random = (int) (Math.random() * Integer.MAX_VALUE / 4);
			String str = Integer.toBinaryString(random);
			double[] inp = new double[31];
			for (int i = 0; i < str.length(); i++) {
				if (str.charAt(str.length() - 1 - i) == '1') {
					inp[i] = 1;
				}
			}
			double output = Math.sin(random);
			return new double[][] { inp, { output } };
		}

		@Override
		public boolean isCorrect(double[] output, double[] expectedOutput) {
			double error = (output[0] - expectedOutput[0]) / expectedOutput[0];
			return Math.abs(error) < 0.02;
		}

		@Override
		public double getWeightLearningRate() {
			return 1;
		}

		@Override
		public double[] rawToInputLayer(Object d) {
			return null;
		}

		@Override
		public double[] rawToOutputLayer(Object a) {
			return null;
		}

		@Override
		public Tuple2D getRandomRawData() {
			return null;
		}

	};
}
