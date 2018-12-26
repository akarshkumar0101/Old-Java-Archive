import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class NeuralNetwork {

	public final int[] networkDimensions;

	// network[layer][nodeid]
	public double[][] network;

	// network before the sigmoid activation function
	// network_Z[layer][nodeid]
	public double[][] network_Z;

	// weights between layer1 and layer2
	// weights[layer1][node2id][node1id]
	public double[][][] weights;

	// biases at layer
	// biases[layer][nodeid]
	public double[][] biases;

	private Training training;

	public NeuralNetwork(int... nodesperlayer) {
		this(nodesperlayer, null, null);
	}

	public NeuralNetwork(int[] nodesPerLayer, double[][][] weights, double[][] biases) {
		networkDimensions = nodesPerLayer.clone();

		if (weights == null) {
			this.weights = Util.deepCopy(weights);
		} else {

		}
		if (biases == null) {
			this.biases = Util.deepCopy(biases);
		}

		int layers = networkDimensions.length;
		network = new double[layers][];
		weights = new double[layers - 1][][];
		biases = new double[layers][];
		for (int l = 0; l < layers; l++) {
			int nodes = nodesperlayer[l];

			network[l] = new double[nodes];
			network_Z[l] = new double[nodes];
			for (int i = 0; i < nodes; i++) {
				network[l][i] = 0;
				network_Z[l][i] = 0;
			}

			if (l != layers - 1) {
				int numnodesnextlayer = nodesperlayer[l + 1];
				weights[l] = new double[numnodesnextlayer][nodes];
				for (int i = 0; i < nodes; i++) {
					for (int inext = 0; inext < numnodesnextlayer; inext++) {
						weights[l][inext][i] = 0.0;
					}
				}
			}
			biases[l] = new double[nodes];
			for (int i = 0; i < nodes; i++) {
				biases[l][i] = 0.0;
				if (l == 0) {
					biases[l][i] = Double.NaN;
				}
			}
		}
		nodePartials = Util.matrixScale(network, 0);
		beforeSigmoidPartials = Util.matrixScale(network_Z, 0);
		weightsPartials = Util.matrixScale(weights, 0);
		biasesPartials = Util.matrixScale(biases, 0);

	}

	public void feed(double... inputLayerData) {
		if (inputLayerData.length != network[0].length)
			throw new IllegalArgumentException("Must feed input layer with correct size");
		network[0] = inputLayerData;
		network_Z[0] = Util.toSingleArray(Util.sigmoid(Util.toDoubleArray(network[0])));
	}

	public double[] getOutput() {
		double[] output = network[network.length - 1];
		return output;
	}

	public void randomizeWeightsAndBiases(double lowlw, double uplw, double lowlb, double uplb) {
		for (double[][] weightarr : weights) {
			for (int x = 0; x < weightarr.length; x++) {
				for (int y = 0; y < weightarr[0].length; y++) {
					double randweight = (Math.random() - 0.5) * (uplw - lowlw) + ((uplw + lowlw) / 2);
					weightarr[x][y] = randweight;
				}
			}

		}
		for (double[] biasarr : biases) {
			for (int i = 0; i < biasarr.length; i++) {
				double randbias = (Math.random() - 0.5) * (uplb - lowlb) + ((uplb + lowlb) / 2);
				biasarr[i] = randbias;
			}
		}

	}

	public void calculateFully() {
		calculateLayer(1, true);
	}

	private void calculateLayer(int layer, boolean recurseFully) {
		if (layer == 0)
			throw new RuntimeException("Cannot calculate input layer");
		if (layer >= networkDimensions.length)
			return;

		double[] nodearr = network[layer - 1];
		double[] biasarr = biases[layer];

		double[][] result = Util.matrixMult(weights[layer - 1], Util.toDoubleArray(nodearr));
		result = Util.matrixAdd(result, Util.toDoubleArray(biasarr));
		network_Z[layer] = Util.toSingleArray(result);
		result = Util.sigmoid(result);
		network[layer] = Util.toSingleArray(result);

		if (recurseFully) {
			calculateLayer(layer + 1, recurseFully);
		}
	}

	private static final double learningRate = 10;

	// TRAINING
	public void setTraining(Training training) {
		this.training = training;
	}

	public void train() {

		for (int i = 0; i < 10000; i++) {
			double[][][] examples = training.generateTrainingExamples(100);
			double bcost = calculateCost(examples);

			double[][][] dW = null;
			double[][] dB = null;
			for (int j = 0; j < 100; j++) {
				feed(examples[j][0]);
				calculateFully();
				calculateOutputNodePartials(examples[j][1], true);
				Main.setExpectedOutput(examples[j][1]);

				if (dW == null) {
					dW = weightsPartials;
				} else {
					dW = Util.matrixAdd(dW, weightsPartials);
				}
				if (dB == null) {
					dB = biasesPartials;
				} else {
					dB = Util.matrixAdd(dB, biasesPartials);
				}
			}
			dW = Util.matrixScale(dW, -learningRate / 100);
			dB = Util.matrixScale(dB, -learningRate / 100);
			takeWeightGradientDescentStep(dW);
			takeBiasGradientDescentStep(dB);

			double cost = calculateCost(examples);
			double accuracy = calculateAccuracy(examples);
			System.out.println(
					"Cost went " + (cost > bcost ? "up " : "down ") + Math.abs(cost - bcost) + ", now at " + cost);
			System.out.println("accuracy: " + accuracy);
			if (i % 50 == 0) {
				// System.out.println("weights: " + Util.toString(weights));
				// System.out.println("biases: " + Util.toString(biases));
				save();
				System.out.println("saved");
			}
			Main.redraw();
		}
	}

	public void save() {
		try {
			FileOutputStream fileOut = new FileOutputStream(Main.saveFile);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			double[][][][] data = new double[][][][] { weights, { biases } };
			out.writeObject(data);

			out.close();
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// these are the partials of the cost function (for given training example) with
	// respect to the following variables

	// network[layer][nodeid]
	private double[][] nodePartials;

	// beforeSigmoidPartials is the node values before sigmoid
	private double[][] beforeSigmoidPartials;

	// weights between layer1 and layer2
	// weights[layer1][node2id][node1id]
	private double[][][] weightsPartials;

	// biases at layer
	// biases[layer][nodeid]
	private double[][] biasesPartials;

	private void calculateOutputNodePartials(double[] expectedOutput, boolean recurseFully) {
		for (int j = 0; j < networkDimensions[networkDimensions.length - 1]; j++) {
			nodePartials[networkDimensions.length - 1][j] = 2
					* (network[networkDimensions.length - 1][j] - expectedOutput[j]);
		}
		if (recurseFully) {
			calculatePartials(networkDimensions.length - 1, recurseFully);
		}
	}

	private void calculatePartials(int layer, boolean recurseFully) {
		if (layer < 1)
			return;

		for (int i = 0; i < networkDimensions[layer]; i++) {
			beforeSigmoidPartials[layer][i] = nodePartials[layer][i] * Util.sigmoidDerivative(network_Z[layer][i]);

			for (int k = 0; k < networkDimensions[layer - 1]; k++) {
				weightsPartials[layer - 1][i][k] = beforeSigmoidPartials[layer][i] * network[layer - 1][k];
			}
			biasesPartials[layer][i] = beforeSigmoidPartials[layer][i];
		}
		for (int k = 0; k < networkDimensions[layer - 1]; k++) {
			double sum = 0;
			for (int i = 0; i < networkDimensions[layer]; i++) {
				sum += beforeSigmoidPartials[layer][i] * weights[layer - 1][i][k];
			}
			nodePartials[layer - 1][k] = sum;
		}

		if (recurseFully) {
			calculatePartials(layer - 1, recurseFully);
		}
	}

	private void takeWeightGradientDescentStep(double[][][] dW) {
		weights = Util.matrixAdd(weights, dW);
	}

	private void takeBiasGradientDescentStep(double[][] dB) {
		biases = Util.matrixAdd(biases, dB);
	}

	/**
	 * trainingExamples[trainingExampleNum][0] is the input layer
	 * trainingExamples[trainingExampleNum][1] is the expected output layer
	 * 
	 * @param trainingExamples
	 * @return the cost of the network ("how bad it is")
	 */
	public double calculateCost(double[][][] trainingExamples) {
		double totalCost = 0;
		for (double[][] example : trainingExamples) {
			totalCost += calculateCost(example);
		}
		return totalCost / trainingExamples.length;
	}

	public double calculateAccuracy(double[][][] trainingExamples) {
		double totalAccuracy = 0;
		for (double[][] example : trainingExamples) {
			totalAccuracy += calculateAccuracy(example) ? 1 : 0;
		}
		return totalAccuracy / trainingExamples.length;
	}

	/**
	 * trainingExamples[0] is the input layer
	 * 
	 * trainingExamples[1] is the expected output layer
	 * 
	 * @param trainingExamples
	 * @return the cost of the network ("how bad it is")
	 */
	public double calculateCost(double[][] trainingExample) {
		feed(trainingExample[0]);
		calculateFully();
		double[] output = getOutput();
		double[] expectedOutput = trainingExample[1];
		double cost = 0;
		for (int i = 0; i < output.length; i++) {
			cost += Math.pow(expectedOutput[i] - output[i], 2);
		}
		return cost;
	}

	public boolean calculateAccuracy(double[][] trainingExample) {
		feed(trainingExample[0]);
		calculateFully();
		double[] output = getOutput();
		double[] expectedOutput = trainingExample[1];
		return training.isCorrect(output, expectedOutput);
	}

	@Override
	public String toString() {
		String str = "";
		for (double[] element : network) {
			for (double element2 : element) {
				str += element2 + " ";
			}
			str += "\n";
		}
		return str;
	}
}

class Util {
	public static double sigmoid(double inp) {
		return 1 / (1 + Math.exp(-inp));
	}

	public static double[][] sigmoid(double[][] inp) {
		double[][] res = inp.clone();
		for (int x = 0; x < inp.length; x++) {
			for (int y = 0; y < inp[x].length; y++) {
				res[x][y] = sigmoid(inp[x][y]);
			}
		}
		return res;
	}

	public static double sigmoidDerivative(double inp) {
		return sigmoid(inp) * (1 - sigmoid(inp));
	}

	public static double[][] toDoubleArray(double[] arr) {
		double[][] mat = new double[arr.length][1];

		for (int i = 0; i < mat.length; i++) {
			mat[i][0] = arr[i];
		}
		return mat;
	}

	public static double[] toSingleArray(double[][] mat) {
		double[] arr = new double[mat.length];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = mat[i][0];
		}
		return arr;
	}

	public static double[][] deepCopy(double[][] arr) {
		arr = arr.clone();
		for (int i = 0; i < arr.length; i++) {
			arr[i] = arr[i].clone();
		}
		return arr;
	}

	public static double[][][] deepCopy(double[][][] arr) {
		arr = arr.clone();
		for (int i = 0; i < arr.length; i++) {
			arr[i] = deepCopy(arr[i]);
		}
		return arr;
	}

	public static double[][] matrixMult(double[][] m1, double[][] m2) {
		int m1height = m1.length, m1width = m1[0].length;
		int m2height = m2.length, m2width = m2[0].length;

		if (m1width != m2height)
			return null;
		int mrheight = m1height; // m result rows length
		int mrwidth = m2width; // m result columns length
		double[][] mr = new double[mrheight][mrwidth];
		for (int i = 0; i < mrheight; i++) { // rows from m1
			for (int j = 0; j < mrwidth; j++) {
				mr[i][j] = 0;
				for (int k = 0; k < m1width; k++) {
					mr[i][j] += m1[i][k] * m2[k][j];
				}
			}
		}
		return mr;
	}

	public static double[][] matrixAdd(double[][] m1, double[][] m2) {
		double[][] mResult = deepCopy(m1);
		for (int x = 0; x < m1.length; x++) {
			for (int y = 0; y < m1[x].length; y++) {
				mResult[x][y] = m1[x][y] + m2[x][y];
			}
		}
		return mResult;
	}

	public static double[][][] matrixAdd(double[][][] m1, double[][][] m2) {
		double[][][] mResult = deepCopy(m1);
		for (int x = 0; x < m1.length; x++) {
			for (int y = 0; y < m1[x].length; y++) {
				for (int k = 0; k < m1[x][y].length; k++) {
					mResult[x][y][k] = m1[x][y][k] + m2[x][y][k];
				}
			}
		}
		return mResult;
	}

	public static double[][] matrixScale(double[][] inp, double scale) {
		inp = deepCopy(inp);
		for (int i = 0; i < inp.length; i++) {
			for (int j = 0; j < inp[i].length; j++) {
				inp[i][j] = scale * inp[i][j];
			}
		}
		return inp;
	}

	public static double[][][] matrixScale(double[][][] inp, double scale) {
		inp = deepCopy(inp);
		for (int i = 0; i < inp.length; i++) {
			for (int j = 0; j < inp[i].length; j++) {
				for (int k = 0; k < inp[i][j].length; k++) {
					inp[i][j][k] = scale * inp[i][j][k];
				}
			}
		}
		return inp;
	}

	public static <E> E[] randomizeArray(E[] array) {
		array = array.clone();
		for (int i = 0; i < array.length; i++) {
			E e1 = array[i];
			int random = (int) (Math.random() * array.length);
			E e2 = array[random];
			array[i] = e2;
			array[random] = e1;
		}
		return array;
	}

	public static String toString(double[] arr) {
		String str = "[";
		for (double d : arr) {
			str += d + ", ";
		}
		return str + "]";
	}

	public static String toString(double[][] arr) {
		String str = "{";
		for (int i = 0; i < arr.length; i++) {
			double[] a = arr[i];
			str += toString(a) + ", ";
			if (i != arr.length - 1) {
				str += "\n";
			}
		}
		return str + "}";
	}

	public static String toString(double[][][] arr) {
		String str = "[";
		for (int i = 0; i < arr.length; i++) {
			double[][] a = arr[i];
			str += toString(a) + ", ";
			if (i != arr.length - 1) {
				str += "\n";
			}
		}
		return str + "]";
	}

}
