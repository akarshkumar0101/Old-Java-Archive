package clumpfind;

import clumpfind.stat.Stat;

public class ClumpFind {
	private final double[][] coors;

	public ClumpFind(double[][] coors) {
		this.coors = coors;
	}

	public double[] findBiggestClump(double avgdisttoclosest) {
		int maxPointsInRange = Integer.MIN_VALUE;
		double[] coorMaxPointsInRange=null;
		for(double[] coor:coors){
			int pointsInRange = numPointsInRange(coor, avgdisttoclosest);
			if(pointsInRange>maxPointsInRange){
				maxPointsInRange=pointsInRange;
				coorMaxPointsInRange = coor;
			}
		}
		return coorMaxPointsInRange;
		
	}
	public double avgDistToClosest(){
		Stat closestPointList = new Stat();
		for (double[] coor : coors) {
			closestPointList.addNum(closestPoint(coor));
		}
		double avgdisttoclosest = closestPointList.mean();
		return avgdisttoclosest;
	}

	public int numPointsInRange(double[] point, double range) {
		int inRange = 0;
		for (double[] coor : coors) {
			if (coor == point)
				continue;
			if (dist(point, coor) <= range)
				inRange++;
		}
		return inRange;
	}

	public double closestPoint(double[] point) {
		double minDist = Double.MAX_VALUE;
		//double[] closest = null;
		for (double[] coor : coors) {
			if (coor == point)
				continue;
			double dist = dist(point, coor);
			if (dist < minDist) {
				minDist = dist;
				//closest = coor;
			}
		}
		// return closest;
		return minDist;
	}

	public static double dist(double[] coora, double[] coorb) {
		return Math.sqrt(Math.pow(coorb[0] - coora[0], 2) + Math.pow(coorb[1] - coora[1], 2));
	}

}
