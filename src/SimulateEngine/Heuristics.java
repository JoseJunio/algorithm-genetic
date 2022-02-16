package SimulateEngine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import LocalSearch.Solution;
import LocalSearch.Utils.LSUtils;
import SimulateAG.Individual;
import Task.Task;
import Utils.Utils;

public class Heuristics {

	public static Individual schedule_MinMin(Vector<Task> metaSet, SimulateEngine sim) {

		Individual ind = new Individual(0);

		/*
		 * We do not actually delete the task from the meta-set rather mark it as
		 * removed
		 */
		boolean[] isRemoved = new boolean[metaSet.size()];

		/*
		 * Matrix to contain the completion time of each task in the meta-set on each
		 * machine.
		 */
		float c[][] = schedule_MinMinHelper(metaSet, sim);
		int i = 0;

		int tasksRemoved = 0;
		do {
			float minTime = Float.MAX_VALUE;
			int machine = -1;
			int taskNo = -1;
			/*
			 * Find the task in the meta set with the earliest completion time and the
			 * machine that obtains it.
			 */
			for (i = 0; i < metaSet.size(); i++) {
				if (isRemoved[i])
					continue;
				for (int j = 0; j < sim.machines; j++) {
					if (c[i][j] < minTime) {
						minTime = c[i][j];
						machine = j;
						taskNo = i;
					}
				}
			}
			Task t = metaSet.elementAt(taskNo);
			sim.mapTask(t, machine);

			System.out.println("task: " + t.tid + " machine: " + machine);

			ind.setGenotype(t.tid, machine);

			// newIndividual.setGenotype(t, machine);
			// t posição do individuo

			/* Mark this task as removed */
			tasksRemoved++;
			isRemoved[taskNo] = true;

			/* Update c[][] Matrix for other tasks in the meta-set */
			for (i = 0; i < metaSet.size(); i++) {
				if (isRemoved[i])
					continue;
				else {
					c[i][machine] = (sim.mat[machine] + sim.etc[metaSet.get(i).tid][machine]);
				}
			}

		} while (tasksRemoved != metaSet.size());

		float longerTime = 0;

		for (int t = 0; t < sim.tasks; t++) {
			Task task = metaSet.elementAt(t);
			if (task.get_cTime() > longerTime) {
				longerTime = task.get_cTime();
			}
		}

		// ind.setMakespan(longerTime);
		// individuo.setMakespan(longerTime);
		// return class Individual
		// return longerTime;
		return ind;
	}

	public static Solution min_min(double[][] etc, int tasks, int machines) {

		double[][] tmp = new double[tasks][machines];

		Solution solution = new Solution(etc);

		for (int i = 0; i < tasks; i++) {
			for (int j = 0; j < machines; j++) {
				tmp[i][j] = etc[i][j];
			}
		}

		boolean[] isRemoved = new boolean[tasks];

		// inicializa com false
		for (int i = 0; i < tasks; i++) {
			isRemoved[i] = false;
		}

		int numTasks = tasks;

		do {

			double minValue = Double.MAX_VALUE;
			int machine = -1;
			int task = -1;

			for (int i = 0; i < tasks; i++) {

				if (isRemoved[i]) {
					continue;
				}

				for (int j = 0; j < machines; j++) {
					if (tmp[i][j] < minValue) {
						minValue = tmp[i][j];
						machine = j;
						task = i;
					}
				}
			}

			solution.load[machine] = minValue;
			solution.mapping[task] = machine;
			isRemoved[task] = true;

			for (int i = 0; i < tasks; i++) {
				if (isRemoved[i]) {
					continue;
				}

				tmp[i][machine] = etc[i][machine] + solution.load[machine];

			}

			numTasks--;
		} while (numTasks > 0);

		solution.makespan();

		return solution;

	}

	public static Solution generatePopInitial(double[][] etc, int tasks, int machines) {

		double[][] tmp = new double[tasks][machines];

		Solution solution = new Solution(etc);
		
		
		for (int i = 0; i < tasks; i++){
		    solution.mapping[i] = LSUtils.randInt(0, machines-1);
		}
		

		for (int i = 0; i < tasks; i++) {
			for (int j = 0; j < machines; j++) {
				tmp[i][j] = etc[i][j];
			}
		}

		boolean[] isRemoved = new boolean[tasks];

		// inicializa com false
		for (int i = 0; i < tasks; i++) {
			isRemoved[i] = false;
		}

		int numTasks = tasks/2;

		do {

			int machine = LSUtils.randInt(0, machines-1);
			int task = LSUtils.randInt(0, tasks-1);

			solution.load[machine] = tmp[task][machine];
			solution.mapping[task] = machine;
			isRemoved[task] = true;

			for (int i = 0; i < tasks; i++) {
				if (isRemoved[i]) {
					continue;
				}

				tmp[i][machine] = etc[i][machine] + solution.load[machine];

			}

			numTasks--;
		} while (numTasks > 0);

		solution.makespan();

		return solution;

	}

	private static float[][] schedule_MinMinHelper(Vector<Task> metaSet, SimulateEngine sim) {
		float c[][] = new float[metaSet.size()][sim.machines];
		int i = 0;
		for (Iterator it = metaSet.iterator(); it.hasNext();) {
			Task t = (Task) it.next();
			for (int j = 0; j < sim.machines; j++) {
				c[i][j] = (sim.mat[j] + sim.etc[t.tid][j]);
			}
			i++;
		}
		return c;
	}


	public static Solution generateTest(String mappingStr, double[][] etc, int tasks, int machines) {
		String[] mapping = mappingStr.split(",");
		
		Solution solution = new Solution(etc);
		
		for(int i=0; i<tasks; i++) {
			int machine = Integer.valueOf(mapping[i]);
			
			solution.mapping[i] = machine - 1;
		}
		
		solution.load();
		solution.makespan();
		
		return solution;
		
	}
	
	public static Solution[] readFile(String fileName, double[][] etc, int tasks, int machines, Solution[] solutions) {
		try {
			File file = new File(fileName);
			FileReader fileReader = new FileReader(file);
			
			BufferedReader br = new BufferedReader(fileReader); 
			
			String st = ""; 
			
			String[] solutionsParsed = null;
			
			while ((st = br.readLine()) != null) {
				
				solutionsParsed = st.split(":");
				  
			 }
			
			int i=1;
			
			for(String solutionParsed : solutionsParsed) {
				//solutions.add(generateTest(solutionParsed, etc, tasks, machines));
				solutions[i] = generateTest(solutionParsed, etc, tasks, machines);
				i++;
			}
			
	
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		return solutions;
	}
	
	
}
