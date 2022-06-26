package SimulateAG;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Task.Task;

public class IndividualParameters {

	private static int numTasks;
	private static int numMachines;
	private static List<Task> tasks = new ArrayList<Task>();
	private static List<Integer> crossoverMask = new ArrayList<Integer>();
	private static int nextIndividualIdentifier;
	
	public IndividualParameters(int numTasks, int numMachines, List<Task> tasks) {
		IndividualParameters.numMachines = numMachines;
		IndividualParameters.numTasks = numTasks;
		IndividualParameters.tasks = tasks;
		IndividualParameters.nextIndividualIdentifier = 0;
		generate_crossover_mask();
	}
	
	public static void generate_crossover_mask() {
		Random random = new Random();
		
		for(int i=0; i < IndividualParameters.numTasks; i++) {
			int assigneMachine = random.nextInt(2) + 1;
			IndividualParameters.crossoverMask.add(assigneMachine);		
 		}
	}

	public static int getNumTasks() {
		return numTasks;
	}

	public static int getNumMachines() {
		return numMachines;
	}

	public static List<Task> getTasks() {
		return tasks;
	}

	public static List<Integer> getCrossoverMask() {
		return crossoverMask;
	}

	public static int getNextIndividualIdentifier() {
		return nextIndividualIdentifier;
	}

	public static void incrementNextIndividualIdentifier(int nextIndividualIdentifier) {
		IndividualParameters.nextIndividualIdentifier += nextIndividualIdentifier;
	}

}
