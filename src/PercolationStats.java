//NetID: xx46

import java.util.*;
import java.util.Random;

public class PercolationStats {
	public static int RANDOM_SEED = 1234;
	public static Random ourRandom = new Random(RANDOM_SEED);
	private double[] resultSet;
	private static double start;
	private static double end;
	
	
	// perform T independent experiments on an N-by-N grid
	public PercolationStats(int N, int T){
		if (N<=0||T<=0)
			throw new IllegalArgumentException("Index is out of bounds.");
		resultSet = new double[T];
		ArrayList<int[]> grid = new ArrayList<>();
		for (int h=0;h<N;h+=1) {
			for (int i=0; i<N; i+=1) {
				int[] coordinate = {h,i};
				grid.add(coordinate);
			}
		}
		double[] times = new double[T];
		for (int k=0;k<T;k+=1) {
			Collections.shuffle(grid,ourRandom);
			start = System.nanoTime();
			IUnionFind finder = new QuickUWPC(N);
			IPercolate percolator = new PercolationUF(N,finder);
			for (int f=0;f<N*N;f+=1) {
				percolator.open(grid.get(f)[0], grid.get(f)[1]);
				if (percolator.percolates()) {
					resultSet[k] = percolator.numberOfOpenSites()/(Math.pow(N,2));
					break;
				}
			}
		end = System.nanoTime();
		times[k] = (end-start)/1e9;
		}
	}
	
	
	public double mean(){
		double sum = 0;
		for (int i=0; i<resultSet.length; i++){
			sum+=resultSet[i];
		}
		return sum/resultSet.length;
	}
	

	public double stddev(){
		double dev = 0;
		for (int i=0; i<resultSet.length; i++){
			dev+=(resultSet[i]-this.mean())*(resultSet[i]-this.mean());
		}
		return Math.sqrt(dev/(resultSet.length-1));
	}
	

	public double confidenceLow(){
		return (this.mean()-1.96*stddev()/Math.sqrt(resultSet.length));
	}
	

	public double confidenceHigh(){
		return (this.mean()+1.96*stddev()/Math.sqrt(resultSet.length));
	}
	

	public static void main(String[] args){
		PercolationStats p = new PercolationStats(80,20);
		System.out.println("mean: "+p.mean());
		System.out.println("Standard Deviation: "+p.stddev());
		System.out.println("low endpoint of 95% confidence interval: "+p.confidenceLow());
		System.out.println("high endpoint of 95% confidence interval: "+p.confidenceHigh());
		System.out.printf("total time = %2.3f\n", (end-start));	
	}
}
