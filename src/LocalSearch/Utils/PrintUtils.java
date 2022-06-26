package LocalSearch.Utils;

public class PrintUtils {

	public static void printMapping(int[] mapping) {
		String tmpStr = "[ ";

		for (int ab = 0; ab < mapping.length; ab++) {
			int machine = mapping[ab];
			
			tmpStr = tmpStr.concat(machine + ", ");
			
		}

		tmpStr = tmpStr.concat("]");
	}
	
}
