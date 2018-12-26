package main;

import data.tuple.Tuple2D;
import machinelearning.neuralnet.NeuralNetworkTrainer;
import machinelearning.neuralnet.TrackableNeuralNetwork;
import machinelearning.neuralnet.VisualNetwork;

public class FunctionLearning {
	public static void main(String[] args) {
		TrackableNeuralNetwork net = new TrackableNeuralNetwork(20, 20, 20, 20);
		VisualNetwork visual = new VisualNetwork(net);
		visual.setVisible(true);

		net.randomizeWeightsAndBiases();
		net.setTrainer(new Trainer());
		net.trainWithBatches(100000, 1000);
	}

	public static int funcToLearn(int inp) {
		return inp % 2 == 1 ? inp + 1 : inp;
	}
}

class Trainer implements NeuralNetworkTrainer<Integer, Integer> {

	public double[] intToBinaryArray(int j) {
		String str = Integer.toBinaryString(j);
		double[] arr = new double[20];

		for (int i = str.length() - 1, k = 0; i >= 0; i--, k++) {
			char c = str.charAt(i);
			arr[arr.length - 1 - k] = c == '0' ? 0 : 1;
		}

		return arr;
	}

	@Override
	public double[] rawToInputLayer(Integer d) {
		return intToBinaryArray(d);
	}

	@Override
	public double[] rawToOutputLayer(Integer a) {
		return intToBinaryArray(a);
	}

	@Override
	public Tuple2D<Integer, Integer> getRandomRawData() {
		int rand = (int) ((Math.random()) * 10000);
		int sol = FunctionLearning.funcToLearn(rand);
		return new Tuple2D<Integer, Integer>(rand, sol);
	}

	@Override
	public double getWeightLearningRate() {
		return .1;
	}

	@Override
	public double acceptableOutputError() {
		return .5;
	}

	@Override
	public int getN() {
		return 20;
	}

	@Override
	public void runEveryNIterations(double[] beforePerformance, double[] afterPerformance) {
		System.out.println("Cost: " + afterPerformance[0]);
		System.out.println("Accuracy: " + afterPerformance[1]);
		System.out.println();
	}

}
