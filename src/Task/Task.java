package Task;

import java.util.ArrayList;
import java.util.List;

public class Task implements Comparable<Task>{

    public int tid;
    
    /*The completion time*/
    float cTime;

    /*The time required to execute the task on the machine on which it has been scheduled*/
    float eTime;
    
    public List<Float> machineTimes;

    public Task(int task_id){
        tid=task_id;
        machineTimes = new ArrayList<Float>();
    }

    public float get_cTime() {
        return cTime;
    }

    public void set_cTime(float cTime) {
        this.cTime = cTime;
    }

    public float get_eTime() {
        return eTime;
    }

    public void set_eTime(float eTime) {
        this.eTime = eTime;
    }
    
    public List<Float> getMachineTimes() {
		return machineTimes;
	}

	public void setMachineTimes(float machineTime) {
		this.machineTimes.add(machineTime);
	}

	@Override
	public int compareTo(Task o) {
		// TODO Auto-generated method stub
		return 0;
	}
    
}
