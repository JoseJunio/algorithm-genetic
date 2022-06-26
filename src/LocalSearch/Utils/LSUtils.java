package LocalSearch.Utils;

import java.util.Random;

import LocalSearch.Solution;

public class LSUtils {
	
	public static Solution randomMap(int tasks, int machines, double[][] etc) {
		Solution solution = new Solution(etc);
		
		for (int i = 0; i < tasks; i++){
		    solution.mapping[i] = randInt(0, machines-1);
		}

		solution.load();
		solution.makespan();
		return solution;
	}
	
	public static int randInt(int min, int max) {
		Random rand = new Random();

	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	public static double randDouble(double min, double max) {
		Random rand = new Random();

	    return min + (max - min) * rand.nextDouble();

	}


}
