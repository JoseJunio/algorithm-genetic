package SimulateAG;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import Task.Task;
import Utils.Utils;

public class Individual implements Comparable<Individual>{

	private int nextIndividualIdentifier = 0;
	private int numTasks = 0;
	private int numMachines = 0;
	private List<Task> tasks = new ArrayList<Task>();
	private List<Integer> crossoverMask = new ArrayList<Integer>();
	
	private int generation;
	private float makespan;
	private float flowtime;
	private float utilization;
	private HashMap<Integer, Integer> genotype;
	private int identifier;
	
	public Individual(int generation) {
		this.generation = generation;
		this.genotype = new HashMap<Integer, Integer>();
		this.makespan = 0;
		this.flowtime = 0;
		this.utilization = 0;
		this.identifier = IndividualParameters.getNextIndividualIdentifier();
		IndividualParameters.incrementNextIndividualIdentifier(1);
	}
	
	public void generate_crossover_mask() {
		Random random = new Random();
		
		for(int i=0; i < this.numTasks; i++) {
			int assigneMachine = random.nextInt(2) + 1;
			this.crossoverMask.add(assigneMachine);		
 		}
	}
	
	public static Individual generate(int generation, int tasks, int machines) {
		Random random = new Random();
		
		Individual newIndividual = new Individual(generation);
		
		for(int t=0; t < tasks; t++) {
			int assigneMachine = Utils.randInt(0, IndividualParameters.getNumMachines()-1);
			newIndividual.setGenotype(t, assigneMachine);
		}
		
		newIndividual.calculate_fitness();
		
		return newIndividual;
	}

	public void calculate_fitness() {
		HashMap<Integer, Float> busyTimes = new HashMap();
		
		for(int i=0; i < IndividualParameters.getNumMachines(); i++) {
			busyTimes.put(i, (float) 0.0);
		}
		
		for(int j=0; j < IndividualParameters.getNumTasks(); j++) {
			Task task = IndividualParameters.getTasks().get(j);
			int machine = this.genotype.get(j);
			busyTimes.put(machine, busyTimes.get(machine) + task.getMachineTimes().get(machine));
		}
		
		this.makespan = Utils.max(busyTimes);
		this.flowtime = Utils.sum(busyTimes);
		//this.calculate_utilization(makespan);
	}
	
	/*public void calculate_utilization(float populationMakespan) {
		this.utilization = this.makespan / (this.numMachines * populationMakespan);
	}*/
	
	public static List<Individual> crossover_one_point(List<Tuple> parents, int generation, int numTasks) {
		List<Individual> childs = new ArrayList();
		
		int numberOfPairs = parents.size() / 2;
		
		for(int i=0; i < numberOfPairs; i++) {
			Tuple tuple = parents.get(i);
			
			Individual parent1 = tuple.getParent1();
			Individual parent2 = tuple.getParent2();
			
			Individual child1 = parent1.make_copy(generation);
			Individual child2 = parent2.make_copy(generation);
			
			int crossoverPoint = Utils.randInt(0, IndividualParameters.getNumTasks() - 1);
			
			for(int j=crossoverPoint; j < IndividualParameters.getNumTasks(); j++) {
				int aux = child1.getGenotype().get(j);
				child1.setGenotype(j, child2.getGenotype().get(j));
				child2.setGenotype(j, aux);
			}
			
			child1.calculate_fitness();
			child2.calculate_fitness();
		
			childs.add(child1);
			childs.add(child2);
		}
		
		return childs;
		
	}
	
	public static List<Individual> crossover_two_point(List<Tuple> parents, int generation, int numTasks) {
		List<Individual> childs = new ArrayList();
		
		int numberOfPairs = parents.size() / 2;
		
		for(int i=0; i < numberOfPairs; i++) {
			Tuple tuple = parents.get(i);
			
			Individual parent1 = tuple.getParent1();
			Individual parent2 = tuple.getParent2();
			
			Individual child1 = parent1.make_copy(generation);
			Individual child2 = parent2.make_copy(generation);
			
			int crossoverPointOne = Utils.randInt(0, IndividualParameters.getNumTasks() - 1);
			int crossoverPointTwo = crossoverPointOne;
			
			while(crossoverPointTwo == crossoverPointOne) {
				crossoverPointTwo = Utils.randInt(0, IndividualParameters.getNumTasks() - 1);
			}
			
			int left = Math.min(crossoverPointOne, crossoverPointTwo);
			int right = Math.max(crossoverPointOne, crossoverPointTwo);
			
			for(int j=left; j < right; j++) {
				int aux = child1.getGenotype().get(j);
				child1.setGenotype(j, child2.getGenotype().get(j));
				child2.setGenotype(j, aux);
			}
			
			child1.calculate_fitness();
			child2.calculate_fitness();
		
			childs.add(child1);
			childs.add(child2);
		}
		
		return childs;
	}
	
	public static List<Individual> crossover_uniform_unique(List<Tuple> parents, int generation, int numTasks) {
		List<Individual> childs = new ArrayList();
		
		int numberOfPairs = parents.size() / 2;
		
		for(int i=0; i < numberOfPairs; i++) {
			Tuple tuple = parents.get(i);
			
			Individual parent1 = tuple.getParent1();
			Individual parent2 = tuple.getParent2();
			
			Individual child1 = parent1.make_copy(generation);
			Individual child2 = parent2.make_copy(generation);
			
			for(int j=0; j < IndividualParameters.getNumTasks(); j++) {
				if(IndividualParameters.getCrossoverMask().get(i) == 1) {
					child1.setGenotype(j, parent1.getGenotype().get(j));
					child2.setGenotype(j, parent2.getGenotype().get(j));
				} else {
					child2.setGenotype(j, parent1.getGenotype().get(j));
					child1.setGenotype(j, parent2.getGenotype().get(j));
				}
			}
			
			child1.calculate_fitness();
			child2.calculate_fitness();
		
			childs.add(child1);
			childs.add(child2);
		}
		
		return childs;
	}
	
	public static List<Individual> crossover_uniform_multiple(List<Tuple> parents, int generation, int numTasks) {
		List<Individual> childs = new ArrayList();
		
		int numberOfPairs = parents.size() / 2;
		
		for(int i=0; i < numberOfPairs; i++) {
			Tuple tuple = parents.get(i);
			
			Individual parent1 = tuple.getParent1();
			Individual parent2 = tuple.getParent2();
			
			Individual child1 = parent1.make_copy(generation);
			Individual child2 = parent2.make_copy(generation);
			
			IndividualParameters.getCrossoverMask();
			
			List<Integer> crossoverMask = IndividualParameters.getCrossoverMask();
			
			for(int j=0; j < IndividualParameters.getNumTasks(); j++) {
				if(crossoverMask.get(i) == 1) {
					child1.setGenotype(j, parent1.getGenotype().get(j));
					child2.setGenotype(j, parent2.getGenotype().get(j));
				} else {
					child2.setGenotype(j, parent1.getGenotype().get(j));
					child1.setGenotype(j, parent2.getGenotype().get(j));
				}
			}
			
			child1.calculate_fitness();
			child2.calculate_fitness();
		
			childs.add(child1);
			childs.add(child2);
		}
		
		return childs;
	}
	
	public void apply_mutation_simple(int mutationFactor) {
		int mutationRand = Utils.randInt(0, 100);
		
		//if(mutationRand <= mutationFactor) {
			int indexMutation = Utils.randInt(0, this.getGenotype().size()-1);
			int newMachine = Utils.randInt(0, IndividualParameters.getNumMachines()-1);
			setGenotype(indexMutation, newMachine);
		//}
		
		calculate_fitness();
	}
	
	public void apply_mutation_uniform(int mutationFactor) {
		for(int i=0; i<this.getGenotype().size()-1; i++) {
			int mutationRand = Utils.randInt(0, 100);
			
			//if(mutationRand <= mutationFactor) {
				int newMachine = Utils.randInt(0, IndividualParameters.getNumMachines()-1);
				setGenotype(i, newMachine);
			//}
		}
		
		calculate_fitness();
		
	}
	
	public Individual make_copy(int generation) {
		Individual newIndividual = new Individual(generation);
		
		for(int i=0; i<IndividualParameters.getNumTasks(); i++) {
			newIndividual.setGenotype(i, genotype.get(i));
		}
		
		return newIndividual;
	}
	
	public float getMakespan() {
		return makespan;
	}

	public HashMap<Integer, Integer> getGenotype() {
		return genotype;
	}

	public void setGenotype(int key, int value) {
		this.genotype.put(key, value);
	}

	public void setMakespan(float makespan) {
		this.makespan = makespan;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public int getGeneration() {
		return generation;
	}

	public void setGeneration(int generation) {
		this.generation = generation;
	}

	public float getFlowtime() {
		return flowtime;
	}

	public void setFlowtime(float flowtime) {
		this.flowtime = flowtime;
	}

	@Override
	public int compareTo(Individual o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
