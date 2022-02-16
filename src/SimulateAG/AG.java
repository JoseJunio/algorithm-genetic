package SimulateAG;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import Log.Log;
import Parameters.Parameters;
import SimulateEngine.SimulateEngine;
import Task.Task;

public class AG {

	private int machines;
	private int tasks;
	private String fileName;
	private int populationSize;
    private int mutationFactor;
    private int crossoverFactor; 
    private int elitismFactor;
    private int crossoverOperator; 
    private int mutationOperator; 
    private int numExecution;
    private int maxIterations;
    private boolean useMinMin;
    
    //private static int maxIterations = 30000;
    public Individual individual;
	
	public AG(Parameters parameters) {
	    this.fileName = parameters.getFileName();
	    this.machines = parameters.get_NUM_MACHINES(); 
        this.tasks = parameters.get_NUM_TASKS(); 
        //this.populationSize = parameters.getPopulationMaskespan();
        this.populationSize = 500;
        this.mutationFactor = parameters.getPercMutation();
        this.crossoverFactor = parameters.getPercCrossover(); 
        this.elitismFactor = parameters.getPercElitism(); 
        this.crossoverOperator = parameters.getOperCrossover(); 
        this.mutationOperator = parameters.getOperMutation(); 
        this.numExecution = parameters.getNumExecution();
        //this.maxIterations = parameters.getNumIteration();
        this.maxIterations = 2000;
        this.useMinMin = true;
	}
	 
	public void execute(){
		IndividualParameters individual = new IndividualParameters(this.tasks, this.machines, loadTasks());
		
		Population makespanPopulation = Population.generate(this.populationSize - 1, this.machines, individual.getTasks().size());
		
		Individual minMin = new SimulateEngine().execute();
		
		minMin.calculate_fitness();
		
		makespanPopulation.individuals.add(minMin);
		
		makespanPopulation.setSize(this.populationSize);
		
		//testes
		/*DecimalFormat df = new DecimalFormat("#.00");
		for(int i=0; i < makespanPopulation.individuals.size(); i++) {
			System.out.println("makespan: " + df.format(makespanPopulation.individuals.get(i).getMakespan()));
		}*/
		
		Log.create();
		
		for(int i=0; i < this.maxIterations; i++){
			//float currentMaskepan = makespanPopulation.makespan_sum();
			
			// SELECAO
			List<Tuple> parents = makespanPopulation.select_parents(this.crossoverFactor);
			
			//teste
			/*System.out.println("Parents");
			for(int j=0; j < (parents.size() - 1); j++) {
				System.out.println("makespan: " + df.format(parents.get(j).getParent1().getMakespan()));
				System.out.println("makespan: " + df.format(parents.get(j).getParent2().getMakespan()));
			}*/
			
			//CROSSOVER
			List<Individual> childs = new ArrayList();
			if(this.crossoverOperator == 1) {
				childs = Individual.crossover_one_point(parents, i+1, this.tasks);
			} else if(this.crossoverOperator == 2) {
				childs = Individual.crossover_two_point(parents, i+1, this.tasks);
			} else if(this.crossoverOperator == 3) {
				childs = Individual.crossover_uniform_unique(parents, i+1, this.tasks);
			} else if(this.crossoverOperator == 4) {
				childs = Individual.crossover_uniform_multiple(parents, i+1, this.tasks);
			}
			
			//teste
			/*System.out.println("Childs");
			for(int j=0; j < childs.size(); j++) {
				System.out.println("makespan: " + df.format(childs.get(j).getMakespan()));
			}*/
			
			//MUTACAO
			if(this.mutationOperator == 1) {
				for(int j=0; j < childs.size(); j++) {
					Individual child = childs.get(j);
					child.apply_mutation_simple(this.mutationFactor);
					//makespanPopulation.insert_individual(child);
				}
			} else if(this.mutationOperator == 2) {
				for(int j=0; j < childs.size(); j++) {
					Individual child = childs.get(j);
					child.apply_mutation_uniform(this.mutationFactor);
					//makespanPopulation.insert_individual(child);
				}
			}
			
			
			
			//System.out.println("Size population: " + makespanPopulation.individuals.size());
			
			//makespanPopulation.update_population(this.elitismFactor);
			
			//if(i % 100 == 0) {
				save_log_information(i, makespanPopulation);
			//}
		}
	}
	
	public List<Task> loadTasks() {
		
		List<Task> tasks = new ArrayList<Task>();
		
		try {
			
			File file = new File(fileName);
			FileReader fileReader = new FileReader(file);
		
			BufferedReader br = new BufferedReader(fileReader); 
			
			float[][] tasksTimes = new float[this.tasks][this.machines];
			
			String st;
			int tsk = 0, mch = 0;
						
			while ((st = br.readLine()) != null) {
				
				  float executionTime = Float.parseFloat(st);
				  
				  tasksTimes[tsk][mch] = executionTime;
				  
				  if(mch == (machines-1)) {
					  mch = 0;  
					 tsk++;
				  } else {
					  mch++;
				  }
			 }
			
			for(int i=0; i < this.tasks; i++) {
				  Task task = new Task(i);
				for(int j=0; j< this.machines; j++) {
					task.setMachineTimes(tasksTimes[i][j]);
				}
				tasks.add(task);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return tasks;

	}
	
	public void save_log_information(int index, Population population_bkp) {
		/*Individual best = population_bkp.getBestIndividual();
		
		DecimalFormat df = new DecimalFormat("#.00");
		
		StringBuffer log = new StringBuffer("Generation " + best.getGeneration() + " \n");
		//log.append("Makespan do melhor indivíduo: " + df.format(best.getMakespan()) + " \n");
		//log.append("Flowtime do melhor indivíduo: " + df.format(best.getFlowtime()) + " \n");
		String tmp = " ";
		
		for(Individual ind : population_bkp.bestIndividuals) {
			tmp += ind.getMakespan() + ", ";
		}
		log.append("Melhores individuos: [ " + tmp + " ] \n");
		
		log.append("Média da população: " + df.format(population_bkp.average()) + " \n");
		log.append("Desvio padrão da população: " + df.format(population_bkp.standardDeviation()) + " \n");
		log.append("-------------------------- \n");
		
		System.out.print(log.toString());
		
		Log.write(log.toString());*/
		
	} 
}
