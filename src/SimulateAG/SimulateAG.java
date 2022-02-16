package SimulateAG;

import Parameters.Parameters;

public class SimulateAG {
	
	public static Parameters parameters = new Parameters("/parameters/parameters_ag.txt");
	
	public int machines;
	public int tasks;
	
	public static AG ag = new AG(parameters);
	
	public static void execute() {
		ag.execute();
	}
	
}
