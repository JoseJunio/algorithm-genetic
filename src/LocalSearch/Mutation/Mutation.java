package LocalSearch.Mutation;

import LocalSearch.Solution;
import LocalSearch.Utils.LSUtils;

public class Mutation {
	
	public Solution mutationOne(Solution l1, int tasks, int machines) {
		Solution solution = new Solution(l1.etc); 
		solution.mapping = l1.mapping;
		
		int a = LSUtils.randInt(0, tasks-1);
		int b = LSUtils.randInt(0, machines-1);
		
		solution.mapping[a] = b;
		
		solution.load();
		solution.makespan();
		
		return solution;
		
	}
	
	public Solution mutationTwo(Solution l1, int tasks, int machines) {
		Solution solution = new Solution(l1.etc); 
		solution.mapping = l1.mapping;
		
		for(int i=0; i<solution.mapping.length/4; i++) {
			int a = LSUtils.randInt(0, tasks-1);
			int b = LSUtils.randInt(0, machines-1);
			
			solution.mapping[a] = b;
		}
		
		solution.load();
		solution.makespan();
		
		return solution;
		
	}
	
}
