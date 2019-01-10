package distances;

public class DistanceJaccard extends DistanceFunction {

	static String NAME = "jaccard";

	public double calculateDistance(double[] vector1, double[] vector2) {
		double count11 = 0;	  // count of M11
		double count10or01or11 = 0; // count of M01, M10 and M11
		
		// for each position in the vector
		for(int i=0; i< vector1.length; i++){
			// if it is not  two 0s
			if(vector1[i] != 0  || vector2[i] != 0) {
				// if it is two 1s
				if(vector1[i] == 1  && vector2[i] == 1) {
					count11++;
				}
				// increase the count of not two 0s
				count10or01or11++;
			}
			
		}
		return count11 / count10or01or11;
	}
	
	@Override
	public String getName() {
		return NAME;
	}
	
	public static void main(String[] args) {
		double[] array1 = new double[] {0,1,0,1};
		double[] array2 = new double[] {1,0,0,1};
		System.out.println(new DistanceJaccard().calculateDistance(array1,array2));
		// result should be 0.33
		
		double[] array4 = new double[] {1, 0};
		double[] array3 = new double[] {1, 0};
		System.out.println(new DistanceCosine().calculateDistance(array3,array4));
	}
	

}
