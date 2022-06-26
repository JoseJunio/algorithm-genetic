package SimulateAG;

public class Tuple {
	Individual parent1;
	Individual parent2;
	
	public Tuple(Individual parent1, Individual parent2) {
		this.parent1 = parent1;
		this.parent2 = parent2;
	}

	public Individual getParent1() {
		return parent1;
	}

	public Individual getParent2() {
		return parent2;
	}
}