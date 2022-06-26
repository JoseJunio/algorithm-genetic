
package LocalSearch;

public class Solution implements Cloneable{

	public int[] mapping;   
	public double[] load;    
	public double makespan;
	public int machines = 16;
	public int tasks = 512;
	public double[][] etc;

	public Solution(double[][] etc){
		this.mapping = new int[512];
		this.load = new double[16];
		this.makespan = 0;
		this.etc = etc;
	}
	
	public void makespan() {
		
	  double max = -1;

	  for (int i = 0; i < machines; i++) {
	    if (load[i] > max) {
	      max = load[i];
	    }
	  }

	  this.makespan = -max;
		
	}
	
	public void load() {

	  double loadTmp;

	  for (int j = 0; j < machines; j++){
		loadTmp = 0.0;
	    
		for (int i = 0; i < tasks; i++){
	      if (mapping[i] == j){
	    	  loadTmp += etc[i][j];
	      }
	    }
	    
		load[j] = loadTmp;
	  
	  }
		
	}
	
	
	public Object clone() {
        try {
            // call clone in Object.
            return super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("Cloning not allowed.");
            return this;
        }
    }
	
	 
}
