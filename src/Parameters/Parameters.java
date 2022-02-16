package Parameters;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parameters {

	private int 				NUM_MACHINES;
    private int 				NUM_TASKS;
    private String				fileName;
    private String				fileNameTratado;
    
    // Algoritmo Genético
    private int populationMaskespan;
    private int percMutation;
    private int percCrossover; 
    private int percElitism;
    private int operCrossover; 
    private int operMutation; 
    private int numExecution;
    private int numIteration;
    
    public Parameters(String path) {
    	
    	String pathFile = System.getProperty("user.dir") + path;
    	
    	File file = new File(pathFile);
		FileReader fileReader;
		
		try {
		
			fileReader = new FileReader(file);
		
			Pattern p = Pattern.compile("(?:.*?= )(.*?)(?=\\n\\d:|$)");
			
			BufferedReader br = new BufferedReader(fileReader); 
			
			String line;
			int count = 0;
			
			while ((line = br.readLine()) != null) {
				
				Matcher m = p.matcher(line);
				
				if(m.matches()) {
					switch(count) {
				 		case 0: 
				 			NUM_MACHINES = Integer.parseInt(m.group(1));
				 			break;
				 		case 1: 
				 			NUM_TASKS = Integer.parseInt(m.group(1));
				 			break;
				 		case 2: 
				 			fileName = m.group(1);
				 			break;
				 		case 3: 
				 			fileNameTratado = m.group(1);
				 			break;
				 		case 4: 
				 			populationMaskespan = Integer.parseInt(m.group(1));
				 			break;
				 		case 5: 
				 			percMutation = Integer.parseInt(m.group(1));
				 			break;
				 		case 6: 
				 			percCrossover = Integer.parseInt(m.group(1));
				 			break;
				 		case 7: 
				 			percElitism = Integer.parseInt(m.group(1));
				 			break;
				 		case 8: 
				 			operCrossover = Integer.parseInt(m.group(1));
				 			break;
				 		case 9: 
				 			operMutation = Integer.parseInt(m.group(1));
				 			break;
				 		case 10: 
				 			numExecution = Integer.parseInt(m.group(1));
				 			break;
				 		case 11: 
				 			numIteration = Integer.parseInt(m.group(1));
				 			break;
					}
					count++;
				}
			}
			
		} catch (Exception  e) {
			e.printStackTrace();
		}
    
    }

	public int get_NUM_MACHINES() {
		return NUM_MACHINES;
	}

	public void set_NUM_MACHINES(int nUM_MACHINES) {
		NUM_MACHINES = nUM_MACHINES;
	}

	public int get_NUM_TASKS() {
		return NUM_TASKS;
	}

	public void set_NUM_TASKS(int nUM_TASKS) {
		NUM_TASKS = nUM_TASKS;
	}

	public String getFileNameTratado() {
		return fileNameTratado;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
    
	public int getPopulationMaskespan() {
		return populationMaskespan;
	}

	public void setPopulationMaskespan(int populationMaskespan) {
		this.populationMaskespan = populationMaskespan;
	}

	public int getPercMutation() {
		return percMutation;
	}

	public void setPercMutation(int percMutation) {
		this.percMutation = percMutation;
	}

	public int getPercCrossover() {
		return percCrossover;
	}

	public void setPercCrossover(int percCrossover) {
		this.percCrossover = percCrossover;
	}

	public int getPercElitism() {
		return percElitism;
	}

	public void setPercElitism(int percElitism) {
		this.percElitism = percElitism;
	}

	public int getOperCrossover() {
		return operCrossover;
	}

	public void setOperCrossover(int operCrossover) {
		this.operCrossover = operCrossover;
	}

	public int getOperMutation() {
		return operMutation;
	}

	public void setOperMutation(int operMutation) {
		this.operMutation = operMutation;
	}

	public int getNumExecution() {
		return numExecution;
	}

	public void setNumExecution(int numExecution) {
		this.numExecution = numExecution;
	}

	public int getNumIteration() {
		return numIteration;
	}

	public void setNumIteration(int numIteration) {
		this.numIteration = numIteration;
	}
	
}
