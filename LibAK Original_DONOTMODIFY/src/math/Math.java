package math;

public class Math {
	public static double scale(double num, double min1, double max1, double min2, double max2) {
		num -= min1;
		num *= (max2 - min2) / (max1 - min1);
		num += min2;
		return num;
	}
}
