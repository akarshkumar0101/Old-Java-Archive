package clumpfind.stat;

import java.util.ArrayList;
import java.util.List;

public class Stat {

	private List<Double> set;

	private double sum;

	private double min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;

	private boolean meanCalc = false;
	private double mean;

	private boolean stddevCalc = false;
	private double stddev;

	public Stat() {
		set = new ArrayList<>(30);
	}

	public Stat(double[] arr) {
		set = new ArrayList<>(arr.length);
		for (double d : arr) {
			addNum(d);
		}
	}

	public void addNum(double num) {
		set.add(num);
		sum += num;

		meanCalc = false;
		stddevCalc = false;

		if (num > max)
			max = num;
		if (num < min)
			min = num;
	}

	public double sum() {
		return sum;
	}

	public double mean() {
		if (meanCalc)
			return mean;
		else {
			meanCalc = true;
			mean = sum / set.size();
			return mean;
		}
	}

	public double stddev() {
		if (stddevCalc)
			return stddev;
		else {
			stddevCalc = true;
			double sumSquares = 0;
			for (double d : set) {
				sumSquares += Math.pow(mean() - d, 2);
			}
			stddev = Math.sqrt(sumSquares / set.size());
			return stddev;
		}
	}
}
