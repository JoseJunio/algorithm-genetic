package SimulateAG;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

import Utils.Utils;

public class Population_priority_queue {

	public int size;
	//public LinkedList<Individual> individuals;
	public PriorityQueue<Individual> individuals;
	public int generation;
	public Individual bestIndividual;
	public LinkedList<Individual> bestIndividuals;
	
	public Population_priority_queue(int size) {
		this.size = size;
		//this.individuals = new LinkedList<Individual>();
		this.individuals = new PriorityQueue();
		this.generation = 0;
	}
	
	public static Population generate(int size, int machines, int tasks) {
		Population newPopulation = new Population(size);
		
		while(newPopulation.getIndividuals().size() < size) {
			/*float makespan = -1;
			
			if(newPopulation.getIndividuals().size() >= 1) {
				makespan = newPopulation.getBestIndividual().getMakespan();
			}
			
			Individual newIndividual = Individual.generate(newPopulation.getGeneration(), tasks, machines, makespan);*/
			Individual newIndividual = Individual.generate(newPopulation.getGeneration(), tasks, machines);
			
			if(!newPopulation.is_duplicate(newIndividual)) {
				newPopulation.individuals.add(newIndividual);
			}
			
			newPopulation.setBestIndividual(newPopulation.getIndividuals().get(0));
			
		}
		return newPopulation;
	}
	
	public boolean is_duplicate(Individual individualToInsert) {
		for(int i=0; i< getIndividuals().size(); i++) {
			
			Individual individual = getIndividuals().get(i);
			if(individualToInsert.getGenotype().equals(individual.getGenotype())) {
				return true;
			}
		}
		return false;
	}
	
	public List<Tuple> select_parents(int crossoverFactor) {
		List<Float> parents = new ArrayList<>();

		int numberOfParents = (int) (this.size * (crossoverFactor / 100.0));
		
		if(numberOfParents % 2 == 0) {
			numberOfParents -= 1;
		}
		
		int numberOfPairs = (int) (numberOfParents / 2);
		
		List<Tuple> tuples = new ArrayList<Tuple>();
		
		for(int i=0; i < numberOfPairs; i++) {
			Tuple tuple = select_pair();
			tuples.add(select_pair());
		}
		
		return tuples;
	}
	
	public Tuple select_pair() {
		Tuple tuple;
		
		int indexParent1 = Utils.randInt(0, this.getIndividuals().size() - 1);
		int indexParent2 = 0;
		
		while(true) {
			indexParent2 = Utils.randInt(0, this.getIndividuals().size() - 1);
			
			if(indexParent2 != indexParent1) {
				break;
			}
		}
		
		tuple = new Tuple(this.getIndividuals().get(indexParent1), this.getIndividuals().get(indexParent2));
		
		return tuple;
	}

	public void insert_individual(Individual newIndividual) {
		if(!is_duplicate(newIndividual)) {
			this.individuals.add(newIndividual);
		}
		/*Individual best = getIndividuals().get(0);
		setBestIndividual(best);*/
	}
	
	public void update_population(int elitismFactor) {
		//LinkedList<Individual> newPopulation = new LinkedList<Individual>();
		PriorityQueue<Individual> newPopulation = new PriorityQueue();
		
		this.bestIndividuals = new LinkedList<Individual>();
		
		int nElitism = (int) (this.size * (elitismFactor / 100.0));
		
		for(int i=0; i < nElitism; i++) {
			newPopulation.add(this.individuals.poll());
		}
		
		for(int i=0; i < (this.size - nElitism); i++) {
			Individual selectedIndividual = select_individual();
			newPopulation.add(selectedIndividual);
		}
		
		/*for(int i = 0; i < nElitism; i++) {
			float[] minAndIndex = Utils.getIndexAndMin(this.individuals);
		
			//if(minAndIndex[1] < this.individuals.get(i).getMakespan()) {
			if(minAndIndex[1] < newPopulation.get(i).getMakespan()) {
				newPopulation.get(i).setMakespan(minAndIndex[1]);
				 
				this.bestIndividuals.add(newPopulation.get(i));
				
				if(i == 0) {
					setBestIndividual(newPopulation.get(0));
				}
			}
			
			this.individuals.remove((int) minAndIndex[0]);
			
		}*/
		
		//Verificamos o menor makespan na coleção de individuos e setamos ele como o menor na posição 0
		/*float minorMakespan = this.individuals.get(0).getMakespan();
		
		for(int i = 0; i < nElitism; i++) {
			for(int j=1; j < this.individuals.size(); j++) {
				Individual ind = this.individuals.get(j);
				if(ind.getMakespan() < minorMakespan) {
					minorMakespan = ind.getMakespan();
				}
			}
		}
		
		if(minorMakespan < this.individuals.get(0).getMakespan()) {
			newPopulation.get(0).setMakespan(minorMakespan);
			setBestIndividual(newPopulation.get(0));
		}*/
		
		this.individuals = newPopulation;
		
	}
	
	public Individual select_individual() {
		int index = Utils.randInt(0, getIndividuals().size() - 1);
		return getIndividuals().get(index);
	}
	
	public float makespan_sum() {
		float total_sum = 0;
		
		for(int i=0; i < getIndividuals().size(); i++) {
			total_sum += getIndividuals().get(i).getMakespan();
		}
		
		return total_sum;
	}
	
	public float average() {
		float average = 0;
		
		average = makespan_sum() / this.individuals.size();
		
		return average;
	}
	
	public float variance() {
		float variance = 0;
		float sum = 0;
		
		float average = average();

		for(int i=0; i < this.individuals.size(); i++) {
			 float makespan = getIndividuals().get(i).getMakespan();
			 sum += (float) Math.pow(makespan - average, 2);
		}
		
		variance = sum / (this.individuals.size() - 1);
		
		return variance;
	}
	
	public float standardDeviation() {
		float variance = variance();
		return (float) Math.sqrt(variance);
	}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public LinkedList<Individual> getIndividuals() {
		LinkedList<Individual> individuos = new LinkedList<Individual>();
		
		Object[] temp = this.individuals.toArray();
		
		for(int i=0; i < temp.length; i++){
			Individual ind = (Individual) temp[i];
			individuos.add(ind);
		}
		
		return individuos;
	}

	public int getGeneration() {
		return generation;
	}

	public void setGeneration(int generation) {
		this.generation = generation;
	}

	public Individual getBestIndividual() {
		return bestIndividual;
	}

	public void setBestIndividual(Individual bestIndividual) {
		this.bestIndividual = bestIndividual;
	}
}
