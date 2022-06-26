 package SimulateEngine;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Vector;

import Parameters.Parameters;
import SimulateAG.Individual;
import Task.Task;

public class SimulateEngine {
	
	Parameters parameters = new Parameters("/parameters/parameters_heuristics.txt");
	
	public int machines;
	public int tasks;
	
	public int mat[];
	public float etc[][];
	public PriorityQueue<Task> p[]; 
	
	public Comparator<Task> comparator;
	
	public SimulateEngine() {
		machines = parameters.get_NUM_MACHINES();
		tasks = parameters.get_NUM_TASKS();
		mat = new int[machines];
		p = new PriorityQueue[machines];  
		
		for(int i = 0;i<p.length;i++) {
			p[i] = new PriorityQueue<Task>(5,comparator);
		}
	}
	
	public Individual execute() {
		
		ETCGenerator etcGenerator = new ETCGenerator(machines, tasks, parameters.getFileName());
		
		etcGenerator.generateETC();
		
		etc = etcGenerator.getETC();
		
		return executeSimulate();
	}
	
	public Individual executeSimulate() {

		Vector<Task> metaSet = new Vector<Task>(tasks);
		
		for(int i=0; i<tasks; i++) {
		   Task k = new Task(i);
		   metaSet.add(k);
		}
		
		//System.out.println("Início da execução");
		
		//float result = Heuristics.schedule_MinMin(metaSet, getInstance());
		Individual ind = Heuristics.schedule_MinMin(metaSet, getInstance());
		
		//System.out.println("Termino da execução");
		return ind;
	}
	
	public void mapTask(Task t, int machine){
        t.set_eTime(etc[t.tid][machine]);
        t.set_cTime( mat[machine]+etc[t.tid][machine] );
        p[machine].offer(t);
        mat[machine] = (int) t.get_cTime();
    }
	
	public SimulateEngine getInstance() {
		return this;
	}
}
