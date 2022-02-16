package Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import SimulateAG.Individual;

public class Utils {
	
	public static void main(String args[]) {
		for(int i=0; i<50; i++)
		System.out.println(Utils.randInt(0, 15));
	}
	

	public static float max(HashMap<Integer, Float> busyTimes) {
		float max = 0;
		
		for(int i=0; i < busyTimes.size(); i++) {
			if(busyTimes.get(i) > max) {
				max = busyTimes.get(i);
			}
		}
		
		return max;
	}
	
	public static float sum(HashMap<Integer, Float> busyTimes) {
		float sum = 0;
		
		for(int i=0; i < busyTimes.size(); i++) {
			sum += busyTimes.get(i);
		}
		
		return sum;
	}
	
	public static int randInt(int min, int max) {
		Random rand = new Random();

	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	public static float[] getIndexAndMin(LinkedList<Individual> data) {
		float[] list = new float[2];
		
	    float min = Float.MAX_VALUE;
	    int index = -1;
	    for (int i = 0; i < data.size(); i++) {
	        Float f = data.get(i).getMakespan();
	        if (Float.compare(f.floatValue(), min) < 0) {
	            min = f.floatValue();
	            index = i;
	        }
	    }
	    list[0] = index;
	    list[1] = min;
	    
	    return list;
	}

}
