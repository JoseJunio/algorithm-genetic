package SimulateEngine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ETCGenerator {
	
	private int machines;
	private int tasks;
	private float etc[][];
	private double etcDouble[][];
	private String fileName;
	
	public ETCGenerator(int machines, int tasks, String fileName) {
		this.machines = machines;
		this.tasks = tasks;
		this.fileName = fileName;
	}
	
	public void generateETC(){
    	etc = new float[tasks][machines];
     
    	try {
			File file = new File(fileName);
			FileReader fileReader = new FileReader(file);
			
			BufferedReader br = new BufferedReader(fileReader); 
			
			int task = 0, machine = 0;
			
			String st = ""; 
			
			while ((st = br.readLine()) != null) {
				
				  float executionTime = Float.parseFloat(st);
				  
				  etc[task][machine] = executionTime;
				  
				  System.out.println("[" + task + "][" + machine + "] = " + executionTime);
				  
				  if(machine == (machines-1)) {
					 machine = 0;  
					 task++;
				  } else {
					 machine++;
				  }
			 }
	
		} catch (Exception e) {
			e.printStackTrace();
		}	
    	
    }
	
	
	public void generateETCDouble(){
		
		etcDouble = new double[tasks][machines];
     
    	try {
			File file = new File(System.getProperty("user.dir") + "/" + fileName);
			FileReader fileReader = new FileReader(file);
			
			BufferedReader br = new BufferedReader(fileReader); 
			
			int task = 0, machine = 0;
			
			String st = ""; 
			
			while ((st = br.readLine()) != null) {
				
				  double executionTime = Double.parseDouble(st);
				  
				  etcDouble[task][machine] = executionTime;
				  
				  if(machine == (machines-1)) {
					 machine = 0;  
					 task++;
				  } else {
					 machine++;
				  }
			 }
	
		} catch (Exception e) {
			e.printStackTrace();
		}	
    	
    }
	
	public float[][] getETC(){
		if(etc == null) {
			generateETC();
		}
		
        return etc;
    }
	
	public double[][] getETCDouble(){
		if(etcDouble == null) {
			generateETCDouble();
		}
		
        return etcDouble;
	}
	
}
