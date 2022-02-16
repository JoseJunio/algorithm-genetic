package LocalSearch.Crossover;

import LocalSearch.Solution;
import LocalSearch.Utils.LSUtils;

public class Crossover {
	
	public int[] crossover_mask;
	
	public Solution crossover_uniform_unique(int tasks, Solution l1, Solution l2, double c) {
		
		Solution l = new Solution(l1.etc);
		
		double average = (l1.makespan + l2.makespan) / 2.0;
		
		generate_crossover_mask(tasks);
		
		for (int a = 0; a < tasks; a++){
		    if(crossover_mask[a] == 1){
		        l.mapping[a] = l1.mapping[a];
		    } else {
		        l.mapping[a] = l2.mapping[a];
		    }
		}
		
		l.load();
		l.makespan();
		
		double rand = LSUtils.randDouble(0.0, 1.0);
		
		if(l.makespan > average || rand < Math.exp((l.makespan - average)/c)) {
			return l;
		}
		
		return null;
		
	}
	
	
	public void generate_crossover_mask(int tasks) {
	
		crossover_mask = new int[tasks];
		
		 for (int i = 0; i < tasks; ++i) {
		   crossover_mask[i] = LSUtils.randInt(0, 1);
		 }
	}
	

}
